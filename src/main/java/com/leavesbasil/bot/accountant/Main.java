package com.leavesbasil.bot.accountant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//        System.out.println("========== Run telegram bot application ==========");
//        try {
//            ApiContextInitializer.init();
//        } catch (Exception e) {
//            System.out.println("========== Telegram api initialize error! ==========");
//            throw e;
//        }
//        System.out.println("========== Telegram api initialize success! ==========");
        SpringApplication.run(Main.class, args);
    }
}
