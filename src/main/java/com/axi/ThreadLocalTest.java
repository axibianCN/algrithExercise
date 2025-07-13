package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ThreadLocalTest {

    public static void main(String[] args) {
        SaleHouse saleHouse = new SaleHouse();
        saleHouse.sale();
        System.out.println(saleHouse.count.get());
        for(int i =0 ; i < 3;i++){
            new Thread(()->{
                int j = new Random().nextInt(10);
                for(int k = 0 ; k < j;k++){
                    saleHouse.sale();
                }
                log.info("sale houses:",saleHouse.count.get());
            }).start();
        }
        System.out.println(saleHouse.count.get());


    }
}

class SaleHouse{
    ThreadLocal<Integer> count = ThreadLocal.withInitial(()->0);
    public  void sale(){
        count.set(count.get()+1);
    }
}

class Solution {
    public boolean checkStraightLine(int[][] coordinates) {

        return false;
    }
}
