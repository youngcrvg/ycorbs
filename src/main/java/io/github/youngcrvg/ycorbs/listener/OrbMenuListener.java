package io.github.youngcrvg.ycorbs.listener;

import io.github.youngcrvg.ycorbs.Main;
import io.github.youngcrvg.ycorbs.cache.OrbCache;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.xml.ws.WebServiceClient;
import java.util.List;

import static io.github.youngcrvg.ycorbs.hook.BancoHook.depositTp;

public class OrbMenuListener implements Listener {
    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("§bUsar Orbs")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            ItemStack item = e.getCurrentItem();
            if (item == null || item.getType() == Material.AIR) {
                return;
            }
            OrbCache orbCache = Main.getInstance().getCache().get(p.getItemInHand().getItemMeta().getDisplayName());
            if (orbCache == null) {
                p.sendMessage("Nenhuma orb encontrada no cache para este jogador.");
                return;
            }
            double tp = orbCache.getTp();
            double money = orbCache.getMoney();
            int slot = e.getSlot();
            int qt = 0;
            switch (slot) {
                case 11:
                    qt = 1;
                    break;
                case 13:
                    qt = 32;
                    break;
                case 15:
                    qt = 64;
                    break;
                case 31:
                    qt = countOrbsInInventory(p, p.getItemInHand());
                    break;
            }
            tp = tp * qt;
            if(countOrbsInInventory(p, p.getItemInHand()) < qt){
                p.sendMessage(ChatColor.RED+"Você não tem orb suficientes.");
                return;
            }
            removeItemStack(p, p.getItemInHand(), qt);
            depositTp(p, tp);
            String f = Main.api.getFormatBalance().formatNumber(tp);
            p.sendMessage("§aVocê ganhou §f" + f  +"§a TP's");
            p.closeInventory();
        }

    }
    public int countOrbsInInventory(Player p, ItemStack orbItem) {
        Inventory inventory = p.getInventory();
        int totalOrbs = 0;
        ItemMeta orbMeta = orbItem.getItemMeta();
        String orbName = orbMeta != null ? orbMeta.getDisplayName() : null;
        List<String> orbLore = orbMeta != null ? orbMeta.getLore() : null;
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.isSimilar(orbItem)) {
                totalOrbs += item.getAmount();
            }
        }
        return totalOrbs;
    }
    public void removeItemStack(Player player, ItemStack itemStack, int amount) {
        Inventory inventory = player.getInventory();
        int remainingAmount = amount;
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.isSimilar(itemStack)) {
                if (item.getAmount() <= remainingAmount) {
                    remainingAmount -= item.getAmount();
                    inventory.remove(item);
                } else {
                    item.setAmount(item.getAmount() - remainingAmount);
                    remainingAmount = 0;
                }

                if (remainingAmount <= 0) {
                    break;
                }
            }
        }
    }
}
