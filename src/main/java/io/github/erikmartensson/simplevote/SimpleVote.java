package io.github.erikmartensson.simplevote;

import org.bukkit.plugin.java.JavaPlugin;

public class SimpleVote extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getCommand("vote").setExecutor(new CommandVote());
        this.getCommand("vote").setTabCompleter(new Tab());
        getLogger().info("Plugin enabled");
    }
    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
    }
}
