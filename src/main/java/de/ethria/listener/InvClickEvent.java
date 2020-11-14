package de.ethria.listener;

import de.ethria.Crates;
import de.ethria.crate.Crate;
import de.ethria.utils.MultipageInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvClickEvent implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        if (event.getInventory() == null) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }
        ItemStack itemStack = event.getCurrentItem();
        Inventory inventory = event.getInventory();

        if (player.getOpenInventory().getTitle().startsWith("§k§l§o")) {
            event.setResult(Event.Result.DENY);
            event.setCancelled(true);


            if (player.getOpenInventory().getTitle().startsWith("§k§l§o§3§lHintergund: ")) {

                String name = player.getOpenInventory().getTitle().split(": ")[1];
                Crates.getInstance().getCrateManager().changeBackgroundColor(player, name, itemStack.getType());

                return;
            }
            if (player.getOpenInventory().getTitle().startsWith("§k§l§o§3§lBearbeiten: ")) {
                String name = player.getOpenInventory().getTitle().split(": ")[1];
                if (itemStack.hasItemMeta()) {
                    if (itemStack.getItemMeta().getDisplayName().startsWith("§7Hintergrundfarbe")) {
                        Crates.getInstance().getCrateManager().openBackGroundInventory(player, Crates.getInstance().getCrateManager().getCrateByName(name));
                    } else if (itemStack.getItemMeta().getDisplayName().startsWith("§7Items:")) {
                        Crates.getInstance().getCrateManager().openCrateItems(player, Crates.getInstance().getCrateManager().getCrateByName(name));
                    } else if (itemStack.getItemMeta().getDisplayName().startsWith("§cTest Öffnung")) {
                        if(player.hasPermission("crate.admin.test.open")) {
                            Crate crate = Crates.getInstance().getCrateManager().getCrateByName(name);
                            player.closeInventory();
                            crate.open(player);
                        }

                    }
                }
                return;
            }
            if (player.getOpenInventory().getTitle().startsWith("§k§l§o§3§lCrates")) {
                Crates.getInstance().getCrateManager().openEditGUI(player, Crates.getInstance().getCrateManager().getCrateByName(itemStack.getItemMeta().getDisplayName()));
                return;
            }
            if (player.getOpenInventory().getTitle().startsWith("§k§l§o§3§lIetms hinzufügen: ")) {
                String name = player.getOpenInventory().getTitle().split(": ")[1];
                if (itemStack.hasItemMeta()) {
                    if (itemStack.getItemMeta().getDisplayName().startsWith("§aItem hinzufügen")) {
                        Crates.getInstance().getCrateManager().openCrateItemAddItem(player, Crates.getInstance().getCrateManager().getCrateByName(name));
                    }
                }
                return;
            }
            if (player.getOpenInventory().getTitle().startsWith("§k§l§o§3Item hinzufügen: ")) {
                String name = player.getOpenInventory().getTitle().split(": ")[1];

                if (!(event.getSlot() == 1 || event.getSlot() == 4 || event.getSlot() == 7)) {
                    player.getOpenInventory().getTopInventory().setItem(4, itemStack);
                }

                if (itemStack.hasItemMeta()) {
                    if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§aHinzufügen")) {
                        if (!inventory.getItem(4).getItemMeta().getDisplayName().equalsIgnoreCase("§cKein Item .... ")) {
                            Crates.getInstance().getCrateManager().addCrateItem(player, Crates.getInstance().getCrateManager().getCrateByName(name), inventory.getItem(4), 10);
                        }
                    }
                }
            }

            if (itemStack.hasItemMeta()) {
                if (itemStack.getItemMeta().getDisplayName().startsWith("§cAbbrechen")) {
                    player.closeInventory();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                } else if (itemStack.getItemMeta().getDisplayName().startsWith("§aCrate erstellen")) {
                    String name = inventory.getItem(1).getItemMeta().getDisplayName().split(": ")[1];
                    Material material = inventory.getItem(2).getType();
                    player.closeInventory();
                    Crates.getInstance().getCrateManager().createCrate(player, name, material);
                } else if (itemStack.getItemMeta().getDisplayName().startsWith("§aWeiter")) {
                    MultipageInventory.get(player).next();
                } else if (itemStack.getItemMeta().getDisplayName().startsWith("§cZurück")) {
                    MultipageInventory.get(player).back();
                } else if (itemStack.getItemMeta().getDisplayName().startsWith("§aItem hinzufügen")) {

                    String name = player.getOpenInventory().getTitle().split(": ")[1];
                    Crates.getInstance().getCrateManager().openCrateItemAdd(player, Crates.getInstance().getCrateManager().getCrateByName(name));
                }
            }

        }

    }

}
