package com.leavesbasil.bot.accountant.command;

import com.leavesbasil.bot.accountant.service.Accountant;
import com.leavesbasil.bot.accountant.service.AccountantManager;
import com.leavesbasil.bot.accountant.service.Payment;
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
import java.util.Set;

@Component
public class Calculate extends BotCommand {

    private static final String COMMAND_IDENTIFIER = "calculate";
    private static final String COMMAND_DESCRIPTION = "calculate payments for accounting group";

    @Autowired
    private AccountantManager accountantManager;

    /**
     * Construct a command
     */
    public Calculate() {
        super(COMMAND_IDENTIFIER, COMMAND_DESCRIPTION);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Accountant accountant = accountantManager.getAccountant(chat);
        List<Purchase> purchases = accountant.getPurchases();
        Set<Payment> payments = accountant.calculate();

        StringBuilder answer = new StringBuilder();
        writePurchases(answer, purchases);
        writePayments(answer, payments);

        SendMessage message = new SendMessage()
                .setChatId(chat.getId())
                .setText(answer.toString());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            BotLogger.error("CALCULATE_COMMAND", e);
        }
    }

    private void writePurchases(StringBuilder builder, List<Purchase> purchases) {
        builder.append("Purchases:\n");
        purchases.forEach(p -> writePurchase(builder, p));
    }

    private void writePurchase(StringBuilder builder, Purchase purchase) {
        builder.append(purchase.getName()).append(":").append(purchase.getPrice()).append("\n");
    }

    private void writePayments(StringBuilder builder, Set<Payment> payments) {
        builder.append("Payments:\n");
        payments.forEach(p -> writePayment(builder, p));
    }

    private void writePayment(StringBuilder builder, Payment payment) {
        builder.append(payment.getSource().getUserName()).append("-[").append(payment.getValue()).append("]->")
                .append(payment.getTarget().getUserName()).append("\n");
    }
}
