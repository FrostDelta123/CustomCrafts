package ru.frostdelta.customcrafts.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.frostdelta.customcrafts.Utils;

import java.util.List;

public class ConfirmMenu {

    public static Inventory confirmMenu(List<String> items, Integer amount) {

        int slots = 9;
        while (items.size() > slots) {
            slots = slots + 9;
            if(items.size() < slots){
                break;
            }
        }

        Inventory inv = Bukkit.createInventory(new ConfirmMenuHolder(), slots, "Требуемые ресурсы");

        for (ItemStack stack : Utils.warp(items)){
            stack.setAmount(stack.getAmount() * amount);
            inv.setItem(inv.firstEmpty(), stack);
        }

        ItemStack confirmButton = new ItemStack(Material.GREEN_RECORD);
        ItemMeta confirm = confirmButton.getItemMeta();
        confirm.setDisplayName("Принять");
        confirmButton.setItemMeta(confirm);
        inv.setItem(inv.getSize()-1, confirmButton);

        return inv;
    }

}
