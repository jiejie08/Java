package com.ld;

/**
 * Author:li_d
 * Created:2019/4/9
 */
public class Main {
    public static void main(String[] args) {
        MyArrayList arrayList = new MyArrayList(10);
        arrayList.pushBack(1);
        arrayList.pushBack(2);
        arrayList.pushBack(3);
        arrayList.pushBack(4);
        arrayList.popBack();
        arrayList.pushFront(4);
        arrayList.pushFront(5);
        arrayList.popFront();
        arrayList.popFront();
        arrayList.add(4,1);
        arrayList.remove(1);
        arrayList.print();

    }
}
