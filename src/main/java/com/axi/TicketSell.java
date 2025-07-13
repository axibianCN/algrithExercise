package com.axi;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

@Slf4j
public class TicketSell {

    public static void main(String[] args) throws InterruptedException {
        TicketWindow ticketWindow = new TicketWindow(100000);
        List<Integer> amountList = new Vector<>();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0 ; i < 2000 ; i++){
            Thread thread = new Thread(()->{
                int amount = TicketSell.randomAmount();
                int sellamount = ticketWindow.sell(amount);

                amountList.add(sellamount);
            });
            //只会在主线程使用，不会被多个线程使用，所以用arraylist
            threads.add(thread);
            thread.start();

        }
        for(Thread thread : threads){
            thread.join();
        }
        //统计卖出票数和剩余票数
        log.debug("shengyu:{}",ticketWindow.getCount());
        //卖出票数
        log.debug("maichu:{}",amountList.stream().mapToInt(Integer::intValue).sum());

    }
    static Random random = new Random();
    public static int randomAmount(){return random.nextInt(5)+1;}
}

class TicketWindow{
    private int count;
    public TicketWindow(int count){
        this.count = count;
    }

    public int getCount(){
        return count;
    }
    public synchronized int sell(int amount){
        if(count >= amount){
            count -= amount;
            return amount;
        }else{
            return 0;
        }
    }
}
