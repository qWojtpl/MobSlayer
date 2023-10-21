package pl.mobslayer.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import pl.mobslayer.MobSlayer;
import pl.mobslayer.mobs.Mob;
import pl.mobslayer.mobs.MobsManager;

public class Events implements Listener {

    private final MobSlayer plugin = MobSlayer.getInstance();
    private final MobsManager mobsManager = plugin.getMobsManager();

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        for(Mob mob : mobsManager.getMobs()) {
            if(event.getEntity().equals(mob.getEntity())) {
                mobsManager.killMob(mob);
                return;
            }
        }
    }

    @EventHandler
    public void onMobDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) {
            return;
        }
        
    }

}
