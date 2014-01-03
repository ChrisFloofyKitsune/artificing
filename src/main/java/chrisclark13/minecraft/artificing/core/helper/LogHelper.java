package chrisclark13.minecraft.artificing.core.helper;

import java.util.logging.Level;
import java.util.logging.Logger;

import chrisclark13.minecraft.artificing.lib.Reference;
import cpw.mods.fml.common.FMLLog;

public class LogHelper {

    private static Logger msiLogger = Logger.getLogger(Reference.MOD_ID);
    private static boolean intialized = false;

    public static void init() {

        msiLogger.setParent(FMLLog.getLogger());
        msiLogger.setLevel(Level.FINE);
        intialized = true;
    }

    public static void log(Level logLevel, String message) {
        
        if (!intialized) {
            init();
        }
        
        msiLogger.log(logLevel, message);
    }

}