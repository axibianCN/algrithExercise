package com.axi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
//手写动态代理
public class DonamicProxy implements InvocationHandler {

    public static void main(String[] args) throws Throwable {
        Sell sell = new Sell();
        Method[] method = Sell.class.getMethods();
        Method method1 = null;
        for(int i = 0 ; i < method.length; i++){
            if(method[i].getName().equals("sell")){
                method[i].setAccessible(true);
                method1 = method[i];
            }
        }
        DonamicProxy donamicProxy = new DonamicProxy(sell);
        if(method1 != null){
            donamicProxy.invoke(sell,method1,null);
            return;
        }
        System.out.println("method not found");

    }

    private Object target;
    public DonamicProxy(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("ops before selling");
        Object result = method.invoke(target, args);
        System.out.println("ops after selling");
        return result;
    }
}

class Sell{
    public void sell(){
        System.out.println("selling...");
    }
}
