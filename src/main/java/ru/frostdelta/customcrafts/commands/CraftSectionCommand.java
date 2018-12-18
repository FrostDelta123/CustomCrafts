package ru.frostdelta.customcrafts.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.frostdelta.customcrafts.CraftingItem;
import ru.frostdelta.customcrafts.Utils;
import ru.frostdelta.customcrafts.gui.CraftGUI;

public class CraftSectionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("customcrafts.craftsection")){
            if(args.length == 1){
                ConfigurationSection section = Utils.getCfg().getConfigurationSection("sections." + args[0] + ".items");
                Player player = ((Player) sender);
                for(String string : section.getKeys(false)) {
                    ConfigurationSection section1 = section.getConfigurationSection(string);
                    Utils.getAllCrafts().put(new ItemStack(Material.getMaterial(section1.getString("material"))), new CraftingItem(section1));
                }

                player.getOpenInventory().close();
                player.openInventory(CraftGUI.craftMenu(Utils.getCfg().getConfigurationSection("sections." + args[0] + ".items")));
            }
        }
        return true;
    }
}
