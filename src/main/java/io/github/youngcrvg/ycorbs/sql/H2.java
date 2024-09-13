package io.github.youngcrvg.ycorbs.sql;

import io.github.youngcrvg.ycorbs.Main;
import io.github.youngcrvg.ycorbs.cache.OrbCache;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class H2 {
    private Connection connection;
    public H2() throws SQLException, ClassNotFoundException {
        loadSQLite();
    }
    private void loadSQLite() throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlite:" + Main.getInstance().getDataFolder().getAbsolutePath() + "/database.db";
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection(url);
        initializeTables();
    }
    private void initializeTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createPlayersTable = "CREATE TABLE IF NOT EXISTS ycorbs (" +
                    "id INT PRIMARY KEY, " +
                    "data INT, " +
                    "name TEXT, " +
                    "lore TEXT, " +
                    "tp INT, " +
                    "money INT" +
                    ");";
            statement.executeUpdate(createPlayersTable);
        }
    }
    public void createOrb(int id, int data, String name, String lore, int tp, int money) {
        String sql = "INSERT INTO ycorbs (id, data, name, lore, tp, money) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, data);
            ps.setString(3, name);
            ps.setString(4, lore);
            ps.setInt(5, tp);
            ps.setInt(6, money);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(Player p) {
        String sql = "SELECT 1 FROM ycorbs WHERE name = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public double get(String name, String column) {
        if (!column.equals("tp") && !column.equals("money")) {
            throw new IllegalArgumentException("Coluna inválida. Use 'tp' ou 'money'.");
        }
        String sql = "SELECT " + column + " FROM ycorbs WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(column);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public void saveOrbsFromCache() {
        String sql = "INSERT OR REPLACE INTO ycorbs (id, data, name, lore, tp, money) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Map.Entry<String, OrbCache> entry : Main.getInstance().getCache().entrySet()) {
                OrbCache orb = entry.getValue();

                ps.setInt(1, orb.getId());
                ps.setInt(2, orb.getData());
                ps.setString(3, orb.getName());
                ps.setString(4, String.join(",", orb.getLore()));
                ps.setDouble(5, orb.getTp());
                ps.setDouble(6, orb.getMoney());

                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void loadOrbsToCache() {
        String sql = "SELECT * FROM ycorbs";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                short data = rs.getShort("data");
                String name = rs.getString("name");
                String loreString = rs.getString("lore");
                double tp = rs.getDouble("tp");
                double money = rs.getDouble("money");
                List<String> lore = Arrays.asList(loreString.split(","));
                OrbCache orbCache = new OrbCache(tp, money, id, data, name, lore);
                Main.getInstance().getCache().put(name, orbCache);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }
}
