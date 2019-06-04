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

@Component
public class Join extends BotCommand {

    private static final String COMMAND_IDENTIFIER = "join";
    private static final String COMMAND_DESCRIPTION = "join to accounting group";

    @Autowired
    private AccountantManager accountantManager;

    /**
     * Construct a command
     */
    public Join() {
        super(COMMAND_IDENTIFIER, COMMAND_DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Accountant accountant = accountantManager.getAccountant(chat);
        accountant.registerUser(user);

        String answer = String.format("%s joined to accounting group!", user.getUserName());
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText(answer);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            BotLogger.error("JOIN_COMMAND", e);
        }
    }
}
