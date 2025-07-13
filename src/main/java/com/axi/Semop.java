package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
@Slf4j
public class Semop {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for(int i = 0 ; i < 5;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        log.info("this acquired:{}",Thread.currentThread().getId());
                        Thread.sleep(2000);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }
}
