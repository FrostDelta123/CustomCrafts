package ru.frostdelta.customcrafts;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    private static FileConfiguration cfg = CustomCrafts.inst().getConfig();
    private static Map<ItemStack, CraftingItem> allCrafts = new HashMap<ItemStack, CraftingItem>();
    private static Map<Player, CraftingItem> craftInProgress = new HashMap<Player, CraftingItem>();
    private static Map<Integer, ConfigurationSection> sectionByid = new HashMap<Integer, ConfigurationSection>();

    public static Map<Integer, ConfigurationSection> getSectionByid() {
        return sectionByid;
    }

    public static String timeCast(long sec){
        DateFormat format = new SimpleDateFormat("HHч mmмин ssсек") ;
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(new Date(sec * 1000));
    }

    public static ItemStack fromSQL(ResultSet rs) throws WarpException {
        ItemStack item;
        try {
            item = new ItemStack(Material.getMaterial(rs.getString("material")));
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(rs.getString("name"));
            Map<Enchantment, Integer> enchantmentMap = new HashMap<Enchantment, Integer>(enchantsFrom(rs.getString("enchant")));

            for(Map.Entry<Enchantment, Integer> entry : enchantmentMap.entrySet()){
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            }

            if(rs.getLong("time") > System.currentTimeMillis()/1000){
            List<String> ready = new ArrayList<String>();
            ready.add(Messages.LORE_NOT_READY);
            ready.add("Осталось: " + Utils.timeCast(rs.getLong("time") - System.currentTimeMillis()/1000));
            meta.setLore(ready);
            }
            item.setItemMeta(meta);
            item.setAmount(rs.getInt("amount"));

            return item;
        }catch (SQLException e){
            e.printStackTrace();
        }

        throw new WarpException("ResultSet invalid!");
    }

    public static Map<Enchantment, Integer> enchantsFrom(String entry){
        List<String> first = new ArrayList<String>();
        first.addAll(Arrays.asList(entry.split(",")));
        Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
        for(String enchant : first){
          String[] ench = enchant.split(":");
          map.put(Enchantment.getByName(ench[0]), Integer.parseInt(ench[1]));
        }
        return map;
    }

    public static ItemStack cast(CraftingItem craft){
        ItemStack item = new ItemStack(craft.getMaterial());
        ItemMeta meta = item.getItemMeta();
        if(!craft.getEnchantment().isEmpty()) {
            for(Map.Entry enchantment : craft.getEnchantment().entrySet()) {
                meta.addEnchant((Enchantment) enchantment.getKey(), (Integer) enchantment.getValue(), true);
            }
        }
        meta.setDisplayName(craft.getName());
        item.setItemMeta(meta);
        item.setAmount(craft.getAmount());

        return item;
    }

    public static List<ItemStack> warp(List<? extends String> list){
        if(list.isEmpty()){
            try {
                throw new WarpException("List is empty");
            } catch (WarpException e) {
                e.printStackTrace();
            }
        }

        List<ItemStack> items = new ArrayList<ItemStack>();
        for(String str : list){
            String[] line = str.split(":", 2);
            ItemStack item = new ItemStack(Material.getMaterial(line[0]));
            item.setAmount(Integer.parseInt(line[1]));
            items.add(item);
        }
        return items;
    }

    public static Map<Player, CraftingItem> getCraftInProgress() {
        return craftInProgress;
    }

    public static boolean isCrafting(Player player){
        return craftInProgress.containsKey(player);
    }

    public static Map<ItemStack, CraftingItem> getAllCrafts() {
        return allCrafts;
    }

    public static Set<String> getAllSections(){
        return getCfg().getConfigurationSection("sections").getKeys(false);
    }

    public static Set<String> getSectionItems(String section){
        return getCfg().getConfigurationSection("sections." + section).getKeys(false);
    }

    public static FileConfiguration getCfg(){
        return cfg;
    }
}
