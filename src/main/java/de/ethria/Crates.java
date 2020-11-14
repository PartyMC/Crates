package de.ethria;

import de.ethria.command.CrateCommand;
import de.ethria.crate.Crate;
import de.ethria.listener.BlockBreak;
import de.ethria.listener.Interact;
import de.ethria.listener.InvClickEvent;
import de.ethria.manager.CrateManager;
import de.ethria.manager.KeyManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Crates extends JavaPlugin {

    private static Crates instance;

    private Economy economy;

    private CrateManager crateManager;
    private KeyManager keyManager;

    private HashMap<UUID, Crate> crateCache;

    @Override
    public void onEnable() {
        instance = this;
        crateManager = new CrateManager();
        crateCache = new HashMap<>();
        keyManager = new KeyManager();

        Bukkit.getPluginManager().registerEvents(new InvClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreak(), this);
        Bukkit.getPluginManager().registerEvents(new Interact(), this);

        getCommand("crate").setExecutor(new CrateCommand());

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp.getProvider();

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

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public HashMap<UUID, Crate> getCrateCache() {
        return crateCache;
    }

    public Economy getEconomy() {
        return economy;
    }
}
