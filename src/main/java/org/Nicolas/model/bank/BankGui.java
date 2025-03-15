package org.Nicolas.model.bank;

import java.util.Scanner;

public class BankGui {

    private final Scanner scanner = new Scanner(System.in);
    private final Bank mainBank;
    private User currentUser;

    public BankGui(Bank mainBank){
        this.mainBank = mainBank;
        this.currentUser = null;
    }

    private void printGuiStartingSelections(){
        System.out.println("Available options: \n");
        System.out.println("  1 - LOGIN \n");
        System.out.println("  2 - REGISTER \n");
        System.out.println("  3 - QUIT \n");
        System.out.print("Enter your choice: ");
    }

    private void printGuiSelections() {
        System.out.println("Available options: \n");
        System.out.println("  1 - DEPOSIT ");
        System.out.println("  2 - WITHDRAW ");
        System.out.println("  3 - INVEST ");
        System.out.println("  4 - ADVANCE TIME ");
        System.out.println("  5 - CHECK YOUR ACCOUNT ");
        System.out.println("  6 - CHECK YOUR WALLET ");
        System.out.println("  7 - CHECK YOUR TRANSACTIONS HISTORY ");
        System.out.println("  8 - LOGOUT \n");
        System.out.print("Enter your choice: ");
    }

    private void clear(){
        for(int i = 0; i < 100; i++){
            System.out.println();
        }
    }

    private int intInput(){
        while(true){
            try{
                int num = scanner.nextInt();
                scanner.nextLine();
                return num;
            }catch(Exception e){
                System.out.print("Please enter a valid number: ");
                scanner.nextLine();
            }
        }
    }

    private double doubleInput(){
        while(true){
            try{
                double num = scanner.nextDouble();
                scanner.nextLine();
                return num;
            }catch(Exception e){
                System.out.print("Please enter a valid number: ");
                scanner.nextLine();
            }
        }
    }

    private boolean loginGui(){
        System.out.println("LOGIN \n");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your Password: ");
        String password = scanner.nextLine();

        while(mainBank.loginUser(username, password) == null){
            System.out.print("The user doesn't exist, enter 'n' to go back or anything to continue: ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if(choice.equals("n")){return false;}

            System.out.print("Enter your username: ");
            username = scanner.nextLine();
            System.out.print("Enter your Password: ");
            password = scanner.nextLine();
        }
        currentUser = mainBank.loginUser(username, password);
        return true;
    }

    private void registerGui(){
        System.out.println("REGISTRATION \n");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your Password: ");
        String password = scanner.nextLine();

        while(!mainBank.registerUser(username, password)){
            System.out.print("The user already exists, enter 'n' to go back or anything to continue: ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if(choice.equals("n")){return;}

            System.out.print("Enter your username: ");
            username = scanner.nextLine();
            System.out.print("Enter your Password: ");
            password = scanner.nextLine();
        }
        currentUser = mainBank.loginUser(username, password);
    }

    private void loggedInGUI(){
        int choice = 0;

        do{
            clear();
            this.printGuiSelections();
            choice = intInput();
            double amount = 0;

            switch (choice) {
                case 1:
                    clear();
                    System.out.println("DEPOSIT \n");
                    System.out.println("Wallet money: " + this.currentUser.getWalletMoney() + " $");
                    System.out.print("-Enter the amount you would like to deposit: ");
                    amount = doubleInput();
                    this.mainBank.deposit(currentUser, amount);
                    break;
                case 2:
                    clear();
                    System.out.println("WITHDRAWAL \n");
                    System.out.println("Bank Balance: " + this.currentUser.getBankBalance() + " $");
                    System.out.print("-Enter the amount you would like to withdraw: ");
                    amount = doubleInput();
                    this.mainBank.withdraw(currentUser, amount);
                    break;
                case 3:
                    clear();
                    System.out.println("INVESTMENT \n");
                    System.out.println("Bank Balance: " + this.currentUser.getBankBalance() + " $");
                    System.out.print(" - Enter the amount to invest: ");
                    amount = doubleInput();
                    String duration = "";
                    do{
                        System.out.print(" - Enter the duration of the investment (short, medium, long): ");
                        duration = scanner.nextLine().trim().toLowerCase();
                    }while(!duration.equals("short") && !duration.equals("medium") && !duration.equals("long"));

                    String risk = "";
                    do{
                        System.out.print(" - Enter the risk of the investment (low, medium, high): ");
                        risk = scanner.nextLine().trim().toLowerCase();
                    }while(!risk.equals("low") && !risk.equals("medium") && !risk.equals("high"));

                    this.mainBank.invest(currentUser, amount, duration, risk);
                    break;
                case 4:
                    clear();
                    System.out.println("ADVANCE TIME \n");
                    System.out.println(this.mainBank.getTime());
                    System.out.println("Every month, a 100$ bonus is automatically added to your wallet");
                    System.out.print(" - Enter the days: ");
                    int day = intInput();
                    System.out.print(" - Enter the months: ");
                    int month = intInput();
                    System.out.print(" - Enter the years: ");
                    int year = intInput();
                    this.mainBank.advanceTime(day, month, year);
                    break;
                case 5:
                    clear();
                    System.out.println("BANK BALANCE \n");
                    System.out.println("-Balance: " + this.currentUser.getBankBalance() + " $");
                    scanner.nextLine();
                    break;
                case 6:
                    clear();
                    System.out.println("WALLET \n");
                    System.out.println("-Wallet: " + this.currentUser.getWalletMoney() + " $");
                    scanner.nextLine();
                    break;
                case 7:
                    clear();
                    System.out.println("TRANSACTIONS HISTORY \n");
                    currentUser.printHistory();
                    scanner.nextLine();
                    break;
                default:
                    break;
            }

        }while(choice != 8);
    }

    public void mainGui() {
        int choice = 0;

        while(true){
            clear();
            printGuiStartingSelections();
            choice = intInput();

            switch(choice){
                case 1:
                    clear();
                    if(!loginGui()){continue;}
                    loggedInGUI();
                    break;
                case 2:
                    clear();
                    registerGui();
                    break;
                case 3:
                    return;
            }
        }
    }

}
