package use.spiral.module.client;


import java.time.Instant;

import meteordevelopment.discordipc.*;
import use.spiral.Spiral;
import use.spiral.module.Module;

@SuppressWarnings("all")
public class DiscordRPC extends Module {
    // private BoolSetting states;
    private long clientIdentifier = 1478098047018991857L;

    public DiscordRPC() {
        super("DiscordRPC", "Shows the Discord RPC", Category.Client);
        this.isEnabled();
    }
    private Thread rpcThread;

    @Override
    public void onEnable() {
        boolean started = DiscordIPC.start(clientIdentifier, () -> {});

        if(!started) {
            Spiral.sendChatMessage("Failed to connect to Discord RPC. is discord open?");
            onDisable();
            return;
        }

        rpcThread = new Thread(() -> {
            RichPresence presence = new RichPresence();
            presence.setLargeImage("a", Spiral.WEBSITE);
            presence.setStart(Instant.now().getEpochSecond());

            String scrollText;

            while(isEnabled()) {
                scrollText =
                        " Playing on " + getServerIp() +
                                "\n" + getEnabledModules() + "/" + getTotalModules() +
                                " modules enabled.";

                presence.setDetails(scrollText);
                DiscordIPC.setActivity(presence);

                scrollText = scrollText.substring(1) + scrollText.charAt(0);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Discord RPC Thread");
        rpcThread.start();
    }

    @Override
    public void onDisable() {
        DiscordIPC.stop();
    }

    private String getServerIp() {
        if(Spiral.mc.isInSingleplayer()) {
            return "Singleplayer";
        } else if(Spiral.mc.getCurrentServerEntry() != null) {
            return Spiral.mc.getCurrentServerEntry().address;
        } else {
            return "Unknown";
        }
    }

    private int getTotalModules() {
        return Spiral.moduleManager.getModules().size();
    }

    private int getEnabledModules() {
        return (int) Spiral.moduleManager.getModules()
                .stream()
                .filter(Module::isEnabled)
                .count();
    }
}

