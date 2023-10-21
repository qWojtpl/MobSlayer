package pl.mobslayer;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mobslayer.commands.Commands;
import pl.mobslayer.data.DataHandler;
import pl.mobslayer.events.Events;
import pl.mobslayer.mobs.Mob;
import pl.mobslayer.mobs.MobsManager;

public final class MobSlayer extends JavaPlugin {

    private static MobSlayer main;
    private Commands commands;
    private DataHandler dataHandler;
    private Events events;
    private MobsManager mobsManager;

    @Override
    public void onEnable() {
        main = this;
        this.commands = new Commands();
        this.dataHandler = new DataHandler();
        this.events = new Events();
        this.mobsManager = new MobsManager();
        getServer().getPluginManager().registerEvents(events, this);
        PluginCommand command = getCommand("mobslayer");
        if(command != null) {
            command.setExecutor(commands);
        }
        dataHandler.loadConfig();
        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        for(Mob mob : mobsManager.getMobs()) {
            mobsManager.killMob(mob);
        }
        getLogger().info("Disabled.");
    }

    public static MobSlayer getInstance() {
        return main;
    }

    public Commands getCommands() {
        return this.commands;
    }

    public DataHandler getDataHandler() {
        return this.dataHandler;
    }

    public Events getEvents() {
        return this.events;
    }

    public MobsManager getMobsManager() {
        return this.mobsManager;
    }

}
