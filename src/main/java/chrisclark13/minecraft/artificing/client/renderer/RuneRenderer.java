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
     * Renders a string of runes so that they are rendered in reverse at (0,0)</br> Each
     * rune takes up 0.625 x 1.0 (40/64 x 1.0) space with 0.03125 (2 pixels) of
     * padding
     * 
     * @param string
     * @param color
     */
    public static void renderRunes(String string, int color) {
        renderRunes(string, 0, 0, color);
    }
    
    /**
     * Renders a string of runes so that they are rendered in reverse at (x,y)
     * position.</br> Each rune takes up 0.625 x 1.0 (40/64 x 1.0) space with
     * 0.03125 (2 pixels) of padding
     * 
     * @param string
     * @param x
     * @param y
     * @param color
     */
    public static void renderRunes(String string, float x, float y, int color) {
        // Lower case string and remove invalid characters
        string = string.toLowerCase(Locale.US).replaceAll(REPLACE, "").trim();
        
        // Reverse string
        StringBuffer sb = new StringBuffer();
        for (int i = string.length() - 1; i >= 0; i--) {
            sb.append(string.charAt(i));
        }
        string = sb.toString();
        
        float widthScaled = (float) RUNE_WIDTH / (float) RUNE_HEIGHT;
        float paddingScaled = (float) PADDING / (float) RUNE_HEIGHT;
        
        for (int i = 0; i < string.length(); i++) {
            float xOffset = (i * widthScaled + i * paddingScaled);
            
            renderRune(string.charAt(i), x + xOffset, y, color);
        }
    }
    
    /**
     * Renders the rune from the rune.png that cooresponds to character</br>
     * Characters that are not a-z will not be rendered.
     * 
     * @param character
     * @param color
     */
    public static void renderRune(char character, int color) {
        renderRune(character, 0, 0, color);
    }
    
    /**
     * Renders the rune from the rune.png that cooresponds to character</br>
     * Characters that are not a-z will not be rendered.
     * 
     * @param character
     * @param x
     * @param y
     * @param color
     */
    public static void renderRune(char character, float x, float y, int color) {
        mc.renderEngine.bindTexture(Textures.RUNES);
        
        if (character >= 'a' && character <= 'z') {
            
            float r = ((color >> 16) & 0xFF) / 256F;
            float g = ((color >> 8) & 0xFF) / 256F;
            float b = (color & 0xFF) / 256F;
            
            GL11.glColor3f(r, g, b);
            
            int charNum = character - 'a';
            
            float width = (float) RUNE_WIDTH / (float) RUNE_HEIGHT;
            int w = IMAGE_WIDTH / RUNE_WIDTH;
            
            int u = (charNum % w) * RUNE_WIDTH;
            int v = (charNum / w) * RUNE_HEIGHT;
            
            RenderUtil.drawImagePart(x, y, width, 1, u, v, RUNE_WIDTH,
                    RUNE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
        }
    }
    
    public static float getRenderWidth(String string) {
        string = string.toLowerCase(Locale.US).replaceAll(REPLACE, "").trim();
        
        float widthScaled = (float) RUNE_WIDTH / (float) RUNE_HEIGHT;
        float paddingScaled = (float) PADDING / (float) RUNE_HEIGHT;
        
        return (string.length() * widthScaled + string.length() * paddingScaled);
    }
}
