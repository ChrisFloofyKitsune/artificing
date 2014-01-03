package chrisclark13.minecraft.artificing.lib;

public class Reference {
    public static final String MOD_ID = "artificing";
    public static final String MOD_NAME = "Artificing";
    public static final String CHANNEL = MOD_ID;
    public static final String VERSION = "0.0.4";
    public static final String SERVER_PROXY_CLASS = "chrisclark13.minecraft.artificing.core.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "chrisclark13.minecraft.artificing.core.proxy.ClientProxy";
    
    public static final int SHIFTED_ID_RANGE_CORRECTION = 256;
    
    public static final int TICKS_PER_SECOND = 20;
    public static final int MILLS_PER_SECOND = 1000;
    public static final int MILLS_PER_TICK = 500;
    
    /**
     * The color black, fully opaque.
     */
    public static final int BLACK = 0xFF000000;
}
