package com.leavesbasil.bot.accountant.service;

import org.junit.Test;
import org.telegram.telegrambots.meta.api.objects.User;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class InMemoryAccountantTest {

    @Test
    public void testJoin() {
        User user = new User();
        Accountant sut = new InMemoryAccountant();
        sut.registerUser(user);
        assertEquals(1, sut.getUsers().size());
    }

    @Test
    public void testAddPurchase() {
        User user = new User();
        Accountant sut = new InMemoryAccountant();
        sut.registerUser(user);
        sut.addPurchase(user, "Tomato", BigDecimal.valueOf(25.5));
        assertEquals(1, sut.getPurchases().size());
    }

    @Test
    public void testCalculate() {
        User user1 = new User(1, "Garry", false, "Poter", "Oculus", "RU");
        User user2 = new User(2, "Germiona", false, "Grandger", "Botan", "RU");
        User user3 = new User(3, "Ron", false, "Weasly", "Red", "RU");
        Accountant sut = new InMemoryAccountant();
        sut.registerUser(user1);
        sut.registerUser(user2);
        sut.registerUser(user3);
        sut.addPurchase(user1, "Tomato", BigDecimal.valueOf(100));
        sut.addPurchase(user2, "Cucumber", BigDecimal.valueOf(0));
        sut.addPurchase(user3, "Potato", BigDecimal.valueOf(0));
        Set<Payment> payments = sut.calculate();
        payments.forEach(payment -> System.out.println(
                payment.getSource().getUserName()
                        .concat("-[")
                        .concat(String.valueOf(payment.getValue()))
                        .concat("]->")
                        .concat(payment.getTarget().getUserName()))
        );
        assertEquals(2, payments.size());
    }

}