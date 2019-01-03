package com.leavesbasil.bot.accountant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.logging.BotLogger;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        try {
            ApiContextInitializer.init();
        } catch (Exception e) {
            BotLogger.error("MAIN", e);
            throw e;
        }
        SpringApplication.run(Main.class, args);
    }
}
