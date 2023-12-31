package pl.mobslayer.mobs;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.mobslayer.MobSlayer;
import pl.mobslayer.data.MessagesManager;
import pl.mobslayer.util.DateManager;
import pl.mobslayer.util.PlayerUtil;

import javax.annotation.Nullable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobsManager {

    private final MobSlayer plugin = MobSlayer.getInstance();
    private final MessagesManager messagesManager = plugin.getMessagesManager();
    private final List<MobSchema> schemas = new ArrayList<>();
    private final List<Mob> mobs = new ArrayList<>();

    public void addSchema(MobSchema schema) {
        schemas.add(schema);
    }

    public void removeSchema(MobSchema schema) {
        schemas.remove(schema);
    }

    public void clearSchemas() {
        schemas.clear();
    }

    @Nullable
    public MobSchema getSchemaByName(String name) {
        for(MobSchema schema : schemas) {
            if(schema.getName().equals(name)) {
                return schema;
            }
        }
        return null;
    }

    public List<MobSchema> getSchemas() {
        return new ArrayList<>(schemas);
    }

    public List<String> getSchemasNames() {
        List<String> names = new ArrayList<>();
        for(MobSchema schema : schemas) {
            names.add(schema.getName());
        }
        return names;
    }

    public void spawnMob(MobSchema schema) {
        Mob mob = new Mob(schema);
        mob.spawnMob();
        plugin.getServer().broadcastMessage(schema.getSpawnMessage());
        mobs.add(mob);
    }

    public void killAll() {
        List<Mob> mobs = new ArrayList<>(getMobs());
        for(Mob mob : mobs) {
            killMob(mob, false);
        }
    }

    public void killMob(Mob mob, boolean byPlayer) {
        if(mob == null) {
            return;
        }
        if(!mobs.contains(mob)) {
            return;
        }
        mobs.remove(mob);
        if(!byPlayer) {
            mob.getLivingEntity().setHealth(0);
        }
        HashMap<String, Double> playerDamage = mob.getPlayerDamage();
        HashMap<String, Double> sortedList = new HashMap<>();
        for(int i = 0; i < playerDamage.size(); i++) {
            double max = 0;
            String maxPlayer = "";
            for(String player : playerDamage.keySet()) {
                if(sortedList.containsKey(player)) {
                    continue;
                }
                if(PlayerUtil.getPlayer(player) == null) {
                    continue;
                }
                if(playerDamage.get(player) > max) {
                    max = playerDamage.get(player);
                    maxPlayer = player;
                }
            }
            if(!maxPlayer.equals("")) {
                sortedList.put(maxPlayer, playerDamage.get(maxPlayer) / mob.getMaxDamage() * 100);
                Player p = PlayerUtil.getPlayer(maxPlayer);
                if(p != null) {
                    p.sendMessage(MessageFormat.format(messagesManager.getMessage("finalDamagePercent"),
                            sortedList.get(maxPlayer),
                            mob.getSchema().getMobName(),
                            String.valueOf(i + 1)
                    ));
                    if(i >= 3) {
                        p.sendMessage(messagesManager.getMessage("noRewards"));
                    }
                }
                if(i == 0) {
                    plugin.getServer().broadcastMessage(MessageFormat.format(mob.getSchema().getKillMessage(),
                            maxPlayer,
                            sortedList.get(maxPlayer)
                    ));
                }
            }
        }
        int i = 0;
        for(String player : sortedList.keySet()) {
            if(i >= 3) {
                continue;
            }
            Player p = PlayerUtil.getPlayer(player);
            if(p == null) {
                continue;
            }
            List<ItemStack> rewards = mob.getSchema().getReward("top_" + i++);
            p.sendMessage(messagesManager.getMessage("rewards"));
            for(ItemStack is : rewards) {
                HashMap<Integer, ItemStack> rest = p.getInventory().addItem(is);
                if(is.getItemMeta() == null) {
                    continue;
                }
                if(!is.getItemMeta().getDisplayName().equals("")) {
                    p.sendMessage(MessageFormat.format(messagesManager.getMessage("rewardRecord"),
                            is.getItemMeta().getDisplayName(),
                            is.getAmount()
                    ));
                } else {
                    p.sendMessage(MessageFormat.format(messagesManager.getMessage("rewardRecord"),
                            is.getType().name(),
                            is.getAmount()
                    ));
                }
                for(ItemStack restIs : rest.values()) {
                    if(p.getLocation().getWorld() != null) {
                        p.getLocation().getWorld().dropItem(p.getLocation(), restIs);
                    }
                }
            }
        }
    }

    public List<Mob> getMobs() {
        return new ArrayList<>(mobs);
    }

    @Nullable
    public Mob getMobByEntity(Entity entity) {
        if(entity == null) {
            return null;
        }
        for(Mob mob : mobs) {
            if(entity.equals(mob.getEntity())) {
                return mob;
            }
        }
        return null;
    }

    public void startCheckTask() {
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            String currentHour = DateManager.getFormatHour() + ":" + DateManager.getFormatMinute();
            for(MobSchema schema : schemas) {
                if(schema.getSpawnHours().contains(currentHour)) {
                    spawnMob(schema);
                }
            }
        }, 0L, 20L * 60);
    }

}
