package com.axi;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

//同步设计模式之保护性暂停
//join的底层原理就是保护性暂停设计模式
//rpc框架常用
@Slf4j
public class GuardedObject {

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0 ; i < 3 ; i++){
            new People().start();
        }
        sleep(1000);
        for(Integer id: MailBoxes.getIds()){
            new Postman(id,"内容"+id).start();
        }
    }
}
@Slf4j
class People extends Thread{
    @Override
    public void run() {
        Guarded go = MailBoxes.createGuardedObject();
        log.debug("收信id:{}",go.getId());
        Object mail = null;
        try {
            mail = go.get(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.debug("收到信:{}",mail);
    }
}
@Slf4j
class Postman extends Thread{

    private int id;
    private String mail;
    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }
    @Override
    public void run() {
        Guarded go = MailBoxes.getGuardedObject(id);
        log.debug("送信id:{},信:{}",id,mail);
        go.complete(mail);
    }
}
class MailBoxes{
    private static Map<Integer, Guarded> boxes = new Hashtable<>();
    private static int id = 1;
    //防止id出现线程安全问题
    private static synchronized int generateId(){
        return id++;
    }

    public static Guarded createGuardedObject(){
       Guarded go = new Guarded(generateId());
       boxes.put(go.getId(),go);
       return go;

    }

    public static Guarded getGuardedObject(int id){
       return boxes.remove(id);
    }

    public static Set<Integer> getIds(){
        return boxes.keySet();
    }

}

class Guarded{

    private int id;
    private Object response;


    public Guarded(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object get(long timeout) throws InterruptedException {
       synchronized (this){
           //开始时间
           long begin = System.currentTimeMillis();
           //经历时间
           long passedTime  = 0;
           while(response == null){
               if(passedTime >= timeout){
                   break;
               }
               //虚假唤醒时，避免重复等待timeout时间，减去已经等待的时间
               this.wait(timeout - passedTime);
               passedTime = System.currentTimeMillis() - begin;
           }
       }
    return response;
    }
    public void complete(Object object) {
        synchronized (this) {
            this.response = object;
            this.notifyAll();
        }
    }
}
