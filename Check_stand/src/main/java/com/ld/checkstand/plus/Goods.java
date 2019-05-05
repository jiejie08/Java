package com.ld.checkstand.plus;

/**
 * 商品
 * Author:li_d
 * Created:2019/4/8
 */
public class Goods {
    // 商品编号
    private String id;

    // 商品名称
    private String name;

    // 商品价格
    private double price;

    public Goods(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        // %.2f保留两位小数
        return String.format("[%2s] %s %.2f", this.getId(), this.getName(), this.getPrice());
    }
}
