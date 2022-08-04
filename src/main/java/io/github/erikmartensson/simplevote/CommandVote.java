package io.github.erikmartensson.simplevote;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandVote implements CommandExecutor {
    private Poll dayPoll = new Poll("day");
    private Poll nightPoll = new Poll("night");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "You need to specify what you want to vote for");
            return false;
        }
        String option = args[0];
        if (!option.equals("day") && !option.equals("night")) {
            sender.sendMessage(ChatColor.RED + "Invalid vote option - day/night");
            return false;
        }

        if (option.equals("day") && this.nightPoll.isActive()) {
            sender.sendMessage(ChatColor.YELLOW + "A night vote is already in progress");
            return false;
        }

        if (option.equals("night") && this.dayPoll.isActive()) {
            sender.sendMessage(ChatColor.YELLOW + "A day vote is already in progress");
            return false;
        }

        Poll poll = option.equals("day") ? this.dayPoll : nightPoll;

        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        if (onlinePlayers <= 1) {
            // Only one person online? Pass immediately
            sender.sendMessage(ChatColor.GRAY + "Forever alone :'(");
            poll.pass();
        } else {
            poll.vote((Player) sender);
        }

        return true;
    }
}
