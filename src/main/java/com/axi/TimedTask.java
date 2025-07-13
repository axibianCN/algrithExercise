package com.axi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;
import java.util.Timer;

public class TimedTask {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //基于timertask实现:每隔10s执行一次
        String fomatDateTime = now.format(dateTimeFormatter);
        System.out.println(fomatDateTime);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时任务执行 at " + LocalDate.now());
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
}


