package use.spiral.command.impl;

import use.spiral.Spiral;
import use.spiral.manager.FriendManager;
import use.spiral.command.Command;

public class FriendCommand extends Command {
    public FriendCommand() {
        super("friends", "Manage your friends", "friend");
    }

    @Override
    public void runCommand(String[] args) {
        if(args.length < 1) {
            Spiral.sendChatMessage("Usage: .friends <add/remove/list/clear>");
            return;
        }

        String action = args[0].toLowerCase();

        switch(action) {
            case "add": {
                if(args.length < 2) {
                    Spiral.sendChatMessage("Usage: .friends add <username>");
                    return;
                }

                String name = args[1];
                FriendManager.add(name);
                Spiral.sendChatMessage(name+" is now your friend!");
                break;
            }

            case "remove": {
                if(args.length < 2) {
                    Spiral.sendChatMessage("Usage: .friends remove <username>");
                    return;
                }

                String name = args[1];
                FriendManager.remove(name);
                Spiral.sendChatMessage(name+" was removed from friends.");
                break;
            }

            case "list": {
                if(FriendManager.getFriends().isEmpty()) {
                    Spiral.sendChatMessage("You have no friends ):");
                    return;
                }

                Spiral.sendChatMessage("Friends: ");
                for(String friend : FriendManager.getFriends()) {
                    Spiral.sendChatMessage(" - " + friend);
                }
                break;
            }

            case "clear": {
                FriendManager.clear();
                Spiral.sendChatMessage("Cleared all friends");
                break;
            }

            default: {
                Spiral.sendChatMessage("Usage: .friends add <username>");
                break;
            }
        }
    }
}