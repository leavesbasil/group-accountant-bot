package com.leavesbasil.bot.accountant.command;

import com.leavesbasil.bot.accountant.service.Accountant;
import com.leavesbasil.bot.accountant.service.AccountantManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.math.BigDecimal;

@Component
public class Buy extends BotCommand {

    @Autowired
    private AccountantManager accountantManager;

    /**
     * Construct a command
     */
    public Buy() {
        super("buy", "add purchase with parameters: $name $price");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Accountant accountant = accountantManager.getAccountant(chat);
        accountant.addPurchase(user, arguments[0], BigDecimal.valueOf(Double.valueOf(arguments[1])));

        String answer = String.format("Purchase(%s) has add!", arguments[0]);
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText(answer);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            BotLogger.error("BUY_COMMAND", e);
        }
    }
}
