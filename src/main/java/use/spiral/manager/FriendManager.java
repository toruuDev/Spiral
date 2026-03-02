package use.spiral.manager;

import net.minecraft.entity.player.PlayerEntity;
import use.spiral.Spiral;
import use.spiral.utils.Utils;

import java.io.*;
import java.util.*;

@SuppressWarnings("all")
public class FriendManager {
    private static final Set<String> friendsList = new HashSet<>();
    private static final File friendsFile = new File(Utils.getClientDir(), "/Friends.txt");

    public static void add(String name) {
        if(name != null && !name.isEmpty()) {
            String lower = name.toLowerCase();
            friendsList.add(lower);
            saveFriends(); // to file
        }
    }

    public static void remove(String name) {
        if(name != null && !name.isEmpty()) {
            String lower = name.toLowerCase();
            friendsList.remove(lower);
            saveFriends(); // to file
        }
    }

    public static boolean isFriend(String name) {
        return name != null && friendsList.contains(name.toLowerCase());
    }

    public static boolean isFriend(PlayerEntity player) {
        return player != null && isFriend(player.getGameProfile().getName());
    }

    public static Set<String> getFriends() {
        return friendsList;
    }

    public static void clear() {
        friendsList.clear();
        saveFriends(); // to file
    }

    public static void saveFriends() {
        try {
            if(!friendsFile.getParentFile().exists()) {
                friendsFile.getParentFile().mkdirs();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(friendsFile))) {
                for (String friend : friendsList) {
                    writer.write(friend);
                    writer.newLine();
                }
            }
        } catch(IOException e) {
            Spiral.sendChatMessage(e.toString());
        }
    }

    public static void loadFriends() {
        friendsList.clear();
        if(friendsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(friendsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if(!line.trim().isEmpty()) {
                        friendsList.add(line.trim().toLowerCase());
                    }
                }
            } catch(IOException err) {
                Spiral.sendChatMessage(err.toString());
            }
        }
    }

    public static void initialize() {
        loadFriends();
    }
}