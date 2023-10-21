package pl.mobslayer.util;

import org.bukkit.Location;
import org.bukkit.World;
import pl.mobslayer.MobSlayer;

public class LocationUtil {

    public static Location parseLocation(String locationString) {
        String[] split = locationString.split(" ");
        if(split.length != 4) {
            sendWrongFormatMessage(locationString + " (Error: Number of arguments)");
            return null;
        }
        try {
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            int z = Integer.parseInt(split[2]);
            World w = MobSlayer.getInstance().getServer().getWorld(split[3]);
            if(w == null) {
                sendWrongFormatMessage(locationString + " (Error: World)");
                return null;
            }
            return new Location(w, x, y, z);
        } catch(NumberFormatException e) {
            sendWrongFormatMessage(locationString + " (Error: Position)");
            return null;
        }
    }

    private static void sendWrongFormatMessage(String locationString) {
        MobSlayer.getInstance().getLogger().severe(locationString + " is in wrong location format!");
    }

}
