/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2017-2018 ForgeRock AS.
 */


package org.forgerock.openam.auth.nodes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.forgerock.openam.annotations.sm.Attribute;
import org.forgerock.openam.auth.node.api.Action;
import org.forgerock.openam.auth.node.api.Node;
import org.forgerock.openam.auth.node.api.NodeProcessException;
import org.forgerock.openam.auth.node.api.SingleOutcomeNode;
import org.forgerock.openam.auth.node.api.TreeContext;
import org.forgerock.openam.auth.nodes.themes.ThemeUtils;
import org.forgerock.openam.core.realms.Realm;
import org.forgerock.openam.sm.AnnotatedServiceRegistry;
import org.forgerock.util.i18n.PreferredLocales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.assistedinject.Assisted;
import com.sun.identity.authentication.callbacks.ScriptTextOutputCallback;
import com.sun.identity.sm.RequiredValueValidator;

@Node.Metadata(outcomeProvider = ThemeNode.OutcomeProvider.class,
        configClass = ThemeNode.Config.class)
public class ThemeNode extends SingleOutcomeNode {

    private static final String BUNDLE = ThemeNode.class.getName().replace(".", "/");
    private final Logger logger = LoggerFactory.getLogger("amAuth");
    private final LocaleSelector localeSelector;
    private final Config config;
    private ThemeConfig themeConfig;
    
    @Inject
    public ThemeNode(
    		@Assisted Config config, 
    		LocaleSelector localeSelector, 
    		@Assisted Realm realm, 
    		AnnotatedServiceRegistry serviceRegistry) throws NodeProcessException 
    {
		this.localeSelector = localeSelector;
		this.config = config;
		this.themeConfig = ThemeConfigChoiceValues.getThemeConfig(config.themeConfigName());
    }

    /**
     * Configuration for the node.
     * It can have as many attributes as needed, or none.
     */
    public interface Config {
    	
        @Attribute(order = 100, choiceValuesClass = ThemeConfigChoiceValues.class)
        default String themeConfigName() { return ThemeConfigChoiceValues.createThemeConfigName("Global Default"); };

        @Attribute(order = 200, validators = {RequiredValueValidator.class})
        default boolean overwriteButton() { return false; }

        @Attribute(order = 300)
        default Map<Locale, String> overwriteButtonText() {
	    		Map<Locale, String> text = new HashMap<Locale, String>();
	    		text.put(new Locale("en"), "Next");
	    		return text;
	    	}
        
    }

    @Override
    public Action process(TreeContext context) throws NodeProcessException {
        if (context.hasCallbacks() || !ThemeConfigChoiceValues.isThemeServiceEnabled(config.themeConfigName())) {
        	logger.debug("Done.");
            return goToNext().build();
        }
        logger.debug("Applying theme...");
        ScriptTextOutputCallback scriptAndSelfSubmitCallback = new ScriptTextOutputCallback(createClientSideScriptExecutorFunction(getScript(context)));
        return Action.send(Arrays.asList(
        		scriptAndSelfSubmitCallback
        		)).build();

    }
    
    private String getScript(TreeContext context) {
        // client-side script to change the look and feel of how the terms and conditions are displayed.
    	StringBuffer script = new StringBuffer()
			.append("var callbackScript = document.createElement(\"script\");\n")
			.append("callbackScript.type = \"text/javascript\";\n")
			.append("callbackScript.text = \"function completed() { document.querySelector(\\\"input[type=submit]\\\").click(); }\";\n")
			.append("document.body.appendChild(callbackScript);\n")
			.append("\n")
			.append("submitted = true;\n")
			.append("\n")
			.append("function applyStyle(className, style) {\n")
			.append("    var elems = document.querySelectorAll(className);\n")
			.append("    var index = 0, length = elems.length;\n")
			.append("    for ( ; index < length; index++) {\n")
			.append("        elems[index].style = style;\n")
			.append("    }\n")
			.append("}\n")
			.append("\n")
			.append("function callback() {\n")
			.append("\n");
    	if (themeConfig.replaceLogo()) {
	    	logger.debug("Replacing logo: {}", themeConfig.replaceLogoProperties().get("src"));
	    	script
	    		.append("    var logo = document.getElementById(\"loginBaseLogo\");\n")
				.append("    logo.innerHTML = '");
				if (null != themeConfig.replaceLogoProperties().get("src")) {
					script.append("<img");
			        Iterator<Entry<String, String>> iterator = themeConfig.replaceLogoProperties().entrySet().iterator();
			        while (iterator.hasNext()) {
						Map.Entry<String, String> prop = iterator.next();
						script.append(" ").append(prop.getKey()).append("=\"").append(substitute(context, prop.getValue())).append("\"");
			        }
					script.append(">");
				}
			script
				.append("';\n")
	    		.append("\n");
    	}
    	if (config.overwriteButton()) {
	    	String overwriteButtonText = substitute(context, getLocalisedMessage(context, config.overwriteButtonText(), "default.overwriteButtonText"));
	    	logger.debug("Overwriting button: {}", overwriteButtonText);
	    	script
	    		.append("    var button = document.getElementById(\"loginButton_0\");\n")
	    		.append("    button.value = \"").append(overwriteButtonText).append("\";\n")
	    		.append("\n");
    	}
    	if (themeConfig.overwriteFooter()) {
	    	String overwriteFooterText = substitute(context, getLocalisedMessage(context, themeConfig.overwriteFooterText(), "default.overwriteButtonText"));
	    	logger.debug("Overwriting footer: {}", overwriteFooterText);
	    	script
	    		.append("    var footer = document.getElementById(\"footer\");\n")
				.append("    footer.innerHTML = '<div class=\"container\"><p><br>").append(overwriteFooterText).append("<br></p></div>';\n")
	    		.append("\n");
    	}
    	if (null != ThemeUtils.getThemeCSS(themeConfig)) {
    		script
				.append("    if (null == document.getElementById(\"style-theme-node\")) {\n")
				.append("    	var style = document.createElement(\"style\");\n")
				.append("    	style.id = \"style-theme-node\";\n")
				.append("    	style.innerHTML = '").append(ThemeUtils.getThemeCSS(themeConfig)).append("';\n")
				.append("    	document.head.append(style);\n")
				.append("    }\n")
				.append("    else {\n")
				.append("    	var style = document.getElementById(\"style-theme-node\");\n")
				.append("    	style.innerHTML = '").append(ThemeUtils.getThemeCSS(themeConfig)).append("';\n")
				.append("    }\n");
    	}
    	if (themeConfig.customizeTheme()) {
	        Iterator<Entry<String, String>> iterator = themeConfig.customizeThemeProperties().entrySet().iterator();
	        while (iterator.hasNext()) {
				Map.Entry<String, String> prop = iterator.next();
				script.append("    applyStyle(\"").append(prop.getKey()).append("\", \"").append(substitute(context, prop.getValue())).append("\");\n");
	        }
    	}
    	script
			.append("}\n")
			.append("\n")
			.append("if (document.readyState !== 'loading') {\n")
			.append("  callback();\n")
			.append("} else {\n")
			.append("  document.addEventListener(\"DOMContentLoaded\", callback);\n")
			.append("}");
        
        return script.toString();
    }

    public static String createClientSideScriptExecutorFunction(String script) {
        return String.format(
                "(function(output) {\n" +
                "    var autoSubmitDelay = 0,\n" +
                "        submitted = false;\n" +
                "    function submit() {\n" +
                "        if (submitted) {\n" +
                "            return;\n" +
                "        }" +
                "        document.forms[0].submit();\n" +
                "        submitted = true;\n" +
                "    }\n" +
                "    %s\n" + // script
                "    setTimeout(submit, autoSubmitDelay);\n" +
                "}) (document.forms[0].elements['nada']);\n",
                script);
    }
    
    private String substitute(TreeContext context, String input) {
    	java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\{{2}([\\w]+?)\\}{2}");
    	java.util.regex.Matcher matcher = pattern.matcher(input);

    	String key, value;
    	HashMap<String, String> vars = new HashMap<String, String>();
    	while(matcher.find()) {
    		key = matcher.group(1);
    		value = context.transientState.get(key).asString();
    		if (null != value && !vars.containsKey(key)) {
    			logger.debug("Resolved variable reference from transient state: {}={}", key, value);
    			vars.put(key, value);
    			continue;
    		}
    		value = context.sharedState.get(key).asString();
    		if (null != value && !vars.containsKey(key)) {
    			logger.debug("Resolved variable reference from shared state: {}={}", key, value);
    			vars.put(key, value);
    			continue;
    		}
			logger.debug("Variable reference not foound: {}", key);
    	}
    	
    	String k, v;
    	Iterator<String> iter = vars.keySet().iterator();
    	while ( iter.hasNext() ) {
    		k = iter.next();
    		v = vars.get(k);
    		logger.debug("Replacing: {{{}}} with {}", k, v);
    		input = input.replace("{{"+k+"}}", v);
    	};
    	
    	return input;
    }

    private String getLocalisedMessage(TreeContext context, Map<Locale, String> localisations,
            String defaultMessageKey) {
        PreferredLocales preferredLocales = context.request.locales;
        Locale bestLocale = localeSelector.getBestLocale(preferredLocales, localisations.keySet());

        if (bestLocale != null) {
            return localisations.get(bestLocale);
        } else if (localisations.size() > 0) {
            return localisations.get(localisations.keySet().iterator().next());
        }

        ResourceBundle bundle = preferredLocales.getBundleInPreferredLocale(BUNDLE, ThemeNode.class.getClassLoader());
        return bundle.getString(defaultMessageKey);
    }
    
}
