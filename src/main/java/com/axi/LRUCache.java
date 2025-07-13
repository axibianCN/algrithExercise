package com.axi;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache extends LinkedHashMap<Integer,Integer> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 1.0f, true);
        this.capacity = capacity;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer,Integer> entry){
        return size()>capacity;
    }


    public static void main(String[] args) {
        int[] a = new int[]{1,2,2,3,3};
        int left = 0;
        int b = a.length-1;
        //0 6
        while(left < b){
            System.out.println("left:"+left);
            System.out.println("b:"+b);
            int mid = (b + left)/2;
            if(a[mid] > a[mid-1]){
                b = mid - 1;
            }
            if(a[mid] == a[mid-1]){
                left = mid + 1;
            }
        }
        System.out.println("left:"+left);
        System.out.println("b:"+b);
        System.out.println(a[left]);
    }
    // 0 1 2 3 4 5 6
    // 1 1 2 2 3 3 4
    // 0 1 2 3 4 5 6
    // 1 2 2 3 3 4 4
}
