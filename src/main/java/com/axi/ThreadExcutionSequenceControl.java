package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ThreadExcutionSequenceControl {
    static final Object lock = new Object();

    private static int flag = 1;
    public static ReentrantLock lockR = new ReentrantLock();

    static final Condition c1 = lockR.newCondition();


    static final Object lock2 =new Object();
    static int t1 = 0;
    static boolean t2 = true;

    static boolean t3 = false;

    static boolean t4 = false;
    public static void main(String[] args) throws InterruptedException {
        //基于wait/notify和条件变量的方式进行线程的同步控制


        WaitNotify waitNotify = new WaitNotify();
        new Thread(() -> {
            try {
                waitNotify.print("a",1,2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                waitNotify.print("b",2,3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            try {
                waitNotify.print("c",3,1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Printone2100 printone2100 = new Printone2100();

        new Thread(() -> {
            try {
                printone2100.print(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t7").start();

        new Thread(() -> {
            try {
                printone2100.print(false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"t6").start();




    }
}
@Slf4j
class WaitNotify{

    private static int flag= 1;
    private static int loopnumber = 5;
    public void print(String letter, int waitflag, int nextFlag) throws InterruptedException {
        for(int i=0; i < loopnumber;i++){
            synchronized (this){
                while (waitflag !=flag ){
                    this.wait();
                }
                System.out.print(letter);
                flag = nextFlag;
                this.notifyAll();
            }
        }




    }
}
@Slf4j

class Printone2100{
    private int number = 0 ;

    private int loopnumber = 50;

    private boolean flag = true;
    public void print(boolean waitflag) throws InterruptedException {
        for(int i=0; i < loopnumber;i++){
            synchronized (this){
                while (waitflag !=flag ){
                    this.wait();
                }
                log.info("{}",number++);
                flag = !waitflag;
                this.notifyAll();
            }
        }




    }


}
