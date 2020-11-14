package de.ethria.listener;

import de.ethria.Crates;
import de.ethria.crate.Crate;
import de.ethria.manager.FileManager;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (Crates.getInstance().getCrateCache().containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            Crate crate = Crates.getInstance().getCrateCache().get(player.getUniqueId());
            Block block = event.getBlock();
            block.setType(crate.getDisplayItem());

            String rawName = Crates.getInstance().getCrateManager().getRawName(crate.getDisplayName());

            FileManager manager = new FileManager("config");
            YamlConfiguration configuration = manager.getConfiguration();
            configuration.set("loc." + rawName + ".world", block.getLocation().getWorld().getName());
            configuration.set("loc." + rawName + ".x", block.getLocation().getX());
            configuration.set("loc." + rawName + ".y", block.getLocation().getY());
            configuration.set("loc." + rawName + ".z", block.getLocation().getZ());
            manager.save();


            player.sendMessage("§aKiste für " + crate.getDisplayName() + " §awurde gesetzt!");
            Crates.getInstance().getCrateCache().remove(player.getUniqueId());
            Crates.getInstance().getCrateManager().getCrateLocation().put(block.getLocation(), crate);
        } else {
            return;
        }
    }

}
