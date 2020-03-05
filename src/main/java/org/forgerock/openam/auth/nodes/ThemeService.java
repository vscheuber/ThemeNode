package org.forgerock.openam.auth.nodes;

import org.forgerock.openam.annotations.sm.Attribute;
import org.forgerock.openam.annotations.sm.Config;
import org.forgerock.openam.annotations.sm.SubConfig;
import org.forgerock.openam.sm.annotations.subconfigs.Multiple;

import com.sun.identity.sm.RequiredValueValidator;

/**
 * Configuration for the node.
 */
@Config(scope = Config.Scope.REALM_AND_GLOBAL)
public interface ThemeService {
	
	@SubConfig
	Multiple<ThemeConfig> themeConfigs();

    @Attribute(order = 90, validators = {RequiredValueValidator.class})
    default boolean enable() { return true; }
    
}
