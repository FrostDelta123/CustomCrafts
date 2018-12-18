package ru.frostdelta.customcrafts.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ReadyItemsGUI {

    public static Inventory readyMenu(List<ItemStack> items) {

        int slots = 9;
        while (items.size() > slots) {
            slots = slots + 9;
            if(items.size() < slots){
                break;
            }
        }

        Inventory inv = Bukkit.createInventory(new ReadyItemsHolder(), slots, "Готовые предметы");

        for (ItemStack stack : items){
            inv.setItem(inv.firstEmpty(), stack);
        }

        return inv;
    }

}
