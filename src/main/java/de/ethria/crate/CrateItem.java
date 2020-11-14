package de.ethria.crate;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CrateItem {

    ItemStack item;
    int chance;

    public CrateItem(ItemStack item, int chance) {
        this.item = item;
        this.chance = chance;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getChance() {
        return chance;
    }

    public ItemStack getInfoItem() {
        ItemStack itemStack = item.clone();
        ItemMeta meta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§aWahrscheinlichkeit: §7" + chance + "§a%");
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
