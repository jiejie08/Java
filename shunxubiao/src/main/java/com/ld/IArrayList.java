package com.ld;

/**
 * 相当于C语言的结构体
 * Author:li_d
 * Created:2019/4/9
 */
public interface IArrayList {
    //头插
    void pushFront(int item);
    //尾插
    void pushBack(int item);
    //插入到指定位置
    void add(int item,int index);
    //头删
    void popFront();
    //尾删
    void popBack();
    //删除指定位置
    void remove(int index);
    //打印
    void print();
}
