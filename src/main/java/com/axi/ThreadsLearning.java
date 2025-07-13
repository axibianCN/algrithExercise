package com.axi;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadsLearning {
    public static void main(String[] args) throws InterruptedException {
        race5();
    }

    public static void printabc(){
        Object o = new Object();
        //t 1: a t 2:b t 3:c
        final int[] t = {1};
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o){
                    while(t[0] !=1){
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print("a");
                    t[0] =2;
                    o.notifyAll();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o){
                    while(t[0] !=2){
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print("b");
                    t[0] =3;
                    o.notifyAll();
                }

            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o){
                    while(t[0] !=3){
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.print("c");
                    t[0] =1;
                    o.notifyAll();
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();

    }

    public static void printabc2(){
        ReentrantLock lock = new ReentrantLock();
        Semaphore semaphorea = new Semaphore(1);
        Semaphore semaphoreb = new Semaphore(0);
        Semaphore semaphorec = new Semaphore(0);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphorea.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.print("a");
                    }finally {
                    semaphoreb.release();

                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphoreb.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.print("b");
                }finally {
                    semaphorec.release();

                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    semaphorec.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.print("c");
                }finally {
                    semaphorea.release();

                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }

    public static void race5(){
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = new Random().nextInt(10) + 1;
                map.put(Thread.currentThread().getName(), time);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = new Random().nextInt(10) + 1;
                map.put(Thread.currentThread().getName(), time);
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = new Random().nextInt(10) + 1;
                map.put(Thread.currentThread().getName(), time);
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = new Random().nextInt(10) + 1;
                map.put(Thread.currentThread().getName(), time);
            }
        });
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = new Random().nextInt(10) + 1;
                map.put(Thread.currentThread().getName(), time);
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            System.out.println(entry.getKey() + "耗时" + entry.getValue());
        }
    }


}
