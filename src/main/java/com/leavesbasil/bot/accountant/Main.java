package com.leavesbasil.bot.accountant;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        System.out.println("========== Run telegram bot application ==========");
        ApiContextInitializer.init();
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            System.out.println("Read parameters failed!");
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        String name = properties.getProperty("telegram.bot.name");
        String token = properties.getProperty("telegram.bot.token");

        System.out.println(name + " register..");
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {

            botsApi.registerBot(new GroupAccountantBot(name, token));
        } catch (TelegramApiException e) {
            System.out.println(name + " register failed!");
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        System.out.println(name + " registered!");
    }
}
