package com.ld.checkstand.plus;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单
 * Author:li_d
 * Created:2019/4/8
 */
public class Order {


    //订单Order对象创建完成之后,订单编号不能修改
    private final String orderId;

    //订单Order对象创建完成之后，goodsInfo属性实例化HashMap
    private final Map<String, Integer> goodsInfo = new HashMap<>();

    public Order(String orderId) {
        this.orderId = orderId;
    }


    /**
     * 订单添加商品
     *
     * @param goodsId 商品编号
     * @param count   数量
     */
    public void add(String goodsId, Integer count) {
        int sum;
        if(goodsInfo.containsKey(goodsId)){
            //如果订单中的商品信息包含指定的商品编号
            sum = goodsInfo.get(goodsId) + count;
        }else {
            sum = count;
        }
        this.goodsInfo.put(goodsId,sum);
    }

    public void cancel(String goodsId, Integer count) {
        if(this.goodsInfo.containsKey(goodsId)){
            int sum = this.goodsInfo.get(goodsId);
            sum = sum-count;
            sum =sum < 0 ? 0 : sum;
            if(sum == 0){
                this.goodsInfo.remove(goodsId);
            }else {
                this.goodsInfo.put(goodsId,sum);
            }
        }
    }

    public void clear() {
        this.goodsInfo.clear();
    }

    public String getOrderId() {
        return orderId;
    }

    public Map<String, Integer> getOrderInfo() {
        return this.goodsInfo;
    }
}
