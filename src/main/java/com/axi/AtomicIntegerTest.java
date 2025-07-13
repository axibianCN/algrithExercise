package com.axi;



import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class AtomicIntegerTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        AtomicInteger i = new AtomicInteger(5);
        //             value表示读取值 value*10表示设置的值，可以用任意的表达式运算
        //unsafe对象只能通过反射获得
        //Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        //theUnsafe.setAccessible(true);
        //Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        i.updateAndGet(value -> value * 10);
        System.out.println(i.get());
    }
    //updateAndGet的原理:基于CAS实现
    public static int updateAndGet(AtomicInteger i, IntUnaryOperator operator){
        while(true){
            int prev = i.get();
            int next = operator.applyAsInt(prev);
            if(i.compareAndSet(prev,next)){
                return next;
            }
        }
    }

}
