package ru.frostdelta.customcrafts;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.frostdelta.customcrafts.commands.CraftGUICommand;
import ru.frostdelta.customcrafts.commands.CraftSectionCommand;
import ru.frostdelta.customcrafts.commands.GetAllCommand;
import ru.frostdelta.customcrafts.events.ChatEvent;
import ru.frostdelta.customcrafts.events.CloseEvent;
import ru.frostdelta.customcrafts.events.InventoryClick;
import ru.frostdelta.customcrafts.events.PlayerLeave;

import java.sql.SQLException;

public final class CustomCrafts extends JavaPlugin {

    private static CustomCrafts inst;

    public CustomCrafts(){
        final CustomCrafts plugin = inst = this;
    }


    public static CustomCrafts inst() {
        return inst;
    }


    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        getLogger().info("Developed by " + getDescription().getAuthors());
        getCommand("craftgui").setExecutor(new CraftGUICommand());
        getCommand("craftall").setExecutor(new GetAllCommand());
        getCommand("craftsection").setExecutor(new CraftSectionCommand());
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new CloseEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);

        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                try {
                    Network db = new Network();
                    db.url = getConfig().getString("url").replaceAll("'", "");
                    db.username = getConfig().getString("username").replaceAll("'", "");
                    db.password = getConfig().getString("password").replaceAll("'", "");
                    db.openConnection();
                    db.createDB();
                } catch (SQLException e) {
                    getLogger().severe("ERROR! Cant load SQL, check config!");
                    getLogger().severe("PLUGIN DISABLED");
                    getLogger().severe("Set debug to true in config.yml");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onDisable() {
        getLogger().info("Developed by " + getDescription().getAuthors());
        getLogger().info("Plugin disabled!");
    }
}
