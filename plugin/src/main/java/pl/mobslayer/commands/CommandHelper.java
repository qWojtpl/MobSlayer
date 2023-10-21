package pl.mobslayer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mobslayer.MobSlayer;

import java.util.ArrayList;
import java.util.List;

public class CommandHelper implements TabCompleter {

    private final MobSlayer plugin = MobSlayer.getInstance();

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if(sender instanceof Player) {
            if(!sender.hasPermission(plugin.getDataHandler().getManagePermission())) {
                return completions;
            }
        }
        if(args.length == 1) {
            completions.add("reload");
            completions.add("spawn");
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("spawn")) {
                completions.addAll(plugin.getMobsManager().getSchemasNames());
            }
        }
        return StringUtil.copyPartialMatches(args[args.length-1], completions, new ArrayList<>());
    }

}
