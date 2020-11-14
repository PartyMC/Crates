package de.ethria.manager;

import de.ethria.Crates;
import de.ethria.crate.Crate;
import de.ethria.key.Key;
import de.ethria.utils.ItemBuilder;
import de.tr7zw.nbtapi.NBTItem;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class KeyManager {

    public void openShop(Player player, Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§k§l§o§3Keys: " + crate.getDisplayName());


        ItemBuilder builder = new ItemBuilder(Material.TRIPWIRE_HOOK);
        builder.setDisplayName("§3§lCrateKey: " + crate.getDisplayName());
        builder.addEnchantment(Enchantment.DURABILITY, 1);
        builder.removeAllAtributs();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7Kaufe §e1x §7Key:");
        lore.add("§7Preis: §e" + crate.getPrice() + " €");
        builder.addLore(lore);


        NBTItem item = new NBTItem(builder.build());
        item.setInteger("GUI_KEY_BUY", crate.getPrice());
        item.setString("GUI_KEY", Crates.getInstance().getCrateManager().getRawName(crate.getDisplayName()));
        inventory.setItem(2, item.getItem());


        builder = new ItemBuilder(Material.TRIPWIRE_HOOK, 5);
        builder.setDisplayName("§3§lCrateKey: " + crate.getDisplayName());
        builder.addEnchantment(Enchantment.DURABILITY, 1);
        builder.removeAllAtributs();
        lore = new ArrayList<>();
        lore.add("");
        lore.add("§7Kaufe §e5x §7Key:");
        lore.add("§7Preis: §7§m" + (crate.getPrice() * 5) + "§e " + (int) (Math.round(crate.getPrice() * 5) * 0.95) + " € §8[§b- 5%§8]");
        builder.addLore(lore);


        item = new NBTItem(builder.build());
        item.setInteger("GUI_KEY_BUY", (int) (Math.round(crate.getPrice() * 5) * 0.95));
        item.setString("GUI_KEY", Crates.getInstance().getCrateManager().getRawName(crate.getDisplayName()));
        inventory.setItem(4, item.getItem());

        builder = new ItemBuilder(Material.TRIPWIRE_HOOK, 10);

        builder.setDisplayName("§3§lCrateKey: " + crate.getDisplayName());
        builder.addEnchantment(Enchantment.DURABILITY, 1);
        builder.removeAllAtributs();
        lore = new ArrayList<>();
        lore.add("");
        lore.add("§7Kaufe §e5x §7Key:");
        lore.add("§7Preis: §7§m" + (crate.getPrice() * 10) + "§e " + (int) (Math.round(crate.getPrice() * 10) * 0.90) + " € §8[§b- 10%§8]");
        builder.addLore(lore);


        item = new NBTItem(builder.build());
        item.setInteger("GUI_KEY_BUY", (int) (Math.round(crate.getPrice() * 10) * 0.90));
        item.setString("GUI_KEY", Crates.getInstance().getCrateManager().getRawName(crate.getDisplayName()));
        inventory.setItem(6, item.getItem());
        player.openInventory(inventory);
    }

}
