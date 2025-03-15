package org.Nicolas.model.bank;

import org.Nicolas.model.date.Date;

import java.util.Scanner;

public class BankGui {

    private final Scanner scanner = new Scanner(System.in);
    private final Bank mainBank;
    private User currentUser;

    public BankGui(Bank mainBank){
        this.mainBank = new Bank(mainBank);
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
                return scanner.nextInt();
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
            scanner.nextLine();
            double amount = 0;

            switch (choice) {
                case 1:
                    clear();
                    System.out.println("DEPOSIT \n");
                    System.out.println("How much do you want to deposit from your wallet?");
                    System.out.println("Remember that you have " + this.currentUser.getWalletMoney() + " euros");
                    amount = scanner.nextDouble();
                    this.mainBank.deposit(currentUser, amount);
                    break;
                case 2:
                    clear();
                    System.out.println("WITHDRAWAL \n");
                    System.out.println("How much money do you want to withdraw to your wallet?");
                    System.out.println("Remember that you have " + this.currentUser.getBankBalance() + " euros");
                    amount = scanner.nextDouble();
                    this.mainBank.withdraw(currentUser, amount);
                    break;
                case 3:
                    clear();
                    System.out.println("INVESTMENT \n");
                    System.out.println("You can choose");
                    System.out.println(" - how much to invest (required)");
                    System.out.println(" - the duration of the investment (required)");
                    System.out.println("   short, medium, long");
                    System.out.println(" - the risk of the investment (required)");
                    System.out.println("   low, medium, high");
                    System.out.println("Remember that you have " + this.currentUser.getBankBalance() + " euros");

                    String duration = scanner.next();
                    String risk = scanner.next();
                    amount = scanner.nextDouble();
                    this.mainBank.invest(currentUser, amount, duration, risk);
                    break;
                case 4:
                    clear();
                    System.out.println("ADVANCE TIME \n");
                    System.out.println(this.mainBank.getTime());
                    System.out.println("You can choose");
                    System.out.println(" - days (required)");
                    System.out.println(" - months (not required, 0 to ignore)");
                    System.out.println(" - years (not required, 0 to ignore)");
                    System.out.println("Every month, a 100-euro bonus is automatically added to your wallet");
                    int day = scanner.nextInt();
                    int month = scanner.nextInt();
                    int year = scanner.nextInt();
                    this.mainBank.advanceTime(day, month, year);
                    System.out.println(this.mainBank.getTime());
                    break;
                case 5:
                    clear();
                    System.out.println("BANK BALANCE \n");
                    System.out.println("In your account, you have " + this.currentUser.getBankBalance() + " euros");
                    break;
                case 6:
                    clear();
                    System.out.println("WALLET \n");
                    System.out.println("In your wallet, you have " + this.currentUser.getWalletMoney() + " euros");
                    break;
                case 7:
                    clear();
                    System.out.println("TRANSACTIONS HISTORY \n");
                    break;
                case 8:
                    clear();
                    System.out.println("THANKS FOR USING OUT BANK");
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
            scanner.nextLine();

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
