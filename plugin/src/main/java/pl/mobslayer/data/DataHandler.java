package pl.mobslayer.data;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.mobslayer.MobSlayer;

import java.io.File;

public class DataHandler {

    private final MobSlayer plugin = MobSlayer.getInstance();

    public void loadConfig() {
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(getConfigFile());

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
