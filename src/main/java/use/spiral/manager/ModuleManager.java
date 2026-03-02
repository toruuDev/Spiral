package use.spiral.manager;

import use.spiral.module.Module;
import use.spiral.module.client.*;
import use.spiral.module.combat.*;
import use.spiral.module.player.KeepSprint;
import use.spiral.module.player.Sprint;
import use.spiral.module.visual.ModuleList;
import use.spiral.module.visual.Watermark;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        addModules(
            // Client
            new ClickGui(),
            new DiscordRPC(),

            // Combat
            new Aura(),
            new Criticals(),

            // Player
            new Sprint(),
            new KeepSprint(),

            // Visual
            new ModuleList(),
            new Watermark()
        );
    }

    public void addModules(Module... mods) {
        for(Module module : mods) {
            modules.add(module);
        }
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> theClass) {
        for(Module module : modules) {
            if(theClass.isInstance(module)) {
                return(T)module;
            }
        }
        return null;
    }

    public List<Module> getModulesByCategory(Module.Category category) {
        List<Module> result = new ArrayList<>();
        for(Module module : modules) {
            if(module.getCategory() == category) {
                result.add(module);
            }
        }
        return result;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public List<Module> getModules() {
        return modules;
    }
}
