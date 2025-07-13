package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
//阻塞队列（take，put，poll方法） --》线程池（管理队列和执行线程的任务：run方法，核心方法，excute）
@Slf4j
public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS, 10,(task, queue)->{
            //死等策略
            queue.put(task);
        });
        for(int i = 0 ; i < 3 ; i++){
            int j = i ;
            threadPool.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    log.debug("执行任务: " + j);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
    }
}
@Slf4j
class ThreadPool{
    private BlockingQueue<Runnable> queue ;

    private HashSet<Worker> workers = new HashSet<Worker>();
    //核心线程数
    private int coreSize;
    //
    private long timeout;
    private TimeUnit timeUnit;

    private int capacity;

    private RejectPolicy<Runnable> rejectPolicy;
    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit,int capacity,RejectPolicy rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.queue = new BlockingQueue<>(capacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task){
        synchronized (workers){
            if(workers.size() < coreSize){
                log.debug("新增核心线程");
                Worker worker = new Worker(task);
                worker.start();
                workers.add(worker);
            }else{
                //如果阻塞队列已满,可以选择的策略
                //1.死等/带超时等待/放弃任务/抛出异常/让调用者自己执行任务
                //可以让调用者自己定义策略的实现
                log.debug("新增到任务队列");
                //queue.offer(task,timeout,timeUnit);
                queue.tryPut(rejectPolicy,task);
            }
        }
    }

    class Worker extends Thread{

        private Runnable task;
        public Worker(Runnable task) {
            this.task =  task;

        }
        @Override
        public void run() {
            while((task != null) || (task = queue.take()) != null){
                try{
                    log.debug("执行任务:{}",task);
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    task = null;
                }
            }
            synchronized (workers) {
                log.debug("线程退出,移除线程:{}",this);
                workers.remove(this);
            }
        }

    }
}

interface RejectPolicy<T>{
    void reject(T task,BlockingQueue<T> blockingQueue);


}
@Slf4j
class BlockingQueue<T>{
    private Deque<T> queue = new ArrayDeque<>();

    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    private ReentrantLock lock = new ReentrantLock();
    private Condition empty = lock.newCondition();

    private Condition full = lock.newCondition();

    public void put(T element){
            lock.lock();
            try{
                while(queue.size() == capacity){
                    try {
                        log.info("阻塞队列已满，请等待加入任务队列");
                        full.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                queue.addLast(element);
                log.info("加入到任务队列中");
                empty.signal();
            }finally{
                lock.unlock();
            }
    }

    public boolean offer(T task, long timeout, TimeUnit unit){
        lock.lock();
        try{
            long nanos = unit.toNanos(timeout);
            while(queue.size() == capacity){
                try {
                    if(nanos <= 0){
                        log.info("添加失败");
                        return false;
                    }
                    log.info("阻塞队列已满，请等待加入任务队列");
                    nanos = full.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            queue.addLast(task);
            log.info("加入到任务队列中");
            empty.signal();
            return true;
        }finally{
            lock.unlock();
        }
    }

    public T take(){
            lock.lock();
            try{
                while(queue.isEmpty()) {
                    try {
                        log.info("阻塞队列为空，请等待");
                        empty.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                T t = queue.removeFirst();
                full.signal();
                return t;
            }finally{
                lock.unlock();
            }
    }

    public T poll(long timeout, TimeUnit unit){
        lock.lock();
        try {
            long nanos = unit.toNanos(timeout);
            while(queue.isEmpty()){
                log.info("阻塞队列为空，请等待");
                if(nanos <= 0){
                    log.debug("超时");
                    return null;
                }
                try {
                    nanos = empty.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return queue.removeFirst();
        }finally {
            lock.unlock();
        }
    }


    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try{
            if(queue.size() == capacity){
                rejectPolicy.reject(task,this);
            }else{
                log.debug("加入队列");
                queue.addLast(task);
                empty.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
