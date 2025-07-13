package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Learning {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try {
                log.info("洗水壶...");
                TimeUnit.SECONDS.sleep(1);
                log.info("烧开水...");
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
        Thread t2 = new Thread(()->{
           log.info("洗茶壶...，洗茶杯，拿茶叶");
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                //join实现线程间的同步等待
                t1.join();
                log.info("t2开始泡茶");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();



    }
}

class TwoPhase{
    private Thread monitor;

    public void start(){
        monitor = new Thread(()->{
            Thread currentThread = Thread.currentThread();
            while(true){
                if(currentThread.isInterrupted()){
                    System.out.println("Thread is interrupted");
                    System.out.println("处理后续...");
                    break;
                }
                try {
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("异常打断...");
                        currentThread.interrupt();

                }


            }
        },"monitor");
        monitor.start();
    }

    public void stop(){
        monitor.interrupt();
    }
}
