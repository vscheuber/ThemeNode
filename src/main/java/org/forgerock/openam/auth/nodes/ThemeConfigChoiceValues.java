package org.forgerock.openam.auth.nodes;

import static org.forgerock.openam.utils.StringUtils.isBlank;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.forgerock.guice.core.InjectorHolder;
import org.forgerock.openam.core.realms.Realm;
import org.forgerock.openam.core.realms.RealmLookupException;
import org.forgerock.openam.core.realms.Realms;
import org.forgerock.openam.sm.AnnotatedServiceRegistry;
import org.slf4j.LoggerFactory;

import com.iplanet.sso.SSOException;
import com.sun.identity.shared.Constants;
import com.sun.identity.sm.ChoiceValues;
import com.sun.identity.sm.SMSEntry;
import com.sun.identity.sm.SMSException;

public class ThemeConfigChoiceValues extends ChoiceValues {
	
	public static void main(String[] args) {
		System.out.println("Global: \"" + createThemeConfigName("Global One") + "\"");
		System.out.println("Global: " + isGlobalThemeConfig(createThemeConfigName("Global One")));
		System.out.println("Global: \"" + getId(createThemeConfigName("Global One")) + "\"");
		System.out.println("Realm: \"" + createThemeConfigName("Realm One", "/one/two") + "\"");
		System.out.println("Realm: " + isGlobalThemeConfig(createThemeConfigName("Realm One", "/one/two")));
		System.out.println("Realm: \"" + getId(createThemeConfigName("Realm One", "/one/two")) + "\"");
		System.out.println("Realm: \"" + getRealmString(createThemeConfigName("Realm One", "/one/two")) + "\"");
	}

	public static String createThemeConfigName(String id) {
		return id;
	}

	public static String createThemeConfigName(String id, String realm) {
		return id + " [" + realm + "]";
	}

	public static String createThemeConfigName(String id, Realm realm) {
		return id + " [" + realm.asPath() + "]";
	}
	
	public static boolean isGlobalThemeConfig(String themeConfigName) {
		return !themeConfigName.endsWith("]");
	}
	
	public static String getRealmString(String themeConfigName) {
		if (isGlobalThemeConfig(themeConfigName))
			return null;
		return themeConfigName.substring(themeConfigName.lastIndexOf('[')+1, themeConfigName.length()-1);
	}
	
	public static Realm getRealm(String themeConfigName) throws RealmLookupException {
		if (isGlobalThemeConfig(themeConfigName))
			return null;
		return Realms.of(getRealmString(themeConfigName));
	}
	
	public static String getId(String themeConfigName) {
		if (isGlobalThemeConfig(themeConfigName))
			return themeConfigName;
		return themeConfigName.substring(0, themeConfigName.lastIndexOf('[')-1);
	}
	
	public static ThemeConfig getThemeConfig(String themeConfigName) {
		AnnotatedServiceRegistry serviceRegistry = InjectorHolder.getInstance(AnnotatedServiceRegistry.class);
		try {
			ThemeService themeService;
			if (isGlobalThemeConfig(themeConfigName)) {
				themeService = serviceRegistry.getGlobalSingleton(ThemeService.class);
			}
			else {
				themeService = serviceRegistry.getRealmSingleton(ThemeService.class, getRealm(themeConfigName)).get();
			}
			return themeService.themeConfigs().get(getId(themeConfigName));
		} catch (SSOException | SMSException | RealmLookupException e) {
			LoggerFactory.getLogger("amAuth").error("Couldn't load theme configs", e);
			throw new IllegalStateException("Couldn't load theme configs", e);
		}
	}
	
	public static boolean isThemeServiceEnabled(String themeConfigName) {
		AnnotatedServiceRegistry serviceRegistry = InjectorHolder.getInstance(AnnotatedServiceRegistry.class);
		try {
			ThemeService themeService;
			if (isGlobalThemeConfig(themeConfigName)) {
				themeService = serviceRegistry.getGlobalSingleton(ThemeService.class);
			}
			else {
				themeService = serviceRegistry.getRealmSingleton(ThemeService.class, getRealm(themeConfigName)).get();
			}
			return themeService.enable();
		} catch (SSOException | SMSException | RealmLookupException e) {
			LoggerFactory.getLogger("amAuth").error("Couldn't load theme configs", e);
			throw new IllegalStateException("Couldn't load theme configs", e);
		}
	}

    @Override
    public Map<String, String> getChoiceValues() {
        return getChoiceValues(Collections.EMPTY_MAP);
    }
    
	@Override
	public Map getChoiceValues(Map envParams) throws IllegalStateException {
        String realm = null;
        if (envParams != null) {
            realm = (String) envParams.get(Constants.ORGANIZATION_NAME);
        }
        if (isBlank(realm)) {
            realm = SMSEntry.getRootSuffix();
        }
		AnnotatedServiceRegistry serviceRegistry = InjectorHolder.getInstance(AnnotatedServiceRegistry.class);
		try {
			Map<String, String> configs = new TreeMap<String, String>();
			ThemeService globalThemeService = serviceRegistry.getGlobalSingleton(ThemeService.class);
			Iterator<String> globalConfigIterator = globalThemeService.themeConfigs().idSet().iterator();
			while (globalConfigIterator.hasNext()) {
				String id = globalConfigIterator.next();
				configs.put(ThemeConfigChoiceValues.createThemeConfigName(id), "");
			}
			if (serviceRegistry.getRealmSingleton(ThemeService.class, Realms.of(realm)).isPresent()) {
				ThemeService realmThemeService = serviceRegistry.getRealmSingleton(ThemeService.class, Realms.of(realm)).get();
				Iterator<String> realmConfigIterator = realmThemeService.themeConfigs().idSet().iterator();
				while (realmConfigIterator.hasNext()) {
					String id = realmConfigIterator.next();
					configs.put(ThemeConfigChoiceValues.createThemeConfigName(id, Realms.of(realm)), "");
				}
			}
			return configs;
		} catch (SSOException | SMSException | RealmLookupException e) {
			LoggerFactory.getLogger("amAuth").error("Couldn't load theme configs", e);
			throw new IllegalStateException("Couldn't load theme configs", e);
		}
	}
}