package ru.frostdelta.customcrafts.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.frostdelta.customcrafts.Messages;
import ru.frostdelta.customcrafts.Network;
import ru.frostdelta.customcrafts.WarpException;
import ru.frostdelta.customcrafts.gui.ReadyItemsGUI;

public class GetAllCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("customcrafts.getall")){
            if(sender instanceof Player) {
                try {
                    if(!(new Network().getItems(sender.getName()).isEmpty())){
                        ((Player) sender).openInventory(ReadyItemsGUI.readyMenu(new Network().getItems(sender.getName())));
                    }else sender.sendMessage(Messages.NO_READY_ITEMS);
                } catch (WarpException e) {
                    e.printStackTrace();
                }

                return true;
            }
        }
        return true;
    }

}
