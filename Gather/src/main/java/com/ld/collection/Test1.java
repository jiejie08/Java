package com.ld.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Author:li_d
 * Created:2019/3/24
 */
public class Test1 {
    public static void main(String[] args) {
        Map<Integer,String> map = new HashMap<Integer, String>();
        map.put(1,"你好");
        map.put(2,"hello");
        map.put(3,"Hello");
        //输出Map集合
        //1.Map->Set
        Set<Map.Entry<Integer,String>> set = map.entrySet();
        //2.取得Set接口迭代器
        Iterator<Map.Entry<Integer,String>> iterator = set.iterator();
        //3.迭代输出
        while (iterator.hasNext()){
            Map.Entry<Integer,String> entry = iterator.next();
            System.out.println(entry.getKey()+"="+entry.getValue());
        }
     }
}
