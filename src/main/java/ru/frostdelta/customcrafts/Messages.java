package ru.frostdelta.customcrafts;

public class Messages {

    public static String NO_READY_ITEMS = Utils.getCfg().getString("messages.no-ready-items").replaceAll("'","").replace("&", "§");
    public static String TYPE_ITEMS_AMOUNT = Utils.getCfg().getString("messages.type-items-amount").replaceAll("'", "").replace("&", "§");
    public static String NO_RESOURCES = Utils.getCfg().getString("messages.no-resources").replaceAll("'", "").replace("&", "§");
    public static String CRAFT_STARTED = Utils.getCfg().getString("messages.craft-started").replaceAll("'", "").replace("&", "§");
    public static String TIME = Utils.getCfg().getString("messages.time").replaceAll("'", "").replace("&", "§");
    public static String CRAFT_SUCCESSFUL = Utils.getCfg().getString("messages.craft-successful").replaceAll("'", "").replace("&", "§");
    public static String CRAFT_FAILED = Utils.getCfg().getString("messages.craft-failed").replaceAll("'", "").replace("&", "§");
    public static String LORE_NOT_READY = Utils.getCfg().getString("messages.lore").replaceAll("'", "").replace("&", "§");

}
