package pl.mobslayer.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import pl.mobslayer.MobSlayer;
import pl.mobslayer.mobs.Mob;
import pl.mobslayer.mobs.MobsManager;

public class Events implements Listener {

    private final MobSlayer plugin = MobSlayer.getInstance();
    private final MobsManager mobsManager = plugin.getMobsManager();

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        Mob mob = mobsManager.getMobByEntity(event.getEntity());
        if(mob == null) {
            return;
        }
        event.getDrops().clear();
        event.setDroppedExp(0);
        mobsManager.killMob(mob, true);
    }

    @EventHandler
    public void onMobDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) {
            return;
        }
        Mob mob = mobsManager.getMobByEntity(event.getEntity());
        if(mob == null) {
            return;
        }
        double damage = event.getFinalDamage();
        Player player = (Player) event.getDamager();
        if(damage > mob.getLivingEntity().getHealth()) {
            damage = mob.getLivingEntity().getHealth();
        }
        mob.registerDamage(player.getName(), damage);
    }

    @EventHandler
    public void onMobPotionEffect(EntityPotionEffectEvent event) {
        Mob mob = mobsManager.getMobByEntity(event.getEntity());
        if(mob == null) {
            return;
        }
        event.setCancelled(true);
    }

}
