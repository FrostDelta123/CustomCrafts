package ru.frostdelta.customcrafts.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import ru.frostdelta.customcrafts.Utils;
import ru.frostdelta.customcrafts.gui.MainMenu;

public class CraftGUICommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("customcrafts.craftgui")){
            if(sender instanceof Player) {
                Player player = ((Player) sender);

                for(String section : Utils.getAllSections()){
                    ConfigurationSection section1 = Utils.getCfg().getConfigurationSection("sections." + section);
                    Utils.getSectionByid().put(Utils.getSectionByid().size(), section1);
                }
                player.openInventory(MainMenu.mainMenu(Utils.getAllSections()));

                return true;
            }
        }
        return true;
    }
}
