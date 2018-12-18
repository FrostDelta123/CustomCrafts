package ru.frostdelta.customcrafts.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.frostdelta.customcrafts.Utils;

public class PlayerLeave implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void leave(PlayerQuitEvent event){
        if(Utils.isCrafting(event.getPlayer())){
            Utils.getCraftInProgress().remove(event.getPlayer());
        }
    }

}
