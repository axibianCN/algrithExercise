package com.axi;

import lombok.extern.slf4j.Slf4j;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class ExcutorPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        //callable和future配合来处理有返回结果的任务。
        /*
        Future<String> future = pool.submit(()-> {
            log.info("running");
                Thread.sleep(1000);
                return "hello world";
        });

         */

        ExecutorService pool1 = Executors.newFixedThreadPool(3);

        //invokeAny:两个版本：带超时时间，不带超时时间。只要有一个任务执行成功，其他任务取消。
        String result = pool1.invokeAny(Arrays.asList(
                () ->{
                    Thread.sleep(1000);
            log.info("running");

            return "hello world1";
        },
                ()-> {
                    Thread.sleep(2000);
                    log.info("running");

                    return "hello world2";
                },
        ()-> {
            Thread.sleep(300);
            log.info("running");

            return "hello world3";
        }));
        log.info("result:{}",result);

        List<Runnable> runnables = pool1.shutdownNow();

        log.info("runnables:{}",runnables);


        //invokeAll:执行所有任务，两个版本：带超时时间，不带超时时间

        //关闭线程池
        //shutdown：不会接收新任务，打断所有空闲线程，但已提交任务会继续执行，不会阻塞调用线程的执行。
        //配合awaitTermination可以实现阻塞效果，带超时时间和单位
        //shutdownNow：不会接收新任务，打断所有线程，会尝试终止还未提交任务，返回未执行的任务列表。

        ScheduledExecutorService pool3 = Executors.newScheduledThreadPool(3);

        ScheduledFuture<?> running = pool3.scheduleAtFixedRate(() -> {
            log.info("running");
        },1,1, TimeUnit.SECONDS);
        pool3.scheduleWithFixedDelay(()->{
            log.info("running");
        },1,1,TimeUnit.SECONDS);



    }
}
