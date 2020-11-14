package de.ethria.listener;

import de.ethria.Crates;
import de.ethria.crate.Crate;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.smartcardio.CardTerminal;

public class Interact implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (player.getInventory().getItemInMainHand() == null) {
                Location location = event.getClickedBlock().getLocation();
                for (Location l : Crates.getInstance().getCrateManager().getCrateLocation().keySet()) {
                    if (l.distance(location) == 0) {
                        event.setCancelled(true);
                        Crate crate = Crates.getInstance().getCrateManager().getCrateLocation().get(location);
                        Crates.getInstance().getKeyManager().openShop(player, crate);
                    }
                }
            }

            if (player.getInventory().getItemInMainHand().getType() == Material.TRIPWIRE_HOOK) {
                NBTItem item = new NBTItem(player.getInventory().getItemInMainHand());
                if (item.hasKey("KEY_ID")) {
                    String rawCrate = item.getString("KEY_CRATE");
                    Crate crate = Crates.getInstance().getCrateManager().getCrateByName(rawCrate);
                    Location location = event.getClickedBlock().getLocation();
                    for (Location l : Crates.getInstance().getCrateManager().getCrateLocation().keySet()) {
                        if (l.distance(location) == 0) {
                            event.setCancelled(true);
                            Crate checkCrate = Crates.getInstance().getCrateManager().getCrateLocation().get(location);

                            if (checkCrate.getDisplayName().equalsIgnoreCase(crate.getDisplayName())) {
                                crate.open(player);
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                            } else {
                                Crates.getInstance().getKeyManager().openShop(player, crate);
                            }
                        }
                    }
                }
            }else {
                Location location = event.getClickedBlock().getLocation();
                for (Location l : Crates.getInstance().getCrateManager().getCrateLocation().keySet()) {
                    if (l.distance(location) == 0) {
                        event.setCancelled(true);
                        Crate crate = Crates.getInstance().getCrateManager().getCrateLocation().get(location);
                        Crates.getInstance().getKeyManager().openShop(player, crate);
                    }
                }
            }


        }
    }

}
