package ru.frostdelta.customcrafts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class CraftProcess extends BukkitRunnable {

    private Player player;
    private CraftingItem craft;
    private Integer time;

    public CraftProcess(Player whoClicked, CraftingItem item) {
        this.player = whoClicked;
        this.craft = item;
        time = item.getTime();
    }

    @Override
    public void run() {
        if(time <= 0){
                result(player, craft);
            this.cancel();
        }else time--;
    }

    private static void giveItem(CraftingItem craft, Player player){
        ItemStack item = Utils.cast(craft);
        Inventory inv = player.getInventory();
        inv.setItem(inv.firstEmpty(), item);

    }

    public static void result(Player player, CraftingItem craft){
        double d = new Random().nextDouble();
        if(d <= (float) craft.getChance()/100){
            if(CustomCrafts.inst().getServer().getOnlinePlayers().contains(player)) {
                player.sendMessage(Messages.CRAFT_SUCCESSFUL);
                giveItem(craft, player);
            }
            new Network().purge(player.getName(), craft.getMaterial().name().toUpperCase(), craft.getAmount(), craft.getName());
        }else {
            if(CustomCrafts.inst().getServer().getOnlinePlayers().contains(player)) {
                player.sendMessage(Messages.CRAFT_FAILED);
            }
            new Network().purge(player.getName(), craft.getMaterial().name().toUpperCase(), craft.getAmount(), craft.getName());
        }
    }
}
