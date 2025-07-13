package com.axi;

public class DoubleCheckingLocking {

    //1.volatile保证指令有序性，防止在调用构造方法前已经赋值的情况下被另一个线程拿到没有调用构造方法的实例
    private volatile static DoubleCheckingLocking instance = null;

    public static DoubleCheckingLocking getInstance(){
        //1.double check
        if(instance!=null){
            return instance;
        }
        synchronized (DoubleCheckingLocking.class){
            //2.这里再次判断是为了防止多线程下的并发问题，如果有两个线程一个进入了syn，另一个阻塞，前面的已经创建了，后一个如果不判断就会再创建一个。
            if(instance==null){
                instance = new DoubleCheckingLocking();
                return instance;
            }
        }
        return instance;
    }
}
