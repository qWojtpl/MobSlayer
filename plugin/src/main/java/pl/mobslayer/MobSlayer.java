package pl.mobslayer;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import pl.mobslayer.commands.CommandHelper;
import pl.mobslayer.commands.Commands;
import pl.mobslayer.data.DataHandler;
import pl.mobslayer.data.MessagesManager;
import pl.mobslayer.events.Events;
import pl.mobslayer.mobs.Mob;
import pl.mobslayer.mobs.MobsManager;

public final class MobSlayer extends JavaPlugin {

    private static MobSlayer main;
    private MessagesManager messagesManager;
    private Commands commands;
    private CommandHelper commandHelper;
    private MobsManager mobsManager;
    private Events events;
    private DataHandler dataHandler;

    @Override
    public void onEnable() {
        main = this;
        this.messagesManager = new MessagesManager();
        this.commands = new Commands();
        this.commandHelper = new CommandHelper();
        this.mobsManager = new MobsManager();
        this.events = new Events();
        this.dataHandler = new DataHandler();
        getServer().getPluginManager().registerEvents(events, this);
        PluginCommand command = getCommand("mobslayer");
        if(command != null) {
            command.setExecutor(commands);
            command.setTabCompleter(commandHelper);
        }
        dataHandler.loadAll();
        mobsManager.startCheckTask();
        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        mobsManager.killAll();
        getLogger().info("Disabled.");
    }

    public static MobSlayer getInstance() {
        return main;
    }

    public MessagesManager getMessagesManager() {
        return this.messagesManager;
    }

    public Commands getCommands() {
        return this.commands;
    }

    public CommandHelper getCommandHelper() {
        return this.commandHelper;
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
