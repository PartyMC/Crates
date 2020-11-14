package de.ethria.utils;
/*
This class was created on 01.10.2020 by iTreon
The class is a part of the project SpigotCore
*/

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class MultipageInventory {

    private static HashMap<Player, MultipageInventory> cache = new HashMap<Player, MultipageInventory>();

    private final Player player;
    private ArrayList<ItemStack> itemStacks;
    private int page;
    private String title;
    private int rows;
    private Inventory inventory;

    private HashMap<Integer, ItemStack> setItems;


    public MultipageInventory(Player player) {
        this.itemStacks = new ArrayList<>();
        this.player = player;
        this.page = 0;
        this.setItems = new HashMap<>();
    }

    public static MultipageInventory get(Player player) {
        if (!cache.containsKey(player))
            cache.put(player, new MultipageInventory(player));
        return cache.get(player);
    }

    public static MultipageInventory getNew(Player player) {
        cache.put(player, new MultipageInventory(player));
        return cache.get(player);
    }

    public void setRows(int rows) {

        if (rows <= 1) {
            rows = 3;
        }

        this.rows = rows;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addItemStack(ItemStack itemStack) {
        itemStacks.add(itemStack);
    }

    public int getSize() {
        return 9 * rows;
    }

    public void setItemStack(int i, ItemStack itemStack) {
        setItems.put(i, itemStack);
    }

    public void open() {
        inventory = Bukkit.createInventory(null, (9 * rows), "§k§l§o" + title);
        for (int i = getSize() - 18; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").removeAllAtributs().build());
        }

        inventory.setItem(inventory.getSize() - 1, new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§aWeiter").build());

        inventory.setItem(inventory.getSize() - 2, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cZurück").build());

        inventory.setItem(inventory.getSize() - 5, new ItemBuilder(Material.ANVIL).setDisplayName("§aItem hinzufügen").build());

        setItems.forEach((integer, itemStack) -> {
            inventory.setItem(integer, itemStack);
        });


        for (int i = 0; i < (inventory.getSize() - 18); i++) {
            int index = ((inventory.getSize() - 18) * page) + i;

            if (index < itemStacks.size()) {
                inventory.setItem(i, itemStacks.get(index));
            }
        }


        player.openInventory(inventory);
    }

    public void next() {
        page++;
        open();
    }

    public void back() {
        if (page >= 1) {
            page--;
            open();
        }
    }

    public HashMap<Integer, ItemStack> getSetItems() {
        return setItems;
    }
}