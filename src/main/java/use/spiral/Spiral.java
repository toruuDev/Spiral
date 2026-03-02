package use.spiral;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import use.spiral.event.EventBus;
import use.spiral.event.InitializeFabricEvents;
import use.spiral.manager.CommandManager;
import use.spiral.manager.FriendManager;
import use.spiral.manager.KeybindManager;
import use.spiral.manager.ModuleManager;
import use.spiral.utils.Utils;

import java.io.File;

public class Spiral implements ModInitializer {
	public static final String MOD_ID = "spiral";
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final String WEBSITE = "https://github.com/toruuDev/spiral";

    public static EventBus eventBus;
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;

    @Override
    public void onInitialize() {

        eventBus = new EventBus();
        InitializeFabricEvents.start();

        moduleManager = new ModuleManager();
        commandManager = new CommandManager();

        FriendManager.initialize();
        KeybindManager.load();

		LOGGER.info("Initialized Spiral!");
        initializeFileDirectories();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

        }));
	}

    public static void sendChatMessage(String message) {
        if(mc.player == null || mc.world == null) return;
        Text spiral = Text.literal("Spiral").styled(s -> s.withColor(0x1546A0));

        Text main = Text.literal("")
                .append(spiral)
                .append(Text.literal(" > ").styled(s -> s.withColor(0x2B2B2B)))
                .append(Text.literal(message));

        mc.player.sendMessage(main, false);
    }

    public static void initializeFileDirectories() {
        File clientDir = new File(Utils.getClientDir());
    }
}