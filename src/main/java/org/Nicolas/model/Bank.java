package org.Nicolas.model;

import java.util.Random;
import java.util.Scanner;

public class Bank {
    private double money;
    private User user;
    private Date time;

    public Bank() {
        this(0.0, new User(), new Date());
    }

    public Bank(double money, User user, Date time) {
        this.money = money;
        this.user = user;
        this.time = time;
    }

    public Bank(double money){
        this.money = money;
        this.user = new User();
        this.time = new Date();
    }

    public Bank(double money, User user){
        this.money = money;
        this.user = user;
        this.time = new Date();
    }

    private int printGuiSelections(Scanner scanner) {
        System.out.println("Choose one of these options:");
        System.out.println("1 - deposit      2 - withdrawal");
        System.out.println("3 - investment  4 - advance time");
        System.out.println("    5 - check your account");
        System.out.println("    6 - check your wallet");
        System.out.println("    7 - logout");

        return scanner.nextInt();
    }

    public void gui() {
        Scanner scanner = new Scanner(System.in);
        int choice = this.printGuiSelections(scanner);
        double amount;

        while (choice != 7) {
            switch (choice) {
                case 1:
                    System.out.println("How much do you want to deposit from your wallet?");
                    System.out.println("Remember that you have " + this.getWallet() + " euros");
                    amount = scanner.nextDouble();
                    this.deposit(amount);
                    break;
                case 2:
                    System.out.println("How much money do you want to withdraw to your wallet?");
                    System.out.println("Remember that you have " + this.getMoney() + " euros");
                    amount = scanner.nextDouble();
                    this.withdraw(amount);
                    break;
                case 3:
                    System.out.println("You can choose");
                    System.out.println(" - how much to invest (required)");
                    System.out.println(" - the duration of the investment (required)");
                    System.out.println("   short, medium, long");
                    System.out.println(" - the risk of the investment (required)");
                    System.out.println("   low, medium, high");
                    System.out.println("Remember that you have " + this.getMoney() + " euros");

                    String duration = scanner.next();
                    String risk = scanner.next();
                    amount = scanner.nextDouble();
                    this.invest(amount, duration, risk);
                    break;
                case 4:
                    System.out.println(this.time.getTime());
                    System.out.println("You can choose");
                    System.out.println(" - days (required)");
                    System.out.println(" - months (not required, 0 to ignore)");
                    System.out.println(" - years (not required, 0 to ignore)");
                    System.out.println("Every month, a 100-euro bonus is automatically added to your wallet");
                    int day = scanner.nextInt();
                    int month = scanner.nextInt();
                    int year = scanner.nextInt();
                    this.advanceTime(day, month, year);
                    System.out.println(this.time.getTime());
                    break;
                case 5:
                    System.out.println("In your account, you have " + this.getMoney() + " euros");
                    break;
                case 6:
                    System.out.println("In your wallet, you have " + this.getWallet() + " euros");
                    break;
                default:
                    System.out.println("Invalid value");
                    break;
            }
            choice = this.printGuiSelections(scanner);
        }
        scanner.close();
    }

    public void deposit(double amount) {
        if (this.user.getWalletMoney() - amount >= 0) {
            this.money += amount;
            this.user.addToWallet(-amount);
        } else {
            System.out.println("You don't have enough money to deposit");
        }
    }

    public void withdraw(double amount) {
        if (this.money - amount >= 0) {
            this.money -= amount;
            this.user.addToWallet(amount);
        } else {
            System.out.println("You don't have enough money to withdraw");
        }
    }

    public void invest(double amount, String duration, String risk) {
        if (this.money <= 0 || amount <= 0 || this.money - amount < 0) {
            System.out.println("No investment available: Account balance is zero or negative");
            return;
        }

        double profitMultiplier = 1;
        double riskMultiplier = 0;

        switch (duration) {
            case "short":
                this.advanceTime(0, 1, 0);
                profitMultiplier = 1.05;
                break;
            case "medium":
                this.advanceTime(0, 6, 0);
                profitMultiplier = 1.25;
                break;
            case "long":
                this.advanceTime(0, 0, 1);
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

        this.money += finalInvestment;
        System.out.println("Current account balance: " + this.getMoney() + " euros");
    }

    public void advanceTime(int day, int month, int year) {
        Date previousTime = new Date(this.time);
        this.time.advancement(day, month, year);
        int bonus = this.time.getDifferenceMonths(previousTime) * 100;
        this.user.addToWallet(bonus);
        System.out.println("Bonus of " + bonus + " euros added to your wallet!");
    }

    public double getMoney() {
        return this.money;
    }

    public double getWallet() {
        return this.user.getWalletMoney();
    }
}
