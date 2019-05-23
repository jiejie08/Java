package com.ld;

/**
 * Author:li_d
 * Created:2019/4/9
 */
public class MyArrayList implements IArrayList{
    private int[] array;//保存数据空间
    private int size;//保存有效数据个数
    MyArrayList(int capacity){
        this.array = new int[capacity];
        this.size = 0;
    }

    //扩容
    private void ensureCapaticy(){
        if (this.size < this.array.length){
            return;
        }
        int capacity = this.array.length*2;
        int[] newArray = new int[capacity];
        for (int i=0;i<this.size;i++){
            newArray[i] = this.array[i];
        }
        this.array = newArray;
    }

    public void pushFront(int item) {
        ensureCapaticy();
        for (int i=size-1;i>=0;i--){
            this.array[i+1] = this.array[i];
        }
        this.array[0] = item;
        this.size++;
    }

    public void pushBack(int item) {
        ensureCapaticy();
        this.array[this.size] = item;
        this.size++;
    }

    public void add(int item, int index) {
        ensureCapaticy();
        if (index< 0 || index>this.size){
            throw new Error();
        }
        for (int i=size-1;i>=index;i--){
            this.array[i+1] = this.array[i];
        }
        this.array[index] = item;
        this.size++;
    }

    public void popFront() {
        for (int i=0;i<size;i++){
            this.array[i] = this.array[i+1];
        }
        this.array[this.size-1] = 0;
        this.size--;
    }

    public void popBack() {
        if (this.size == 0){
            throw new Error();
        }
        this.array[--this.size] = 0;
    }

    public void remove(int index) {
        if (index<0 || index>=size){
            throw new Error();
        }
        for (int i=index;i<size;i++){
            this.array[i] = this.array[i+1];
        }
        this.array[size-1] = 0;
        this.size--;
    }

    public void print() {
        for (int i=0;i<size;i++){
            System.out.println(this.array[i]);
        }
    }
}
