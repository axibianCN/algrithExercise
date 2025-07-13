package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
@Slf4j
public class TestAQS {
    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(() -> {

            lock.lock();
            try {
                log.info("running");
            } finally {

                lock.unlock();
            }

        });

}

static class MyLock implements Lock {
    //实现AQS
    class MySync extends AbstractQueuedSynchronizer {
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                //加锁成功:设置owner为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        //是否持有独占锁
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }

    }

    private MySync sync = new MySync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override//可打断的加锁
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override//尝试加锁
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override//带超时时间的尝试加锁
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        //释放锁，并且唤醒阻塞线程
        sync.release(1);
    }

    @Override//成员变量
    public Condition newCondition() {
        return sync.newCondition();
    }
}
}