package com.ld;

import java.util.Arrays;

/**
 * Author:li_d
 * Created:2019/5/2
 */
public class Sort {
    private static void Swap(int[] array,int a, int b) {
        int t = array[a];
        array[a] = array[b];
        array[b] = t;
    }

//    public static void insertSort(int[] array){
//        //从后往前排序
//        for (int i = 0;i < array.length;i++){
//            for (int j = i-1;j >= 0;j--){
//                if (array[i] >= array[j]){
//                    break;
//                }else {
//                    Swap(array,i,j);
//                }
//            }
//        }
//    }

    /**
     * 直接插入（***）
     * 时间复杂度：O(n^2),最好的情况：已经有序，倒着找，正着则不满足O(n)
     * 空间复杂度：O(1)
     * 稳定
     * @param array
     */
    public static void insertSort(int[] array){
        for (int i = 0;i < array.length;i++) {
            int key = array[i];
            int j = i-1;
            for (;j >= 0 && key < array[j];j--){
                array[j+1] = array[j];
            }
            array[j+1] = key;
        }
    }
    /**
     * 二插排序
     */
    public static void BinarySort(int[] array){
        for (int i = 0;i < array.length;i++) {
            int key = array[i];
            int left = 0;
            int right = i;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (key >= array[mid]) {
                    left = mid + 1;
                } else {
                    //[ )
                    right = mid;
                }
            }
            int pos = left;
            for (int k = i;k > pos;k--){
                array[k] = array[k-1];
            }
            array[pos] = key;
        }
    }

    /**
     * 希尔排序
     * 最坏的情况概率 变小
     * @param array
     */
    public static void ShellSort(int[] array){
        int i = 0;
        int gap = array.length;
        while (gap > 1)
        {
            gap = gap / 3 + 1;
            for (i = 0; i < array.length - gap; i += gap)
            {
                int end = i;
                int temp = array[end + gap];
                while (end >= 0 && array[end] > temp)
                {
                    array[end + gap] = array[end];
                    end -= gap;
                }
                array[end + gap] = temp;
            }
        }
    }

    //选择排序
    public static void selectSort(int[] array){
        for (int i = 0;i < array.length;i++){
            int min = i;
            for (int j = i+1;j < array.length;j++){
                if (array[j] < array[min]){
                    min = j;
                }
            }
            Swap(array,min,i);
        }
    }
    //优化的选择排序
    public static void selectSort1(int[] array){
        int begin = 0, end = array.length - 1;
        while (begin < end)
        {
            int i = 0;
            int min = begin, max = end;
            for (i = begin; i <= end; ++i)
            {
                if (array[i]<array[min])
                    min = i;      //最小数的下标
                if (array[i]>array[max])
                    max = i;      //最大数的下标
            }
            Swap(array,begin,min);
            if (max == begin)     //优化，防止最大的数在头，最小的数在尾被交换了两次
            {
                max = min;
            }
            Swap(array,end,max);
            begin++;
            end--;
        }
    }

    //冒泡排序
    public static void bubbleSort(int[] array){
        for (int i = 1;i < array.length;i++){
            int flag = 0;
            for (int j = 0;j < array.length-i ;j++){
                if (array[j] > array[j+1]){
                    Swap(array,j,j+1);
                }
                flag = 1;
            }
            if (flag == 0){
                break;
            }
        }
    }

    //堆的向下调整算法
    private static void AdjustDown(int[] array, int size, int index) {
        //1.判断叶子结点
        while (2*index+1 < size){
            //2.找到最大孩子的下标
            int max = 2*index+1;//左孩子
            if (max+1 < size && array[max+1] > array[max]){
                max++;
            }
            //3.判断最大的孩子和根的值
            if (array[index] < array[max]){
                Swap(array,index,max);
                index = max;
                max = 2*index+1;
            }else {
                //4.根的值比较大，可以直接结束，不交换也不继续往下走
                break;
            }
        }
    }

    public static void heapSort(int[] array){
        //建堆
        for (int i = (array.length-2)/2;i >= 0;i--){
            AdjustDown(array,array.length,i);
        }
        //排序
        for (int i = 0;i < array.length;i++){
            //无序[0,length-i-1]
            //有序[lenght-i,length)
            Swap(array,0,array.length-1-i);
            AdjustDown(array,array.length-1-i,0);
        }
//        int size = array.length;
//        while (size-1 > 0){
//            Swap(array,0,size-1);
//            size--;
//            AdjustDown(array,size,0);
//        }
    }

    //hover法
    private static int PartSort1(int[] array, int left, int right) {
        int key = array[right];//基准值
        int begin = left, end = right;
        while (begin < end)
        {
            while (begin < end && array[begin] <= key)
            {
                ++begin;
                //array[begin] > key
            }
            while (begin < end && array[end] >= key)
            {
                --end;
                //array[end] < key
            }
            Swap(array,begin,end);
        }
        //begin与end相遇，把key值放到此处
        Swap(array,begin,right);
        return begin;

    }

    //挖坑法
    private static int PartSort2(int[] array, int left, int right){
        int key = array[right];
        int begin = left,end = right;
        while (begin < end){
            while (begin < end && array[begin] <= key){
                begin++;
            }
            array[end] = array[begin];//左边坑添右边坑，不能是left与right,因为这两个不变换
            while (begin < end && array[end] >= key)
            {
                --end;
                //array[end] < key
            }
            array[begin] = array[end];//右边坑添左边坑
        }
        array[begin] = key;
        return begin;
    }

    //前后下标法:保证key下标前面的都比基准值小，key与i之间的都比基准值大
    private static int PartSort3(int[] array, int left, int right){
        int key = left;
        for (int i = left;i < right;i++){
            if (array[i] < array[right]){
                Swap(array,key,i);
                key++;
            }
        }
        Swap(array,key,right);
        return key;
    }

    //三数取中法，尽可能避免快排最坏的情况
    private static int ThreeMiddleMethod(int[] array,int left,int right){
        int mid = left + (right - left) / 2;
        if (array[left] > array[right]){
            if (array[left] < array[mid]){
                return left;
            }else if (array[mid] > array[right]){
                return mid;
            }else {
                return right;
            }
        }else {
            if (array[right] < array[mid]){
                return right;
            }else if (array[mid] > array[left]){
                return mid;
            }else {
                return left;
            }
        }
    }

    private static void quickSortInner(int[] array, int left, int right) {
        if (left >= right){//需要判断左右区间是否存在，看是否还进行分支
            return;
        }
        //找基准值，把区间分为三部分
        int middleIndex = ThreeMiddleMethod(array,left,right);
        Swap(array,middleIndex,right);

        //int div = PartSort1(array, left, right);
        //int div = PartSort2(array, left, right);
        int div = PartSort3(array, left, right);
        //递归
        quickSortInner(array, left, div-1);
        quickSortInner(array, div+1, right);
    }

    //快排
    public static void quickSort(int[] array){
        //[]
        quickSortInner(array,0,array.length-1);
    }

    private static void mergeSortInner(int[] array,int low,int high,int[] extra){
        if (low >= high - 1){
            return;
        }

        int mid = low + (high - low) / 2;
        mergeSortInner(array, low, mid,extra);
        mergeSortInner(array, mid, high,extra);

        merge(array,low,mid,high,extra);
    }

    private static void merge(int[] array, int low, int mid, int high, int[] extra) {
        extra = new int[high - low];
        int i = low,j = mid,x = 0;
        while (i < mid && j < high){
            if (array[i] < array[j]){
                extra[x++] = array[i++];
            }else {
                extra[x++] = array[j++];
            }
        }

        //左边没走完
        while (i < mid){
            extra[x++] = array[i++];
        }
        //右边没走完
        while (j < high){
            extra[x++] = array[j++];
        }
        //把数放回去
        for (int k = low;k < high;k++){
            array[k] = extra[k - low];
        }
    }

    //归并排序
    public static void mergeSort(int[] array){
        int[] extra = new int[array.length];
        mergeSortInner(array,0,array.length,extra);
    }

    //归并的非递归
    public static void mergeSortNorR(int[] array){
        int[] extra = new int[array.length];
        for (int i = 1;i < array.length;i *= 2){
            for (int j = 0;j < array.length;j += 2*i){
                int low = j;
                int mid = low + i;
                if (mid >= array.length){
                    continue;
                }
                int high = mid + i;
                if (high > array.length){
                    high = array.length;
                }

                merge(array,low,mid,high,extra);
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {9,3,1,5,2,4,3,8,7,6};
        //insertSort(array);
        //BinarySort(array);
        //ShellSort(array);
        //selectSort(array);
        //selectSort1(array);
        //bubbleSort(array);
        //heapSort(array);
        //quickSort(array);
        //mergeSort(array);
        mergeSortNorR(array);

        System.out.println(Arrays.toString(array));
    }
}
