package de.ethria.manager;

import de.ethria.Crates;
import de.ethria.crate.Crate;
import de.ethria.crate.CrateItem;
import de.ethria.utils.ItemBuilder;
import de.ethria.utils.MultipageInventory;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CrateManager {

    private ArrayList<Crate> crates;

    private HashMap<Location, Crate> crateLocation;

    public CrateManager() {
        loadCrates();
    }

    public void loadCrates() {
        this.crates = new ArrayList<>();
        this.crateLocation = new HashMap<>();

        if (!Crates.getInstance().getDataFolder().exists()) {
            Crates.getInstance().getDataFolder().mkdirs();
        }
        for (File file : Objects.requireNonNull(Crates.getInstance().getDataFolder().listFiles())) {
            if (file.isFile()) {
                if (file.getName().endsWith(".yml")) {
                    String name = file.getName().replace(".yml", "");
                    if (name.equalsIgnoreCase("config")) {
                        continue;
                    }
                    Crate crate = loadCrateFromConfig(name);
                    FileManager manager = new FileManager("config");
                    YamlConfiguration configuration = manager.getConfiguration();
                    if (configuration.contains("loc." + name)) {
                        World world = Bukkit.getWorld(configuration.getString("loc." + name + ".world"));
                        double x = configuration.getDouble("loc." + name + ".x");
                        double y = configuration.getDouble("loc." + name + ".y");
                        double z = configuration.getDouble("loc." + name + ".z");

                        Location location = new Location(world, x, y, z);
                        crateLocation.put(location, crate);
                    }


                    if (!crates.contains(crate))
                        crates.add(crate);
                }
            }
        }
    }

    public void createCrate(Player player, String name, Material material) {

        FileManager manager = new FileManager(getRawName(name));
        YamlConfiguration configuration = manager.getConfiguration();
        configuration.set("crate.info.name", name.replace("§", "&"));
        configuration.set("crate.info.item", material.toString());
        configuration.set("crate.info.background", Material.GRAY_STAINED_GLASS_PANE.toString());
        configuration.set("crate.info.price", 5000);
        manager.save();
        Crate crate = loadCrateFromConfig(name);
        if (!crates.contains(crate))
            crates.add(crate);
        openEditGUI(player, crate);
    }

    public Crate loadCrateFromConfig(String crate) {
        FileManager manager = new FileManager(getRawName(crate));
        YamlConfiguration configuration = manager.getConfiguration();

        String name = configuration.getString("crate.info.name").replace("&", "§");
        Material material = Material.valueOf(configuration.getString("crate.info.item"));
        Material background = Material.valueOf(configuration.getString("crate.info.background"));
        int price = configuration.getInt("crate.info.price");

        Crate toReturn = new Crate(name, material, background, price);
        if (configuration.contains("crate.items")) {
            for (String id : configuration.getConfigurationSection("crate.items").getKeys(false)) {
                String display = configuration.getString("crate.items." + id + ".display").replace("&", "§");
                Material mat = Material.valueOf(configuration.getString("crate.items." + id + ".material"));
                int amount = configuration.getInt("crate.items." + id + ".amount");
                List<String> lore = configuration.getStringList("crate.items." + id + ".lore");
                List<String> enchants = configuration.getStringList("crate.items." + id + ".enchants");
                int chance = configuration.getInt("crate.items." + id + ".chance");

                ItemBuilder builder = new ItemBuilder(mat, amount);

                if (display.length() > 1) {
                    builder.setDisplayName(display);
                }

                if (lore.size() >= 1) {
                    builder.addLore(lore);
                }

                if (enchants.size() >= 1) {
                    for (String rawString : enchants) {
                        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(rawString.split(":")[0]));
                        int level = Integer.parseInt(rawString.split(":")[1]);
                        builder.addEnchantment(enchantment, level);
                    }
                }

                CrateItem crateItem = new CrateItem(builder.build(), chance);
                toReturn.getCrateItems().add(crateItem);
            }
        }
        return toReturn;
    }

    public void openEditGUI(Player player, Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§k§l§o§3§lBearbeiten: " + crate.getDisplayName());
        inventory.setItem(4, crate.getDisplay());
        inventory.setItem(10, new ItemBuilder(crate.getBackGround()).setDisplayName("§7Hintergrundfarbe").build());
        inventory.setItem(13, new ItemBuilder(Material.GOLD_NUGGET).setDisplayName("§7Preis: §e" + crate.getPrice()).build());
        inventory.setItem(16, new ItemBuilder(Material.BOOK).setDisplayName("§7Items: " + crate.getCrateItems().size()).build());
        inventory.setItem(22, new ItemBuilder(Material.ENDER_EYE).setDisplayName("§cTest Öffnung").build());

        player.openInventory(inventory);
    }

    public void openBackGroundInventory(Player player, Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, 18, "§k§l§o§3§lHintergund: " + crate.getDisplayName());

        inventory.addItem(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§7Weiß").build());
        inventory.addItem(new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName("§7Orange").build());
        inventory.addItem(new ItemBuilder(Material.MAGENTA_STAINED_GLASS_PANE).setDisplayName("§7Magenta").build());
        inventory.addItem(new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayName("§7Hellblau").build());
        inventory.addItem(new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayName("§7Gelb").build());
        inventory.addItem(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayName("§7Hellgrün").build());
        inventory.addItem(new ItemBuilder(Material.PINK_STAINED_GLASS_PANE).setDisplayName("§7Pink").build());
        inventory.addItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§7Grau").build());
        inventory.addItem(new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE).setDisplayName("§7Hellgrau").build());
        inventory.addItem(new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayName("§7Türkis").build());
        inventory.addItem(new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setDisplayName("§7Violett").build());
        inventory.addItem(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName("§7Blau").build());
        inventory.addItem(new ItemBuilder(Material.BROWN_STAINED_GLASS_PANE).setDisplayName("§7Braun").build());
        inventory.addItem(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§7Grün").build());
        inventory.addItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayName("§7Rot").build());
        inventory.addItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§7Schwarz").build());

        player.openInventory(inventory);
    }

    public void changeBackgroundColor(Player player, String name, Material newBackGround) {
        FileManager manager = new FileManager(getRawName(name));
        YamlConfiguration configuration = manager.getConfiguration();
        configuration.set("crate.info.background", newBackGround.toString());
        manager.save();

        crates.removeIf(c -> c.getDisplayName().equalsIgnoreCase(name));
        Crate crate = loadCrateFromConfig(name);
        if (!crates.contains(crate))
            crates.add(crate);
        openEditGUI(player, crate);
    }

    public void openCrateItems(Player player, Crate crate) {
        MultipageInventory inventory = MultipageInventory.getNew(player);
        inventory.setTitle("§k§l§o§3§lIetms: " + crate.getDisplayName());
        inventory.setRows(6);
        inventory.setItem(49, new ItemBuilder(Material.ANVIL).setDisplayName("§aItem hinzufügen").build());
        for (CrateItem item : crate.getCrateItems()) {
            inventory.addItemStack(item.getInfoItem());
        }
        inventory.open();
    }

    public void openCrateItemAdd(Player player, Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§k§l§o§3§lIetms hinzufügen: " + crate.getDisplayName());

        inventory.setItem(2, new ItemBuilder(Material.ITEM_FRAME).setDisplayName("§aItem hinzufügen").build());
        inventory.setItem(6, new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§eGeld hinzufügen").build());

        player.openInventory(inventory);
    }

    public void openCrateItemAddItem(Player player, Crate crate) {
        Inventory inventory = Bukkit.createInventory(null, 9, "§k§l§o§3Item hinzufügen: " + crate.getDisplayName());

        inventory.setItem(1, new ItemBuilder(Material.LIME_CONCRETE).setDisplayName("§aHinzufügen").build());
        inventory.setItem(4, new ItemBuilder(Material.BARRIER).setDisplayName("§cKein Item .... ").build());
        inventory.setItem(7, new ItemBuilder(Material.RED_CONCRETE).setDisplayName("§cAbbrechen").build());

        player.openInventory(inventory);
    }

    public void addCrateItem(Player player, Crate crate, ItemStack itemStack, int chance) {
        CrateItem crateItem = new CrateItem(itemStack, chance);
        FileManager fileManager = new FileManager(getRawName(crate.getDisplayName()));
        YamlConfiguration configuration = fileManager.getConfiguration();
        if (!configuration.contains("crate.items")) {
            configuration.set("crate.items.0.display", crateItem.getItem().getItemMeta().getDisplayName().replace("§", "&"));
            configuration.set("crate.items.0.material", crateItem.getItem().getType().toString());
            configuration.set("crate.items.0.amount", crateItem.getItem().getAmount());
            ArrayList<String> enchtants = new ArrayList<>();
            for (Enchantment enchantment : crateItem.getItem().getEnchantments().keySet()) {
                enchtants.add(enchantment.getKey().getKey() + ":" + crateItem.getItem().getEnchantments().get(enchantment));
            }

            configuration.set("crate.items.0.enchants", enchtants);
            configuration.set("crate.items.0.lore", crateItem.getItem().getItemMeta().getLore());
            configuration.set("crate.items.0.chance", crateItem.getChance());
        } else {
            int id = configuration.getConfigurationSection("crate.items").getKeys(false).size();
            configuration.set("crate.items." + id + ".display", crateItem.getItem().getItemMeta().getDisplayName().replace("§", "&"));
            configuration.set("crate.items." + id + ".material", crateItem.getItem().getType().toString());
            configuration.set("crate.items." + id + ".amount", crateItem.getItem().getAmount());

            ArrayList<String> enchtants = new ArrayList<>();
            for (Enchantment enchantment : crateItem.getItem().getEnchantments().keySet()) {
                enchtants.add(enchantment.getKey().getKey() + ":" + crateItem.getItem().getEnchantments().get(enchantment));
            }

            configuration.set("crate.items." + id + ".enchants", enchtants);
            configuration.set("crate.items." + id + ".lore", crateItem.getItem().getItemMeta().getLore());
            configuration.set("crate.items." + id + ".chance", crateItem.getChance());
        }

        fileManager.save();

        crate.getCrateItems().add(crateItem);
        openCrateItems(player, crate);
    }

    public String getRawName(String name) {
        boolean check = false;
        String rawName = name;
        do {

            if (rawName.charAt(0) == '§') {
                rawName = rawName.substring(2);
            } else {
                check = true;
            }

        } while (!check);
        return rawName;
    }

    public ArrayList<Crate> getCrates() {
        return crates;
    }

    public Crate getCrateByName(String name) {
        for (Crate crate : crates) {
            String crateName = getRawName(crate.getDisplayName());
            String rawName = getRawName(name.replace("&", "§"));
            if (crateName.equalsIgnoreCase(rawName)) {
                return crate;
            }
        }
        return null;
    }

    public HashMap<Location, Crate> getCrateLocation() {
        return crateLocation;
    }

    public void openItemChoose(Player player, Crate crate) {
        MultipageInventory inventory = MultipageInventory.getNew(player);
        inventory.setTitle("§k§l§o§3§lIetms in der " + crate.getDisplayName());
        inventory.setRows(6);
        for (CrateItem item : crate.getCrateItems()) {
            inventory.addItemStack(item.getInfoItem());
        }
        inventory.open();
    }
}
