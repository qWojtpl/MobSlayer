package pl.mobslayer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.mobslayer.MobSlayer;
import pl.mobslayer.mobs.MobSchema;

public class Commands implements CommandExecutor {

    private final MobSlayer plugin = MobSlayer.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            if(!sender.hasPermission(plugin.getDataHandler().getManagePermission())) {
                sender.sendMessage("§cYou don't have permission.");
                return true;
            }
        }
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("reload")) {
                plugin.getDataHandler().loadAll();
                sender.sendMessage("§aReloaded.");
            } else if(args[0].equalsIgnoreCase("spawn")) {
                if(args.length > 1) {
                    MobSchema schema = plugin.getMobsManager().getSchemaByName(args[1]);
                    if(schema == null) {
                        sender.sendMessage("§cCannot find mob: " + args[1]);
                        return true;
                    }
                    plugin.getMobsManager().spawnMob(schema);
                    sender.sendMessage("§aSuccessfully spawned mob: " + args[1]);
                } else {
                    sender.sendMessage("§c/mslayer spawn <name>");
                }
            }
        } else {
            sender.sendMessage("§cCorrect usage: /mslayer <reload|spawn> [name]");
        }
        return true;
    }

}
