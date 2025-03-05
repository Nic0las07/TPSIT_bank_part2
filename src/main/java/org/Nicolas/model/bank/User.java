package org.Nicolas.model.bank;

import java.util.Random;

public class User {

    String username;
    String password;
    double bankBalance;
    private double walletMoney;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.walletMoney = 100.0;
    }

    public boolean deposit(User user, double amount) {
        if(walletMoney - amount < 0){return false;}
        bankBalance += amount;
        walletMoney -= amount;
        return true;
    }

    public boolean withdraw(User user, double amount) {
        if(bankBalance - amount < 0){return false;}
        bankBalance -= amount;
        walletMoney += amount;
        return true;
    }

    public boolean invest(Bank bank, double amount, String duration, String risk) {

        if (bankBalance <= 0 || amount <= 0 || bankBalance - amount < 0) {return false;}

        double profitMultiplier = 1;
        double riskMultiplier = 0;

        switch (duration) {
            case "short":
                bank.advanceTime(0, 1, 0);
                profitMultiplier = 1.05;
                break;
            case "medium":
                bank.advanceTime(0, 6, 0);
                profitMultiplier = 1.25;
                break;
            case "long":
                bank.advanceTime(0, 0, 1);
                profitMultiplier = 1.4;
                break;
        }

        double finalInvestment = (amount * profitMultiplier) - amount;

        switch (risk) {
            case "low":
                riskMultiplier = 1;
                finalInvestment *= 1.5;
                break;
            case "medium":
                riskMultiplier = 1.5;
                finalInvestment *= 2.5;
                break;
            case "high":
                riskMultiplier = 3;
                finalInvestment *= 4;
                break;
        }

        Random random = new Random();
        double randomValue = random.nextInt(8000) * riskMultiplier;

        System.out.println("Calculating final gain or loss...");

        if (randomValue < 4500) {
            System.out.println("Gain!");
            System.out.println("You earned " + finalInvestment + " euros");
        } else {
            System.out.println("Loss!");
            System.out.println("You lost " + finalInvestment + " euros");
            finalInvestment = -finalInvestment;
        }

        bankBalance += finalInvestment;
        System.out.println("Current account balance: " + bankBalance + " euros");
        return true;
    }

}
