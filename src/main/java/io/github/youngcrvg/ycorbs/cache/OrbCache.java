package io.github.youngcrvg.ycorbs.cache;

import java.util.List;

public class OrbCache {
    private double tp;
    private double money;
    private int id;
    private short data;
    private String name;
    private List<String> lore;
    public OrbCache(double tp, double money, int id, short data,String name, List<String> lore) {
        this.tp = tp;
        this.money = money;
        this.id = id;
        this.data = data;
        this.name = name;
        this.lore = lore;
    }

    public void setData(short data) {
        this.data = data;
    }

    public short getData() {
        return data;
    }

    public double getTp() {
        return tp;
    }
    public double getMoney() {
        return money;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<String> getLore() {
        return lore;
    }
    public void setTp(double tp) {
        this.tp = tp;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}
