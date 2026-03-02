package use.spiral.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import net.minecraft.client.util.InputUtil;
import use.spiral.Spiral;
import use.spiral.utils.InputUtils;
import use.spiral.utils.Utils;

import use.spiral.module.Module;

public class KeybindManager {
    private static final File file = new File(Utils.getClientDir(), "Keybinds.txt");

    public static void save() {
        try {
            if(!file.exists()) file.createNewFile();
            FileWriter writer = new FileWriter(file);

            for(Module module : Spiral.moduleManager.getModules()) {
                InputUtil.Key key = module.getKeybind();

                if(key != InputUtil.UNKNOWN_KEY) {
                    String keyName = key.getTranslationKey()
                            .substring("key.keyboard.".length())
                            .toUpperCase();
                    writer.write(module.getName() + ": " + keyName + "\n");
                }
            }
            writer.flush();
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        try {
            if(!file.exists()) return;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                if(!line.contains(":")) continue;
                String[] split = line.split(":");
                String moduleName = split[0].trim();
                String key = split[1].trim();

                Module module = Spiral.moduleManager.getModuleByName(moduleName);
                if(module == null) continue;

                InputUtil.Key keyObject = InputUtils.getKey(key);
                if(keyObject == InputUtil.UNKNOWN_KEY) continue;

                module.setKeybind(keyObject);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}