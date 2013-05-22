package chrisclark13.minecraft.artificing.client.renderer;

import java.util.Locale;

import org.lwjgl.opengl.GL11;

import chrisclark13.minecraft.artificing.lib.Textures;
import net.minecraft.client.Minecraft;

public class RuneRenderer {
    
    private static Minecraft mc = Minecraft.getMinecraft();
    public static final int RUNE_WIDTH = 40;
    public static final int RUNE_HEIGHT = 64;
    public static final int PADDING = 2;
    
    private static final int IMAGE_WIDTH = 280;
    private static final int IMAGE_HEIGHT = 256;
    
    private static final String REPLACE = "[^a-z| ]";
    
    private RuneRenderer() {
        
    }
    
    /**
     * Renders a string of runes so that they are rendered in reverse</br> Each
     * rune takes up 0.625 x 1.0 (40/64 x 1.0) space with 0.03125 (2 pixels) of
     * padding
     * 
     * @param string
     */
    public static void renderRunes(String string, int color) {
        mc.renderEngine.bindTexture(Textures.RUNES);
        
        string = string.toLowerCase(Locale.US).replaceAll(REPLACE, "").trim();
        
        StringBuffer sb = new StringBuffer();
        for (int i = string.length() - 1; i >= 0; i--) {
            sb.append(string.charAt(i));
        }
        
        string = sb.toString();
        
        float widthScaled = (float) RUNE_WIDTH / (float) RUNE_HEIGHT;
        float paddingScaled = (float) PADDING / (float) RUNE_HEIGHT;
        
        float r = ((color >> 16) & 0xFF) / 256F;
        float g = ((color >> 8) & 0xFF) / 256F;
        float b = (color & 0xFF) / 256F;
        
        GL11.glColor3f(r, g, b);
        
        for (int i = 0; i < string.length(); i++) {
            float x = (i * widthScaled + i * paddingScaled);
            
            if (string.charAt(i) != ' ') {
                int charNum = string.charAt(i) - 'a';
                
                int w = IMAGE_WIDTH / RUNE_WIDTH;
                
                int u = (charNum % w) * RUNE_WIDTH;
                int v = (charNum / w) * RUNE_HEIGHT;

                ArtificingRenderHelper.drawImagePart(x, 0, widthScaled, 1, u, v, RUNE_WIDTH, RUNE_HEIGHT, IMAGE_WIDTH,
                        IMAGE_HEIGHT);
            }
        }
    }
    
    public static float getRenderWidth(String string) {
        string = string.toLowerCase(Locale.US).replaceAll(REPLACE, "").trim();
        
        float widthScaled = (float) RUNE_WIDTH / (float) RUNE_HEIGHT;
        float paddingScaled = (float) PADDING / (float) RUNE_HEIGHT;
        
        return (string.length() * widthScaled + string.length() * paddingScaled);
    }
}
