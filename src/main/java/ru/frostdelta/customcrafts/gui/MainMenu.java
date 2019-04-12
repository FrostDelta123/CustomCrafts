package ru.frostdelta.customcrafts.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.frostdelta.customcrafts.Utils;

import java.util.Set;

public class MainMenu implements InventoryHolder {


    public static Inventory mainMenu(Set<String> sections) {


        int slots = 9;
        while (sections.size() > slots) {
            slots = slots + 9;
            if(sections.size() < slots){
                break;
            }
        }

        Inventory inv = Bukkit.createInventory(new MainMenu(), slots, "MainMenu");
        int x = 0;

        for (String section : sections) {

            FileConfiguration configuration = Utils.getCfg();
            String path = "sections." + section;

            ItemStack item = new ItemStack(Material.getMaterial(configuration.getString(path + ".material")));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(configuration.getString(path + ".name").replaceAll("'", ""));
            item.setItemMeta(meta);
            inv.setItem(x, item);

            x++;
        }

        return inv;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
