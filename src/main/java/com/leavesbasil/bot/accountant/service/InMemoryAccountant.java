package com.leavesbasil.bot.accountant.service;

import org.telegram.telegrambots.meta.api.objects.User;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
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
        int users = data.size();
        if (users < 1) {
            return Collections.emptySet();
        }

        BigDecimal total = data.values().stream()
                .flatMap(Collection::stream)
                .map(Purchase::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total: " + total);

        BigDecimal payment = total.divide(BigDecimal.valueOf(users), BigDecimal.ROUND_CEILING);
        System.out.println("Payment for one: " + payment);

        Function<Map.Entry<User, List<Purchase>>, Account> accountMapper = entry -> {
            BigDecimal balance = entry.getValue().stream()
                    .map(Purchase::getPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::subtract)
                    .add(payment);
            return new Account(entry.getKey(), balance);
        };

        LinkedList<Account> accounts = data.entrySet().stream()
                .map(accountMapper)
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println("Balance on start:");
        accounts.forEach(account -> System.out.println(account.user.getUserName().concat(":").concat(String.valueOf(account.balance))));


        Set<Payment> payments = new HashSet<>();
        Collections.sort(accounts);
        while (accounts.getFirst().balance.compareTo(BigDecimal.ZERO) != 0 && accounts.getLast().balance.compareTo(BigDecimal.ZERO) != 0) {
            Account first = accounts.pollFirst();
            Account last = accounts.pollLast();
            if (first == null || last == null) {
                throw new IllegalStateException("Accounts not found!");
            }

            BigDecimal value = first.balance.abs().compareTo(last.balance.abs()) < 0 ? first.balance.abs() : last.balance.abs();
            payments.add(new Payment(last.user, first.user, value));

            first.balance = first.balance.add(value);
            last.balance = last.balance.subtract(value);
            accounts.add(first);
            accounts.add(last);

            Collections.sort(accounts);
        }

        System.out.println("Balance on end:");
        accounts.forEach(account -> System.out.println(account.user.getUserName().concat(":").concat(String.valueOf(account.balance))));
        return payments;
    }

    static class Account implements Comparable<Account> {
        private User user;
        private BigDecimal balance;

        public Account(User user, BigDecimal balance) {
            this.user = user;
            this.balance = balance;
        }

        @Override
        public int hashCode() {
            return user.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Account)) {
                return false;
            }
            Account account = (Account) obj;
            return user.equals(account.user);
        }

        @Override
        public int compareTo(Account o) {
            if (o == null) {
                throw new NullPointerException();
            }
            if (this == o) {
                return 0;
            }
            return balance.compareTo(o.balance);
        }
    }
}
