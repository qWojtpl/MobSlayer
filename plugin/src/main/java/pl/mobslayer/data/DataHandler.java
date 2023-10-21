package pl.mobslayer.data;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import pl.mobslayer.MobSlayer;
import pl.mobslayer.mobs.MobSchema;
import pl.mobslayer.util.LocationUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataHandler {

    private final MobSlayer plugin = MobSlayer.getInstance();
    private final MessagesManager messagesManager = plugin.getMessagesManager();
    private String managePermission;

    public void loadAll() {
        plugin.getMobsManager().killAll();
        plugin.getMobsManager().clearSchemas();
        messagesManager.clearMessages();
        loadConfig();
        loadMessages();
    }

    public void loadConfig() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getConfigFile());
        managePermission = yml.getString("managePermission", "mslayer.manage");
        ConfigurationSection section = yml.getConfigurationSection("mobs");
        if(section == null) {
            return;
        }
        for(String mobName : section.getKeys(false)) {
            String path = "mobs." + mobName + ".";
            MobSchema schema = new MobSchema(mobName);
            try {
                schema.setEntityType(EntityType.valueOf(yml.getString(path + "type", "").toUpperCase()));
            } catch(IllegalArgumentException e) {
                plugin.getLogger().severe("Not found mob type: " + yml.getString(path + "type"));
                continue;
            }
            schema.setMaxHealth(yml.getDouble(path + "maxHealth"));
            schema.setMovementSpeed(yml.getDouble(path + "movementSpeed"));
            schema.setAttackDamage(yml.getDouble(path + "attackDamage"));
            schema.setAttackSpeed(yml.getDouble(path + "attackSpeed"));
            schema.setMobName(yml.getString(path + "mobName", "").replace("&", "§"));
            schema.addSpawnHours(yml.getStringList(path + "spawnHours"));
            List<String> locations = yml.getStringList(path + "spawnLocations");
            boolean skip = false;
            for(String location : locations) {
                Location loc = LocationUtil.parseLocation(location);
                if(loc == null) {
                    MobSlayer.getInstance().getLogger().severe("Error while loading mob " + mobName + " - incorrect location format.");
                    skip = true;
                    break;
                }
                schema.addSpawnLocation(loc);
            }
            if(skip) {
                continue;
            }
            schema.setSpawnMessage(yml.getString(path + "spawnMessage", "").replace("&", "§"));
            schema.setKillMessage(yml.getString(path + "killMessage", "").replace("&", "§"));
            ConfigurationSection rewardSection = yml.getConfigurationSection(path + "rewards");
            if(rewardSection != null) {
                for(String rewardKey : rewardSection.getKeys(false)) {
                    schema.addReward(rewardKey, ItemLoader.getItemStackList(yml, path + "rewards." + rewardKey));
                }
            }
            plugin.getMobsManager().addSchema(schema);
        }
        plugin.getLogger().info("Loaded (" + plugin.getMobsManager().getSchemas().size() + ") mobs.");
    }

    public void loadMessages() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getMessagesFile());
        ConfigurationSection section = yml.getConfigurationSection("messages");
        if(section == null) {
            return;
        }
        for(String key : section.getKeys(false)) {
            messagesManager.addMessage(key, yml.getString("messages." + key));
        }
    }

    public void setConfigValue(String key, Object value) {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getConfigFile());
        yml.set(key, value);
        try {
            yml.save(getConfigFile());
        } catch(IOException e) {
            plugin.getLogger().severe("Cannot save config file!");
            e.printStackTrace();
        }
    }

    public File getConfigFile() {
        return getFile("config.yml");
    }

    public File getMessagesFile() {
        return getFile("messages.yml");
    }

    public File getFile(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if(!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        return file;
    }

    public String getManagePermission() {
        return this.managePermission;
    }

}
