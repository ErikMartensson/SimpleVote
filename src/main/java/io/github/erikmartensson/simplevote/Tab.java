package io.github.erikmartensson.simplevote;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Tab implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("vote")) {
            List<String> l = new ArrayList<>();
            if (strings.length == 1) {
                l.add("day");
                l.add("night");
            }
            return l;
        }
        return null;
    }
}