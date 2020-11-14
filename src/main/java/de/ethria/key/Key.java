package de.ethria.key;

import de.ethria.Crates;
import de.ethria.crate.Crate;
import de.ethria.utils.ItemBuilder;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Key {

    Crate crate;

    public Key(Crate crate) {
        this.crate = crate;
    }

    public ItemStack getKey() {
        ItemBuilder builder = new ItemBuilder(Material.TRIPWIRE_HOOK);
        builder.setDisplayName("§3§lCrateKey: " + crate.getDisplayName());
        builder.addEnchantment(Enchantment.DURABILITY, 1);
        builder.removeAllAtributs();
        ItemStack itemStack = builder.build();
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("KEY_ID", UUID.randomUUID().toString());
        nbtItem.setString("KEY_CRATE", Crates.getInstance().getCrateManager().getRawName(crate.getDisplayName()));

        return nbtItem.getItem();
    }

}
