package pl.mobslayer.data;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import pl.mobslayer.MobSlayer;
import pl.mobslayer.mobs.MobSchema;

import java.io.File;

public class DataHandler {

    private final MobSlayer plugin = MobSlayer.getInstance();

    public void loadConfig() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getConfigFile());
        ConfigurationSection section = yml.getConfigurationSection("mobs");
        if(section == null) {
            return;
        }
        for(String mobName : section.getKeys(false)) {
            String path = "mobs." + mobName + ".";
            MobSchema schema = new MobSchema(mobName);
            try {
                schema.setEntityType(EntityType.valueOf(yml.getString(path + "type")));
            } catch(IllegalArgumentException e) {
                plugin.getLogger().severe("Not found mob type: " + yml.getString(path + "type"));
                continue;
            }
            plugin.getMobsManager().addSchema(schema);
        }
    }

    public File getConfigFile() {
        return getFile("config.yml");
    }

    public File getFile(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if(!file.exists()) {
            plugin.saveResource(fileName, false);
        }
        return file;
    }

}
