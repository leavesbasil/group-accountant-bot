package com.leavesbasil.bot.accountant.service;

import org.telegram.telegrambots.meta.api.objects.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface Accountant {
    void registerUser(User user);

    void unregisterUser(User user);

    List<User> getUsers();

    void addPurchase(User user, String name, BigDecimal price);

    void removePurchase(User user, String name);

    List<Purchase> getPurchases();

    Set<Payment> calculate();
}
