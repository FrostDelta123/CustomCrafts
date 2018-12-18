package ru.frostdelta.customcrafts.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.frostdelta.customcrafts.CraftingItem;
import ru.frostdelta.customcrafts.Utils;
import ru.frostdelta.customcrafts.gui.ConfirmMenu;

public class ChatEvent implements Listener {

    @EventHandler
    public void chat(AsyncPlayerChatEvent event){
        if(Utils.isCrafting(event.getPlayer())){
            if(isInteger(event.getMessage()) && Integer.parseInt(event.getMessage()) > 0) {
                Player p = event.getPlayer();
                CraftingItem item = Utils.getCraftInProgress().get(p);
                item.setAmount(Integer.parseInt(event.getMessage()));
                p.openInventory(ConfirmMenu.confirmMenu(item.getCraftMaterial(), Integer.parseInt(event.getMessage())));

                event.setCancelled(true);
            }else event.getPlayer().sendMessage(ChatColor.RED + "Введите только число!");
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

}
