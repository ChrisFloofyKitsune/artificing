package chrisclark13.minecraft.artificing.core.helper;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class LocalizationHelper {
    
    private static final String LANG_FILE_LOCATION = "/assets/artificing/lang";
    
    private static String[] localeFiles = {LANG_FILE_LOCATION + "/en_US.xml"};
    
    public static void loadLanguages() {
        for (String locale : localeFiles) {
            LanguageRegistry.instance().loadLocalization(locale, getLocaleFromFileName(locale), isXMLFile(locale));
        }
    }
    
    private static String getLocaleFromFileName(String locale) {
        return locale.substring(locale.lastIndexOf("/") + 1, locale.lastIndexOf("."));
    }
    
    private static boolean isXMLFile(String locale) {
        return locale.endsWith(".xml");
    }
    
    public static String getLocalizedString(String key) {
        return LanguageRegistry.instance().getStringLocalization(key);
    }
}
