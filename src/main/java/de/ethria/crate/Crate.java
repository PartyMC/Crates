package de.ethria.crate;

import de.ethria.Crates;
import de.ethria.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;

public class Crate {

    String displayName;
    Material displayItem;
    Material backGround;
    int price;

    ArrayList<CrateItem> crateItems;

    public Crate(String displayName, Material displayItem, Material backGround, int price) {
        this.displayName = displayName;
        this.displayItem = displayItem;
        this.backGround = backGround;
        this.price = price;
        loadCrateItems();
    }

    public void loadCrateItems() {
        this.crateItems = new ArrayList<>();
    }

    public void setCrateItems(ArrayList<CrateItem> crateItems) {
        this.crateItems = crateItems;
    }

    public void open(Player player) {
        ArrayList<ItemStack> tmp = new ArrayList<>();

        for (CrateItem item : crateItems) {
            for (int i = 0; i < item.chance; i++) {
                tmp.add(item.item);
            }
        }

        Collections.shuffle(tmp);

        Inventory inventory = Bukkit.createInventory(null, 27, "§k§l§o" + displayName);

        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemBuilder(backGround).setDisplayName(" ").build());
        }

        for (int i = 0; i < 9; i++) {
            inventory.setItem((9 + i), tmp.get(i));
        }

        inventory.setItem(4, new ItemBuilder(Material.HOPPER).setDisplayName("§7Gewinn").build());
        player.openInventory(inventory);
        int c = 0;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);
        c++;
        startFrame(player, inventory, tmp, c, false);

        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);
        c += 2;
        startFrame(player, inventory, tmp, c, false);


        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 5;
        startFrame(player, inventory, tmp, c, false);
        c += 30;
        startFrame(player, inventory, tmp, c, true);


    }

    public void startFrame(Player player, Inventory inventory, ArrayList<ItemStack> prices, int delay, boolean lastSpin) {
        System.out.println(delay);
        new BukkitRunnable() {
            int spins = 0;

            @Override
            public void run() {
                spins++;
                ItemStack stack = prices.get(0);
                prices.remove(0);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, (float) 0.5, (float) 0.5);
                prices.add(stack);

                for (int i = 0; i < 9; i++) {
                    inventory.setItem((9 + i), prices.get(i));
                }

                if (lastSpin) {
                    ItemStack win = inventory.getItem(13);
                    player.sendMessage("§7Du hast " + win.getItemMeta().getDisplayName() + " §7gewonnen!");
                    player.getInventory().addItem(win);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    this.cancel();
                }

            }
        }.runTaskLaterAsynchronously(Crates.getInstance(), delay);
    }
    public void setPlayerWin(Player player, ItemStack win) {
        if(win.getItemMeta().getDisplayName().equalsIgnoreCase("")) {

        }
    }
    public void getPrice(Player player, Inventory inventory) {
        ItemStack win = inventory.getItem(13);
    }

    public ItemStack getDisplay() {
        return new ItemBuilder(displayItem).setDisplayName(displayName).build();
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getDisplayItem() {
        return displayItem;
    }

    public Material getBackGround() {
        return backGround;
    }

    public ArrayList<CrateItem> getCrateItems() {
        return crateItems;
    }

    public int getPrice() {
        return price;
    }
}
