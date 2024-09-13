package io.github.youngcrvg.ycorbs.hook;

import io.github.youngcrvg.ycorbs.Main;
import org.bukkit.entity.Player;

public class BancoHook {
    private static final io.github.youngcrvg.ycbanco.Main banco = io.github.youngcrvg.ycbanco.Main.getInstance();
    public static void depositTp(Player p, double tp) {
        double a = banco.getCache().get(p.getName()).getTp();
        banco.getCache().get(p.getName()).setTp(a + tp);
    }
}
