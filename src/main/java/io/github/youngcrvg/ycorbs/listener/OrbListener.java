package io.github.youngcrvg.ycorbs.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.youngcrvg.ycorbs.Main;
import io.github.youngcrvg.ycorbs.cache.OrbCache;

import java.util.HashMap;
import java.util.List;

public class OrbListener implements Listener {

    @EventHandler
    public void onInteractOrb(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        String itemName = itemMeta.getDisplayName();
        List<String> itemLore = itemMeta.getLore();
        HashMap<String, OrbCache> cache = Main.getInstance().getCache();
        for (OrbCache orbCache : cache.values()) {
            if (orbCache.getName().equals(itemName) && orbCache.getLore().equals(itemLore)) {
                Main.getInstance().getOrbGui().open(p);
                return;
            }
        }
        p.sendMessage("Nenhuma orb correspondente encontrada.");
    }
}
