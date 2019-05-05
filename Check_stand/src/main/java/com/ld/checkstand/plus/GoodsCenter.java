package com.ld.checkstand.plus;

/**
 * 商品中心
 * Author:li_d
 * Created:2019/4/8
 */
public interface GoodsCenter {
    /**
     * 添加商品
     * @param goods
     */
    void addGoods(Goods goods);

    /**
     * 移除商品
     * @param goodsId
     */
    void removeGoods(String goodsId);

    /**
     * 更新商品
     * @param goods
     */
    void updateGoods(Goods goods);

    /**
     * 判断商品是否存在
     * @param goodsId
     * @return
     */
    boolean isExistGoods(String goodsId);

    /**
     * 通过商品编号获取商品
     * @param goodsId
     * @return
     */
    Goods getGoods(String goodsId);

    /**
     * 列出商品信息
     * @return
     */
    String listGoods();

    /**
     * 保存商品信息
     */
    void store();

    /**
     * 加载商品信息
     */
    void load();

}
