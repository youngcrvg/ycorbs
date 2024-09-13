package io.github.youngcrvg.ycorbs;

import io.github.youngcrvg.api.YoungAPI;
import io.github.youngcrvg.ycorbs.cache.OrbCache;
import io.github.youngcrvg.ycorbs.cmd.CreateOrbCommand;
import io.github.youngcrvg.ycorbs.gui.OrbGui;
import io.github.youngcrvg.ycorbs.listener.OrbListener;
import io.github.youngcrvg.ycorbs.listener.OrbMenuListener;
import io.github.youngcrvg.ycorbs.sql.H2;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;

public class Main extends JavaPlugin {
    private static Main instance;
    private  final HashMap<String, OrbCache> cache = new HashMap<>();
    private OrbGui orbGui;
    public static  YoungAPI api;
    public H2 h2;
    @Override
    public void onEnable() {
        instance = this;
        api = YoungAPI.getInstance();
        saveDefaultConfig();
        try {
            h2 = new H2();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        getCommand("criarorb").setExecutor(new CreateOrbCommand());
        getServer().getPluginManager().registerEvents(new OrbListener(), this);
        getServer().getPluginManager().registerEvents(new OrbMenuListener(), this);
        orbGui = new OrbGui();
        h2.loadOrbsToCache();
    }
    @Override
    public void onDisable() {
        h2.saveOrbsFromCache();
    }
    public static Main getInstance() {
        return instance;
    }

    public HashMap<String, OrbCache> getCache() {
        return cache;
    }

    public OrbGui getOrbGui() {
        return orbGui;
    }
}
