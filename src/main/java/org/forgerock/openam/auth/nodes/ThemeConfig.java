package org.forgerock.openam.auth.nodes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.forgerock.openam.annotations.sm.Attribute;
import org.forgerock.openam.annotations.sm.Id;
import org.forgerock.openam.auth.nodes.themes.ThemeUtils;

import com.sun.identity.sm.RequiredValueValidator;

public interface ThemeConfig {
	
	@Id
	String id();

	@Attribute(order = 100, validators = {RequiredValueValidator.class})
    default Theme theme() { return Theme.FR_ORANGE; }

    @Attribute(order = 200, validators = {RequiredValueValidator.class})
    default boolean replaceLogo() { return true; }

    @Attribute(order = 300)
    default Map<String, String> replaceLogoProperties() {
    		Map<String, String> props = new HashMap<String, String>();
    		props.put("src", "https://www.forgerock.com/img/ForgeRock_Vert_Color_Logo_RGB_R_med.svg");
    		props.put("alt", "ForgeRock");
    		props.put("width", "210px");
    		props.put("class", "main-logo");
    		return props;
    	}

    @Attribute(order = 400, validators = {RequiredValueValidator.class})
    default boolean overwriteFooter() { return true; }

    @Attribute(order = 500)
    default Map<Locale, String> overwriteFooterText() {
    		Map<Locale, String> text = new HashMap<Locale, String>();
    		text.put(new Locale("en"), "Copyright © 2010-2020 ForgeRock AS. All rights reserved.");
    		return text;
    	}

    @Attribute(order = 600)
    default String customTheme() { return "Beach"; };

    @Attribute(order = 700)
    default Map<String, String> customThemes() {
    		Map<String, String> props = new HashMap<String, String>();
    		props.put("Beach", ThemeUtils.loadCSSFromFile("Beach.css"));
    		props.put("Green", ThemeUtils.loadCSSFromFile("Green.css"));
    		props.put("Red", ThemeUtils.loadCSSFromFile("Red.css"));
    		return props;
    	}

    @Attribute(order = 800, validators = {RequiredValueValidator.class})
    default boolean customizeTheme() { return false; }

    @Attribute(order = 900)
    default Map<String, String> customizeThemeProperties() {
    		Map<String, String> props = new HashMap<String, String>();
    		props.put(".footer", "border-top: 1px solid rgba(0, 83, 101, 0.616); background-color: rgba(0, 83, 101); color:white;");
    		props.put(".btn-primary", "color: #fff; background-color: #fcd800; border-color: #e3c200;");
    		props.put(".btn-info", "color: #fff; background-color: #5bc0de; border-color: #46b8da;");
    		props.put(".form-control", "color: #000; border: 1px solid #005365;");
    		props.put("a", "color: #00a7bc; text-decoration: none; background-color: transparent;");
    		return props;
    	}
    
	public static enum Theme {
		NONE,
		FR_BLUE_DARK,
		FR_BLUE,
		FR_ORANGE,
		CUSTOM
	}

	@SuppressWarnings("serial")
	public static final Map<Theme, String> themes = new TreeMap<Theme, String>() {
		{
			put(Theme.FR_BLUE_DARK, ThemeUtils.loadCSSFromFile("theme-forgerock-blue-dark.css"));
			put(Theme.FR_BLUE, ThemeUtils.loadCSSFromFile("theme-forgerock-blue.css"));
			put(Theme.FR_ORANGE, ThemeUtils.loadCSSFromFile("theme-forgerock-orange.css"));
		}
	};

}