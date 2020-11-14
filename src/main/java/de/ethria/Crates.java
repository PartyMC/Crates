package de.ethria;

import de.ethria.command.CrateCommand;
import de.ethria.listener.InvClickEvent;
import de.ethria.manager.CrateManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Crates extends JavaPlugin {

    private static Crates instance;

    private CrateManager crateManager;

    @Override
    public void onEnable() {
        instance = this;
        crateManager = new CrateManager();

        Bukkit.getPluginManager().registerEvents(new InvClickEvent(), this);

        getCommand("crate").setExecutor(new CrateCommand());
    }

    @Override
    public void onDisable() {

    }

    public static Crates getInstance() {
        return instance;
    }


    public CrateManager getCrateManager() {
        return crateManager;
    }
}
