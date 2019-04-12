package ru.frostdelta.customcrafts.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import ru.frostdelta.customcrafts.*;
import ru.frostdelta.customcrafts.gui.*;

import java.util.List;

public class InventoryClick implements Listener {

    private Network network = new Network();

    @EventHandler(priority = EventPriority.NORMAL)
    public void click(InventoryClickEvent event){

        if(event.getSlotType() != InventoryType.SlotType.OUTSIDE && event.getSlotType() == InventoryType.SlotType.CONTAINER) {
            if(event.getInventory().getHolder() instanceof ReadyItemsGUI && !event.getCurrentItem().getType().equals(Material.AIR)){

                Player player = ((Player) event.getWhoClicked());
                ItemStack item = event.getCurrentItem();
                if(item.getItemMeta().hasLore()){
                    player.sendMessage(ChatColor.RED + "Предмет не готов!");
                    event.setCancelled(true);
                    return;
                }
                Inventory inv = event.getWhoClicked().getInventory();
                new Network().purge(player.getName(), item.getType().name().toUpperCase(), item.getAmount(), item.getItemMeta().getDisplayName());
                event.getInventory().removeItem(item);
                inv.setItem(inv.firstEmpty(), item);

                event.setCancelled(true);
            }
            if(event.getInventory().getHolder() instanceof CraftGUI && !event.getCurrentItem().getType().equals(Material.AIR)){
                CraftingItem item = Utils.getAllCrafts().get(event.getCurrentItem());

                Utils.getCraftInProgress().put((Player)event.getWhoClicked(), item);
                event.getWhoClicked().sendMessage(Messages.TYPE_ITEMS_AMOUNT);
                event.getWhoClicked().getOpenInventory().close();

                event.setCancelled(true);
            }

            if(event.getInventory().getHolder() instanceof ConfirmMenu && !event.getCurrentItem().getType().equals(Material.AIR)){
                CraftingItem item = Utils.getCraftInProgress().get(((Player) event.getWhoClicked()));

                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().equals("Принять") && event.getSlot() == event.getInventory().getSize()-1){
                    Inventory inventory = event.getWhoClicked().getInventory();

                    List<ItemStack> materials = Utils.warp(item.getCraftMaterial());
                    for(ItemStack itemMaterial : materials){
                        itemMaterial.setAmount(itemMaterial.getAmount() * item.getAmount());

                        if(!inventory.containsAtLeast(itemMaterial, itemMaterial.getAmount())){

                            event.getWhoClicked().sendMessage(ChatColor.RED + Messages.NO_RESOURCES);
                            event.getWhoClicked().getOpenInventory().close();
                            event.setCancelled(true);
                            return;
                        }
                        inventory.removeItem(itemMaterial);
                    }

                    event.getWhoClicked().getOpenInventory().close();
                    Utils.getCraftInProgress().remove(((Player) event.getWhoClicked()));
                    network.addCraft(((Player) event.getWhoClicked()), item);

                    event.getWhoClicked().sendMessage(ChatColor.GREEN + Messages.CRAFT_STARTED);
                    event.getWhoClicked().sendMessage(ChatColor.GREEN + Messages.TIME + Utils.timeCast(item.getTime() * item.getAmount()));
                    BukkitTask task = new CraftProcess((Player) event.getWhoClicked(), item).runTaskTimerAsynchronously(CustomCrafts.inst(),0, 20);
                }

                event.setCancelled(true);
            }

            if(event.getInventory().getHolder() instanceof MainMenu && !event.getCurrentItem().getType().equals(Material.AIR)){
                ConfigurationSection section = Utils.getCfg().getConfigurationSection("sections." + Utils.getSectionByid().get(event.getSlot()).getName() + ".items");
                Player player = ((Player) event.getWhoClicked());
                for(String string : section.getKeys(false)) {
                    ConfigurationSection section1 = section.getConfigurationSection(string);
                    Utils.getAllCrafts().put(new ItemStack(Material.getMaterial(section1.getString("material"))), new CraftingItem(section1));
                }

                player.getOpenInventory().close();
                player.openInventory(CraftGUI.craftMenu(Utils.getCfg().getConfigurationSection("sections." + Utils.getSectionByid().get(event.getSlot()).getName() + ".items")));
                event.setCancelled(true);
            }
        }
    }

}
