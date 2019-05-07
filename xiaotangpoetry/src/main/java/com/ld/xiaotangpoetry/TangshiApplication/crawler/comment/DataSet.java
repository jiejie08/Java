package com.ld.xiaotangpoetry.TangshiApplication.crawler.comment;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储清洗的数据
 * Author:li_d
 * Created:2019/3/17
 */
@ToString
public class DataSet {
    /**
     * data把DOM解析，清洗之后存储的数据，需要一个Map集合，获取真正的数据
     * 比如：
     * 标题：行宫
     * 作者：元稹
     * 正文：XXX
     */
    private Map<String,Object> data = new HashMap<>();

    /**
     * 存放数据
     * @param key
     * @param value
     */
    public void putData(String key,Object value){
        this.data.put(key, value);
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public Object getData(String key){
        return this.data.get(key);
    }

    /**
     * 获取所有信息
     * @return
     */
    public Map<String,Object> getData(){
        return new HashMap<>(this.data);//new产生新对象，数据不会被随意破坏
    }
}
