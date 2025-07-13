package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
//sleep和wait区别
@Slf4j
public class TestWaitAndSleep {
    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock){

                try {
                    log.info("我先拿到锁,但是wait后把锁释放");
                    lock.wait();
                    log.info("锁释放了，我可以执行了");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        },"t1").start();

        new Thread(() -> {
            synchronized (lock){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("sleep 但是不释放锁");
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"t2").start();

        new Thread(() -> {
            synchronized (lock){
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("等待锁的释放 然后notify");
                lock.notify();
            }
        },"t3").start();





    }
}
