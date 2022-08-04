package io.github.erikmartensson.simplevote;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class Poll {
    private final String option;
    private Date started;
    private LinkedHashMap<UUID, Player> votes = new LinkedHashMap<>();
    private Timer timer = new Timer();

    public Poll(String option) {
        this.option = option;
    }

    public void vote(Player player) {
        UUID playerId = player.getUniqueId();
        if (votes.containsKey(playerId)) {
            player.sendMessage("You have already voted");
            return;
        }

        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int votesNeededToPass = (int) Math.ceil(onlinePlayers / 2.0);
        //int votesNeededToPass = 2;
        Bukkit.getLogger().info(String.valueOf(onlinePlayers));
        Bukkit.getLogger().info(String.valueOf(votesNeededToPass));
        String playerName = player.getDisplayName();

        votes.put(playerId, player);

        // If a vote isn't already ingoing, set one up now
        if (started == null) {
            started = new Date();
            TimerTask task = new TimerTask() {
                public void run() {
                    fail();
                }
            };
            timer.schedule(task, 30000L);
            Bukkit.broadcastMessage(playerName + ChatColor.GOLD +  " wants " + option + " - [1/" + votesNeededToPass + "] | /vote " + option);
        } else {
            Bukkit.broadcastMessage(playerName + ChatColor.GOLD + " also wants " + option + " - [" + votes.size() + "/" + votesNeededToPass + "]");
        }

        if (votes.size() >= votesNeededToPass) {
            pass();
        }
    }

    public boolean isActive() {
        return started != null;
    }

    public void pass() {
        if (option.equals("day")) {
            setDay();
        } else {
            setNight();
        }
        Bukkit.broadcastMessage(ChatColor.GOLD + "It's now " + this.option + "!");
        reset();
    }

    private void fail() {
        Bukkit.broadcastMessage(ChatColor.GRAY + "Vote timed out, not enough votes");
        reset();
    }

    private void reset() {
        started = null;
        timer.cancel();
        timer = new Timer();
        votes.clear();
    }

    private void setDay() {
        Objects.requireNonNull(Bukkit.getWorld("world")).setTime(0);
    }

    private void setNight() {
        Objects.requireNonNull(Bukkit.getWorld("world")).setTime(13000);
    }
}
