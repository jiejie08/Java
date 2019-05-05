package com.ld.checkstand.plus;

/**
 * Author:li_d
 * Created:2019/4/8
 */
public interface OrderCenter {

    /**
     * 添加订单
     * @param order
     */
    void addOrder(Order order);

    /**
     * 移除订单
     * @param order
     */
    void removeOrder(Order order);

    /**
     * 通过订单编号获取订单
     * @param ordersId
     * @return
     */
    String ordersTable(String ordersId);

    /**
     * 列出订单信息
     * @return
     */
    String ordersTable();

    /**
     * 存储订单信息
     */
    void storeOrders();

    /**
     * 加载订单信息
     */
    void loadOrders();
}
