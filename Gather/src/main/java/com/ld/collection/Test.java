package com.ld.collection;

import java.util.*;

/**
 * Author:li_d
 * Created:2019/3/21
 */
public class Test {
    public static void main(String[] args) {
        Map<Integer,String> map = new HashMap<Integer, String>();
        map.put(1,"hello");
        //当key值重复，再次put变为相应value的更新操作
        map.put(1,"Hello");
        map.put(3,"java");
        map.put(2,"class");
        map.put(null,null);
        map.put(null,null);
        map.put(4,null);
        System.out.println(map.get(1));
        //取得当前Map中的所有key值
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> iterator = keySet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        //取得所有value值
        /*List<String> list = (List<String>) map.values();
        Iterator<String> iterator1 = list.iterator();
        while (iterator1.hasNext()){
            System.out.println(iterator1.next());
        }*/
        for (Integer i : keySet){
            System.out.println(map.get(i));
        }
    }
}
