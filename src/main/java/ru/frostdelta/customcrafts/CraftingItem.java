package ru.frostdelta.customcrafts;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftingItem {

    private String name;
    private Map<Enchantment, Integer> enchantment = new HashMap<Enchantment, Integer>();
    private Material material;
    private List<String> craftMaterial;
    private List<String> materialNames = new ArrayList<String>();
    private List<String> sqlEnchants = new ArrayList<String>();
    private Integer level;
    private Integer time;
    private Integer chance;
    private Integer amount;
    private String customName;
    private List<String> lore;

    public CraftingItem(ConfigurationSection section){
        customName = section.getName();
        List<String> enchant = new ArrayList<String>();
        enchant.addAll(section.getStringList("enchantment"));
        sqlEnchants = enchant;
        for(String line : enchant){
            String[] ench = line.split(":");
            enchantment.put( Enchantment.getByName(ench[0]), Integer.parseInt(ench[1]));
        }
        material = Material.getMaterial(section.getString("material"));
        name = section.getString("name");
        craftMaterial = section.getStringList("crafting");
        time = section.getInt("time");
        chance = section.getInt("chance");
        lore = section.getStringList("lore");
        for(String name : craftMaterial){
            //ШТА ПРОИСХОДИТ??
            materialNames.add(Material.getMaterial(name.split(":")[0]).name().replace("_", " ").toLowerCase());
        }
    }

    public List<String> getLore() {
        return lore;
    }

    //КОСТЫЛЬ
    public String enchantsToString(){
        StringBuilder sb = new StringBuilder();
        for(String str : sqlEnchants){
            sb.append(str + ",");
        }
        return sb.toString();
    }

    public List<String> getSqlEnchants() {
        return sqlEnchants;
    }

    public List<String> names(){
        return materialNames;
    }


    public void from(Map<Enchantment, Integer> enchants, String name, Material material){
        //String[] enchant = section.getString("enchantment").split(":");
        //material = Material.getMaterial(section.getName());
        //name = section.getString("name");
    }

    public String getCustomName() {
        return customName;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getChance() {
        return chance;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getLevel() {
        return level;
    }

    public Map<Enchantment, Integer> getEnchantment() {
        return enchantment;
    }

    public List<String> getCraftMaterial() {
        return craftMaterial;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
