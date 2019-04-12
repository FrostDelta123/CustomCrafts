package ru.frostdelta.customcrafts.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import ru.frostdelta.customcrafts.Utils;
import ru.frostdelta.customcrafts.gui.ConfirmMenu;

public class CloseEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void close(InventoryCloseEvent event){
        if(event.getInventory().getHolder() instanceof ConfirmMenu){
            Utils.getCraftInProgress().remove(((Player) event.getPlayer()));
        }
    }

}
