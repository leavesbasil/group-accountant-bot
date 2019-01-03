package com.leavesbasil.bot.accountant.service;

import org.telegram.telegrambots.meta.api.objects.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryAccountant implements Accountant {

    private ConcurrentHashMap<User, List<Purchase>> data = new ConcurrentHashMap<>();

    @Override
    public void registerUser(User user) {
        data.put(user, new ArrayList<>());
    }

    @Override
    public void unregisterUser(User user) {
        data.remove(user);
    }

    @Override
    public List<User> getUsers() {
        return Collections.unmodifiableList(new ArrayList<>(data.keySet()));
    }

    @Override
    public void addPurchase(User user, String name, BigDecimal price) {
        data.compute(user, (key, purchases) -> {
            if (purchases == null) {
                purchases = new ArrayList<>();
            }
            purchases.add(new Purchase(name, price));
            return purchases;
        });
    }

    @Override
    public void removePurchase(User user, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Purchase> getPurchases() {
        return Collections.unmodifiableList(data.entrySet()
                .stream()
                .flatMap(o -> o.getValue().stream())
                .collect(Collectors.toList()));
    }

    @Override
    public Set<Payment> calculate() {
        throw new UnsupportedOperationException();
    }
}
