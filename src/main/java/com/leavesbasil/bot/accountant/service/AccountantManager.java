package com.leavesbasil.bot.accountant.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountantManager {

    private final Map<Long, Accountant> accountants;

    public AccountantManager() {
        this.accountants = new ConcurrentHashMap<>();
    }

    public Accountant getAccountant(Chat chat) {
        Accountant accountant = accountants.get(chat.getId());
        if (accountant == null) {
            accountant = new InMemoryAccountant();
            accountants.put(chat.getId(), accountant);
        }
        return accountant;
    }

    public boolean remove(Chat chat) {
        Accountant accountant = accountants.get(chat.getId());
        if (accountant != null) {
            accountants.remove(chat.getId());
        }
        return false;
    }
}
