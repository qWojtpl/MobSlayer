package pl.mobslayer.util;

import org.bukkit.entity.Player;
import pl.mobslayer.MobSlayer;

import javax.annotation.Nullable;

public class PlayerUtil {

    @Nullable
    public static Player getPlayer(String nickname) {
        for(Player player : MobSlayer.getInstance().getServer().getOnlinePlayers()) {
            if(player.getName().equals(nickname)) {
                return player;
            }
        }
        return null;
    }

}
