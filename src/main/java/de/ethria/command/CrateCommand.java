package de.ethria.command;

import de.ethria.Crates;
import de.ethria.utils.ItemBuilder;
import de.ethria.crate.Crate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class CrateCommand implements TabExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player))
            return false;

        Player player = (Player) commandSender;
        if (args.length == 0) {
            player.sendMessage("§8----------» §3§lCrate §8«----------");
            player.sendMessage("");
            player.sendMessage("§3/crate list");
            player.sendMessage("§3/crate create");
            player.sendMessage("§3/crate edit");
            player.sendMessage("§3/crate set");
            player.sendMessage("§3/crate reload");
            player.sendMessage("");
            player.sendMessage("§8----------» §3§lCrate §8«----------");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("l")) {

                if (player.hasPermission("crate.admin.list")) {

                    Inventory inventory = Bukkit.createInventory(null, 27, "§k§l§o§3§lCrates");

                    for (Crate crate : Crates.getInstance().getCrateManager().getCrates()) {
                        inventory.addItem(crate.getDisplay());
                    }

                    player.openInventory(inventory);
                }
            } else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {

                if (player.hasPermission("crate.admin.create")) {

                    player.sendMessage("§3§lCrates §7>> §7Benutze §3/crate create §7<Name> <Item>");

                }
            } else if (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("e")) {

                if (player.hasPermission("crate.admin.edit")) {

                    player.sendMessage("§3§lCrates §7>> §7Benutze §3/crate edit §7<Crate>");

                }
            } else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {

                if (player.hasPermission("crate.admin.reload")) {

                    long d = System.currentTimeMillis();
                    player.sendMessage("§3§lCrates §7>> §cCrates werden neugeladen....");
                    Crates.getInstance().getCrateManager().loadCrates();
                    long delay = System.currentTimeMillis() - d;
                    player.sendMessage("§3§lCrates §7>> §aCrates wurden erfolgreich neugeladen! §8[§a" + delay + " ms§8]");


                }
            }
        } else if (args.length == 2) {

            if (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("e")) {

                if (player.hasPermission("crate.admin.edit")) {
                    String name = args[1];
                    Crate crate = Crates.getInstance().getCrateManager().getCrateByName(name);

                    if(crate == null) {
                        player.sendMessage("§3§lCrates §7>> §7Benutze §3/crate list");
                        return true;
                    }

                    Crates.getInstance().getCrateManager().openEditGUI(player, crate);
                }
            }else if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("s")) {

                if (player.hasPermission("crate.admin.set")) {
                    String name = args[1];
                    Crate crate = Crates.getInstance().getCrateManager().getCrateByName(name);

                    if(crate == null) {
                        player.sendMessage("§3§lCrates §7>> §7Benutze §3/crate list");
                        return true;
                    }

                    Crates.getInstance().getCrateCache().put(player.getUniqueId(), crate);
                    player.sendMessage("§aBau einen Block ab wo die Kiste sich befinden soll!");
                }
            }

        } else if (args.length == 3) {

            if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("c")) {

                if (player.hasPermission("crate.admin.create")) {
                    String name = args[1];
                    try {
                        Material item = Material.valueOf(args[2].toUpperCase());

                        Inventory inventory = Bukkit.createInventory(null, 9, "§k§l§o§3§lCrate §8erstellen");
                        inventory.setItem(1, new ItemBuilder(Material.NAME_TAG).setDisplayName("§fCrate Name: " + name.replace("&", "§")).removeAllAtributs().build());
                        inventory.setItem(2, new ItemBuilder(item).setDisplayName("§fCrate Item: §3" + item.toString()).removeAllAtributs().build());


                        inventory.setItem(6, new ItemBuilder(Material.LIME_CONCRETE).setDisplayName("§aCrate erstellen").removeAllAtributs().build());
                        inventory.setItem(7, new ItemBuilder(Material.RED_CONCRETE).setDisplayName("§cAbbrechen").removeAllAtributs().build());

                        player.openInventory(inventory);

                    } catch (IllegalArgumentException e) {
                        player.sendMessage("§3§lCrates §7>> §7Das Material §c" + args[2] + " §7konnte nicht gefunden werden!");
                    }
                }
            }
        } else {
            player.sendMessage("§8----------» §3§lCrate §8«----------");
            player.sendMessage("");
            player.sendMessage("§3/crate list");
            player.sendMessage("§3/crate create");
            player.sendMessage("§3/crate edit");
            player.sendMessage("");
            player.sendMessage("§8----------» §3§lCrate §8«----------");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> arguments = new ArrayList<>();
        if(args.length == 1) {
            arguments.add("create");
            arguments.add("edit");
            arguments.add("list");
            arguments.add("set");
            arguments.add("reload");
        }else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("edit")) {
                for(Crate crate : Crates.getInstance().getCrateManager().getCrates()) {
                    arguments.add(Crates.getInstance().getCrateManager().getRawName(crate.getDisplayName()));
                }
            }
        }

        return arguments;
    }
}
