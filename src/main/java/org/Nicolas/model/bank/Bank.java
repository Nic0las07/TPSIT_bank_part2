package org.Nicolas.model.bank;

import org.Nicolas.model.date.Date;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank {

    private String bankName;
    private ArrayList<User> usersList;
    private final Date time;

    public Bank(String bankName) {
        this.time = new Date();
        this.bankName = bankName;
    }

    public Bank(String bankName, Date time) {
        this.bankName = bankName;
        this.time = time;
    }

    public boolean registerUser(String username, String password){
        for(User user : usersList){
            if(user.username == username){return false;}
        }
        usersList.add(new User(username, password));
        return true;
    }

    public int loginUser(String username, String password){
        User user = new User(username, password);
        if(!usersList.contains(user)){return -1;}
        return usersList.indexOf(user);
    }

    public void advanceTime(int day, int month, int year) {
        Date previousTime = new Date(this.time);
        this.time.advancement(day, month, year);
        int bonus = this.time.getDifferenceMonths(previousTime) * 100;
        for (User user : usersList) {
            user.bankBalance += bonus;
        }
    }

/*
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

 */

}
