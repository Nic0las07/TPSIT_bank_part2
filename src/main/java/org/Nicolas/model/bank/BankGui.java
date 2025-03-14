package org.Nicolas.model.bank;

import org.Nicolas.model.date.Date;

import java.util.Scanner;

public class BankGui {

    private final Scanner scanner = new Scanner(System.in);

    private void printGuiStartingSelections(){
        System.out.println("Available options: \n");
        System.out.println("  1 - LOGIN \n");
        System.out.println("  2 - REGISTER \n");
        System.out.println("  3 - QUIT \n");
        System.out.print("Enter your choice: ");
    }

    private void printGuiSelections() {
        System.out.println("Choose one of these options: ");
        System.out.println("1 - deposit      2 - withdrawal");
        System.out.println("3 - investment  4 - advance time");
        System.out.println("    5 - check your account");
        System.out.println("    6 - check your wallet");
        System.out.println("    7 - logout");
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

    private boolean loginGui(Bank mainBank, User currentUser){
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

    private boolean registerGui(Bank mainBank, User currentUser){
        System.out.println("REGISTRATION \n");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your Password: ");
        String password = scanner.nextLine();

        while(!mainBank.registerUser(username, password)){
            System.out.print("The user already exists, enter 'n' to go back or anything to continue: ");
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

    public void gui(Bank mainBank, Date mainDate) {
        User currentUser = null;

        int choice = 0;

        while(true){
            clear();
            printGuiStartingSelections();
            choice = intInput();
            scanner.nextLine();

            switch(choice){
                case 1:
                    clear();
                    if(!loginGui(mainBank, currentUser)){continue;}
                    break;
                case 2:
                    clear();
                    if(!registerGui(mainBank, currentUser)){continue;}
                    break;
                case 3:
                    return;
            }
        }

        //int choice = this.printGuiSelections(scanner);
        //double amount;

        /*
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

         */
    }

}
