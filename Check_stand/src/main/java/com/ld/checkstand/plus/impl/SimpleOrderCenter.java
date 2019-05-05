package com.ld.checkstand.plus.impl;

import com.ld.checkstand.plus.Goods;
import com.ld.checkstand.plus.GoodsCenter;
import com.ld.checkstand.plus.Order;
import com.ld.checkstand.plus.OrderCenter;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:li_d
 * Created:2019/4/8
 */
public class SimpleOrderCenter implements OrderCenter {
    private Map<String,Order> orderMap = new HashMap<>();

    //当前存储在文件中
    private String filePath = System.getProperty("user.dir") + File.separator + "orders.txt";

    private GoodsCenter goodsCenter;

    public SimpleOrderCenter(GoodsCenter goodsCenter) {
        this.goodsCenter = goodsCenter;
    }

    @Override
    public void addOrder(Order order) {
        this.orderMap.put(order.getOrderId(), order);
    }

    @Override
    public void removeOrder(Order order) {
        this.orderMap.remove(order.getOrderId(), order);
    }

    @Override
    public String ordersTable(String ordersId) {
        StringBuilder sb = new StringBuilder();
        Order order = this.orderMap.get(ordersId);
        sb.append("===============================");
        sb.append("\n");
        sb.append("编号: " + order.getOrderId());
        sb.append("\n");
        sb.append("打印时间: " + LocalDate.now().toString());
        sb.append("\n");
        sb.append("===============================");
        sb.append("\n");
        sb.append("编号     名称      数量     单价");
        sb.append("\n");
        double total = 0.0D;
        for (Map.Entry<String, Integer> entry : order.getOrderInfo().entrySet()) {

            Goods goods = this.goodsCenter.getGoods(entry.getKey());
            sb.append(String.format("%2s\t%s\t%d\t%.2f", entry.getKey(), goods.getName(), entry.getValue(), goods.getPrice()));

            total += goods.getPrice() * entry.getValue();

            sb.append("\n");
        }
        sb.append("===============================");
        sb.append("\n");
        sb.append(String.format("总价: %.2f", total));
        sb.append("\n");
        sb.append("===============================");
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String ordersTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("===============================");
        sb.append("\n");
        sb.append("订单编号  总价");
        sb.append("\n");
        for (Order order : this.orderMap.values()) {
            Map<String, Integer> goodsMap = order.getOrderInfo();
            double totalPrice = 0.0;
            for (Map.Entry<String, Integer> entry : goodsMap.entrySet()) {

                String goodsId = entry.getKey();
                Integer goodsCount = entry.getValue();
                Goods goods = goodsCenter.getGoods(goodsId);
                totalPrice += goods.getPrice() * goodsCount;
            }
            sb.append(String.format("%2s\t\t%.2f", order.getOrderId(), totalPrice));
            sb.append("\n");
        }

        sb.append("===============================");
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public void storeOrders() {
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for (Map.Entry<String, Order> entry : this.orderMap.entrySet()) {
                Order order = entry.getValue();
                 writer.write(String.format("%s:%s\n", order.getOrderId(),order.getOrderInfo()));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //1=id:totalPrice:goodsId-number:goodsId-number
        System.out.println("保存所有订单到文件每个订单记录：编号和总价");
    }

    @Override
    public void loadOrders() {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            //加载，读取文件
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(":");
                    if (values.length == 1) {
                        Order order = new Order(
                                values[0]
//                                values[1],
//                                Double.parseDouble(values[2])
                        );
                        this.addOrder(order);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //TODO  id: totalPrice ( goodsId -> number)
        System.out.println("加载文件中的数据，实例化Order");
    }
}
