package io.github.youngcrvg.ycorbs.cmd;

import io.github.youngcrvg.ycorbs.Main;
import io.github.youngcrvg.ycorbs.cache.OrbCache;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreateOrbCommand implements CommandExecutor {
    private final Main instance = Main.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        if(!p.hasPermission("ycorb.admin")) {
            return true;
        }
        if(args.length != 2) {
            return true;
        }
        ItemStack item = p.getItemInHand();
        if(item == null || item.getType() == Material.AIR) {
            p.sendMessage(ChatColor.RED+"Você precisa ter um item em sua mão.");
            return true;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            p.sendMessage(ChatColor.RED+"O item precisa ter um displayname e lore");
            return true;
        }
        if(meta.getLore() == null || meta.getDisplayName() == null) {
            p.sendMessage(ChatColor.RED+"O item precisa ter um displayname e lore");
            return true;
        }
        if(instance.getCache().containsKey(meta.getDisplayName())) {
            p.sendMessage(ChatColor.RED+"Essa orb já existe.");
            return true;
        }
        double tp = Double.parseDouble(args[0]);
        double money = Double.parseDouble(args[1]);
        instance.getCache().put(meta.getDisplayName(), new OrbCache(tp, money, item.getTypeId(), item.getData().getData(),meta.getDisplayName(), meta.getLore()));
        p.sendMessage(ChatColor.GREEN+"Você criou a orb com sucesso!");
        return true;
    }
}
