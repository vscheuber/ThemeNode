package org.forgerock.openam.auth.nodes.themes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.forgerock.openam.auth.nodes.ThemeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThemeUtils {
	
	static Logger logger = LoggerFactory.getLogger("amAuth");
	
	public static String loadCSSFromFile(String name) {
        int c = 0;
        int z = c;
        String css = "";
		InputStream in = ThemeUtils.class.getResourceAsStream(name);
		if (null == in)
			return css;
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
	        while(c >= 0) {
	            c = reader.read();
	            // skip carriage returns
	            if(c == 13)
	                continue;
	            // skip line breaks
	            if(c == 10)
	                continue;
	            // skip tabs
	            if(c == 9)
	                continue;
	            // skip multiple spaces
	            if(c == 32 && z == 32)
	                continue;
	            // Skip EOF
	            if(c == -1)
	                continue;
	            css += (char) c;
	            z = c;
	        }
        }
        catch (IOException e) {
        	logger.error("Error loading theme css file \"{}\"", name, e);
        }
        return css;
	}
	
	public static String getThemeCSS(ThemeConfig themeConfig) {
		if (ThemeConfig.Theme.NONE != themeConfig.theme() && ThemeConfig.Theme.CUSTOM != themeConfig.theme()) {
			return ThemeConfig.themes.get(themeConfig.theme());
		}
		else if (ThemeConfig.Theme.CUSTOM == themeConfig.theme()) {
			if (null != themeConfig.customThemes().get(themeConfig.customTheme()))
				return themeConfig.customThemes().get(themeConfig.customTheme()).replace('\r', ' ').replace('\n', ' ');
			logger.error("Error loading custom theme \"{}\". Make sure the value entered in Custom Theme matches the name (key) of one of the custom themes in the Custom Theme Definitions list.", themeConfig.customTheme());
		}
		return null;
	}

}
