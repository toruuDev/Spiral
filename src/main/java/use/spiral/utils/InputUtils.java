package use.spiral.utils;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.util.InputUtil;

public class InputUtils {
    public static InputUtil.Key getKey(String name) {
        try { // handle a-z
            if(name.length() == 1) {
                char c = Character.toUpperCase(name.charAt(0)); 
                if(c >= 'A' && c <= 'Z') {
                    return InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_A + (c - 'A'));
                }
            }

            // handle numbers 0 to 9
            if(name.length() == 1 && Character.isDigit(name.charAt(0))) {
                return InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_0 + (name.charAt(0) - '0'));
            }

            // special keys
            switch(name.toUpperCase()){
                case "SPACE": return InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_SPACE);
                case "ENTER": return InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_ENTER);
                case "TAB": return InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_TAB);
                case "ESCAPE": return InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_ESCAPE);
                case "LEFTSHIFT": case "LEFT_SHIFT": return InputUtil.Type.KEYSYM.createFromCode(GLFW.GLFW_KEY_LEFT_SHIFT); 

            }
        } catch(Exception e ) {
            e.printStackTrace();
        }
        return InputUtil.UNKNOWN_KEY;
    }
}
