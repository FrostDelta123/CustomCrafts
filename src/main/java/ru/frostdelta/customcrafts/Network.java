package ru.frostdelta.customcrafts;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.nio.Buffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Network {

    public String url, username, password;
    private static HashMap<String, PreparedStatement> preparedStatements = new HashMap<>();
    private Connection connection;

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url +
                    "?useUnicode=true&characterEncoding=UTF-8", this.username, this.password);

            preparedStatements.put("addCraft", connection.prepareStatement(
                    "INSERT INTO `crafts` (player, material, enchant, name, amount, time, custom) VALUES (?,?,?,?,?,?,?)"));
            preparedStatements.put("getItems", connection.prepareStatement(
                    "SELECT * FROM `crafts` WHERE player=?"));
            preparedStatements.put("purge", connection.prepareStatement(
                    "DELETE FROM `crafts` WHERE player=? AND material=? AND amount=? AND name=? AND time <= ?"));
        }

    }

    public void purge(String player, String material, int amount, String name) {
        try {
            PreparedStatement purge = preparedStatements.get("purge");
            purge.setString(1, player);
            purge.setString(2, material);
            purge.setInt(3, amount);
            purge.setString(4, name);
            purge.setLong(5, System.currentTimeMillis()/1000);
            purge.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ItemStack> getItems(String player) throws WarpException {
        try {
            PreparedStatement getItems = preparedStatements.get("getItems");
            getItems.setString(1, player);
            try (ResultSet rs = getItems.executeQuery()) {

                List<ItemStack> items = new ArrayList<ItemStack>();
                while (rs.next()) {
                    items.add(Utils.fromSQL(rs));
                }
                return items;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void addCraft(Player player, CraftingItem craft) {
        try {
            PreparedStatement addCraft = preparedStatements.get("addCraft");

            addCraft.setString(1, player.getName());
            addCraft.setString(2, craft.getMaterial().name());
            addCraft.setString(3, craft.enchantsToString().substring(0, craft.enchantsToString().length() - 1));
            addCraft.setString(4, craft.getName());
            addCraft.setString(5, String.valueOf(craft.getAmount()));
            addCraft.setLong(6, System.currentTimeMillis()/1000 + craft.getTime());
            addCraft.setString(7, craft.getCustomName());
            addCraft.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createDB() {
        try {

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `crafts` (player varchar(200), material varchar(200), enchant varchar(200), name varchar(200), amount int(10), time bigint(200), custom varchar(200)) CHARACTER SET utf8 COLLATE utf8_general_ci";
            statement.executeUpdate(sql);

            CustomCrafts.inst().getLogger().info("Database created!");
        } catch (SQLException sqlException) {
            if (sqlException.getErrorCode() == 1007) {
                System.out.println(sqlException.getMessage());
            } else {
                sqlException.printStackTrace();
            }
        }
    }

}