package com.leavesbasil.bot.accountant.command;

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
public class Remove extends BotCommand {

    private static final String COMMAND_IDENTIFIER = "remove";
    private static final String COMMAND_DESCRIPTION = "remove accounting data";

    @Autowired
    private AccountantManager accountantManager;

    /**
     * Construct a command
     */
    public Remove() {
        super(COMMAND_IDENTIFIER, COMMAND_DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        boolean status = accountantManager.remove(chat);
        String answer = status ? "Accounting data removed!" : "Accounting data not found!";
        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText(answer);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            BotLogger.error("REMOVED_COMMAND", e);
        }
    }
}
