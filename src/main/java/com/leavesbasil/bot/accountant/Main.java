package com.leavesbasil.bot.accountant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("========== Run telegram bot application ==========");
        ApiContextInitializer.init();
        SpringApplication.run(Main.class, args);
    }
}
