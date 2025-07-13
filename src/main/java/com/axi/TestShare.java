package com.axi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestShare {
    static int counter = 0 ;

    public static void main(String[] args) throws InterruptedException {
        //时间片轮转导致最后的结果不一定是0，某一个线程结果还没有存入，就切换成第二个线程，但切换回来后，写入了一个错误的值
        //指令的执行发生了交错
        Thread t1 = new Thread(() -> {
            synchronized (TestShare.class){
                for(int i = 0 ; i < 5000 ; i++){
                    counter++;
                    log.info("counter:{}",counter);
                }
            }

        });
        Thread t2 = new Thread(() -> {
            synchronized (TestShare.class){
                for(int i = 0 ; i < 5000 ; i++){
                    counter--;
                    log.info("counter:{}",counter);
                }
            }
        });
        t1.start();
        t2.start();

    }


}
