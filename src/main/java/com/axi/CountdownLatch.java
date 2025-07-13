package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Slf4j
public class CountdownLatch {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for(int i = 0 ; i < 10 ;i++){
            int j = i ;
            pool.submit(()->{
                try {
                    int progress = 100;
                    while(progress != 0){
                        log.info("Thread{}: is loading {}",j,progress);
                        Thread.sleep(j*100);
                        progress-=10;
                    }
                    if(progress == 0){
                        countDownLatch.countDown();
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        log.info("主线程等待所有子线程执行完毕");
        countDownLatch.await();
        log.info("所有子线程执行完毕，主线程继续执行");
    }
}
