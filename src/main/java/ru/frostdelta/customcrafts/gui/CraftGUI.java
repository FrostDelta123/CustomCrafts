package ru.frostdelta.customcrafts.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.frostdelta.customcrafts.CraftingItem;
import ru.frostdelta.customcrafts.Utils;

import java.util.Map;
import java.util.Set;

public class CraftGUI implements InventoryHolder {

    public static Inventory craftMenu(ConfigurationSection section) {

        int slots = 9;
        while (section.getKeys(false).size() > slots) {
            slots = slots + 9;
            if(section.getKeys(false).size() < slots){
                break;
            }
        }

        Inventory inv = Bukkit.createInventory(new CraftGUI(), slots, "CraftMenu");
        Set<String> sections = section.getKeys(false);
        for (String sec : sections) {
            ConfigurationSection section1 = section.getConfigurationSection(sec);

            ItemStack item = new ItemStack(Material.getMaterial(section1.getString("material")));
            CraftingItem craft = Utils.getAllCrafts().get(item);
            Utils.getAllCrafts().remove(item);
            ItemMeta meta = item.getItemMeta();

            if(craft.getEnchantment() != null) {
                for(Map.Entry enchantment : craft.getEnchantment().entrySet()) {
                    meta.addEnchant((Enchantment) enchantment.getKey(), (Integer) enchantment.getValue(), true);
                }
            }
            meta.setDisplayName(craft.getName());
            meta.setLore(craft.getLore());
            item.setItemMeta(meta);
            Utils.getAllCrafts().put(item, craft);
            inv.setItem(inv.firstEmpty(), item);

        }

        return inv;
    }

    /**
     * Get the object's inventory.
     *
     * @return The inventory.
     */
    @Override
    public Inventory getInventory() {
        return null;
    }
}
