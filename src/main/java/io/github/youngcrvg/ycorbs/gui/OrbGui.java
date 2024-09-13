package io.github.youngcrvg.ycorbs.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OrbGui {
    private Inventory inv;

    public void open(Player p) {
        inv = Bukkit.createInventory(null, 6 * 9, "§bUsar Orbs");
        set(p);
        p.openInventory(inv);
    }

    private void set(Player p) {
        ItemStack item = p.getItemInHand();
        int[] slots = {11, 13, 15, 31};
        int qt;
        String name;
        for (int slot : slots) {
            switch (slot) {
                case 13:
                    qt = 32;
                    name = "§bUsar 32";
                    break;
                case 15:
                    qt = 64;
                    name = "§bUsar 64";
                    break;
                case 31:
                    qt = 1;
                    name = "§bUsar Todas";
                    break;
                default:
                    qt = 1;
                    name = "§bUsar 1";
                    break;
            }
            ItemStack itemClone = item.clone();
            itemClone.setAmount(qt);
            ItemMeta meta = itemClone.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(name);
                itemClone.setItemMeta(meta);
            }
            inv.setItem(slot, itemClone);
        }
    }
}
