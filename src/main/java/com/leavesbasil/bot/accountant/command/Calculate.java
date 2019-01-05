package com.leavesbasil.bot.accountant.command;

import com.leavesbasil.bot.accountant.service.Accountant;
import com.leavesbasil.bot.accountant.service.AccountantManager;
import com.leavesbasil.bot.accountant.service.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.List;

@Component
public class Calculate extends BotCommand {

    @Autowired
    private AccountantManager accountantManager;

    /**
     * Construct a command
     */
    public Calculate() {
        super("calculate", "calculate payments for accounting group");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Accountant accountant = accountantManager.getAccountant(chat);
        List<Purchase> purchases = accountant.getPurchases();

        StringBuilder answer = new StringBuilder("I will be calculate soon! Purchases:\n");
        purchases.forEach(p -> answer.append(p.getName()).append(":").append(p.getPrice()).append("\n"));
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText(answer.toString());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            BotLogger.error("CALCULATE_COMMAND", e);
        }
    }
}
