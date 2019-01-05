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

    private static final String COMMAND_IDENTIFIER = "buy";
    private static final String COMMAND_DESCRIPTION = "add purchase. Use /buy [command] [name] [price] for add new item";

    @Autowired
    private AccountantManager accountantManager;

    /**
     * Construct a command
     */
    public Buy() {
        super(COMMAND_IDENTIFIER, COMMAND_DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Accountant accountant = accountantManager.getAccountant(chat);
        accountant.addPurchase(user, arguments[0], new BigDecimal(arguments[1]));

        String answer = String.format("Purchase (%s) added!", arguments[0]);
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
