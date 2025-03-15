package org.Nicolas.model.bank;

import org.Nicolas.model.date.Date;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Bank {

    private String bankName;
    private final ArrayList<User> usersList;
    private final Date time;

    public Bank(String bankName) {
        this.time = new Date();
        this.bankName = bankName;
        this.usersList = new ArrayList<>();
        this.loadData();
    }

    public Bank(String bankName, Date time) {
        this.bankName = bankName;
        this.time = time;
        this.usersList = new ArrayList<>();
        this.loadData();
    }

    public Bank(Bank bank){
        this.bankName = bank.bankName;
        this.usersList = new ArrayList<>(bank.usersList);
        this.time = bank.time;
        this.loadData();
    }

    public String getTime(){
        return this.time.getTime();
    }

    public boolean registerUser(String username, String password){
        for(User user : usersList){
            if(Objects.equals(user.username, username)){return false;}
        }
        usersList.add(new User(username, password));
        return true;
    }

    public User loginUser(String username, String password){
        for (User user : usersList) {
            if (Objects.equals(user.username, username) && Objects.equals(user.password, password)) {
                return user;
            }
        }
        return null;
    }

    public boolean deposit(User user, double amount) {
        if(!usersList.contains(user)){return false;}
        if(user.getWalletMoney() - amount < 0){return false;}
        user.bankBalance += amount;
        user.walletMoney -= amount;
        String transaction = time.getTime() + " : " + "Deposit of " + amount + "$ to the bank balance";
        user.transactionsHistory.add(transaction);
        return true;
    }

    public boolean withdraw(User user, double amount) {
        if(!usersList.contains(user)){return false;}
        if(user.bankBalance - amount < 0){return false;}
        user.bankBalance -= amount;
        user.walletMoney += amount;
        String transaction = time.getTime() + " : " + "Withdrawal of " + amount + "$ from " + "the bank balance";
        user.transactionsHistory.add(transaction);
        return true;
    }

    public void advanceTime(int day, int month, int year) {
        Date previousTime = new Date(this.time);
        this.time.advancement(day, month, year);
        int bonus = this.time.getDifferenceMonths(previousTime) * 100;
        for (User user : usersList) {
            user.bankBalance += bonus;
            user.transactionsHistory.add(time.getTime() + " : " + "Gained " + bonus + "$ from the bank bonus");
        }
    }

    public boolean invest(User user, double amount, String duration, String risk) {
        if(!usersList.contains(user)){return false;}
        if (user.bankBalance <= 0 || amount <= 0 || user.bankBalance - amount < 0) {return false;}

        String transaction = time.getTime() + " : " + "Payment of " + amount + "$ for a " + duration + " " + risk + "-risk Investment";
        user.transactionsHistory.add(transaction);

        double profitMultiplier = 1;
        double riskMultiplier = 0;

        switch (duration) {
            case "short":
                advanceTime(0, 1, 0);
                profitMultiplier = 1.05;
                break;
            case "medium":
                advanceTime(0, 6, 0);
                profitMultiplier = 1.25;
                break;
            case "long":
                advanceTime(0, 0, 1);
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

        if (randomValue > 4500) {
            finalInvestment = -finalInvestment;
        }

        user.bankBalance += finalInvestment;
        if(finalInvestment > 0){
            transaction = time.getTime() + " : " + "Earned " + finalInvestment + "$ from the " + duration + " " + risk + "-risk investment";
        }else{
            transaction = time.getTime() + " : " + "Lost " + -finalInvestment + "$ from the " + duration + " " + risk + "-risk investment";
        }

        user.transactionsHistory.add(transaction);
        return true;
    }


    public void saveData(){
        String directoryPath = "src/main/resources";
        String filename = "bank_data.txt";
        File file = new File(directoryPath + File.separator + filename);

        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Error during the file creation");
            return;
        }

        String timeData = time.getTime();

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath(), false))){
            bw.write(bankName + ";" + timeData + ";" + "\n");
            for (User user : usersList) {
                bw.write(user.username + ";" + user.password + ";" + user.bankBalance + ";" + user.walletMoney + ";");
                for(String transaction : user.transactionsHistory){
                    bw.write(transaction + ";");
                }
                bw.write("\n");
            }
        }catch(Exception e){
            System.out.print("Error during the file writing");
        }
    }

    public void loadData(){
        String filePath = "src/main/resources/bank_data.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();

            if(line != null){
                bankName = line.substring(0,line.indexOf(";"));
                line = line.substring(line.indexOf(";")+1,line.length()-1);

                time.setDay(Integer.parseInt(line.substring(0,line.indexOf("/"))));
                line = line.substring(line.indexOf("/")+1);

                time.setMonth(Integer.parseInt(line.substring(0,line.indexOf("/"))));
                line = line.substring(line.indexOf("/")+1);

                time.setYear(Integer.parseInt(line));
            }

            String temp;
            double temp1;

            while ((line = br.readLine()) != null) {
                User tempUser = new User("","");
                temp = line.substring(0,line.indexOf(";"));
                tempUser.username = temp;
                line = line.substring(line.indexOf(";")+1);

                temp = line.substring(0,line.indexOf(";"));
                tempUser.password = temp;
                line = line.substring(line.indexOf(";")+1);

                temp1 = Double.parseDouble(line.substring(0,line.indexOf(";")));
                tempUser.bankBalance = temp1;
                line = line.substring(line.indexOf(";")+1);

                temp1 = Double.parseDouble(line.substring(0,line.indexOf(";")));
                tempUser.walletMoney = temp1;


                System.out.println(line + "\n");

                while((line.indexOf(";") + 1) != line.length()){

                    line = line.substring(line.indexOf(";")+1);

                    temp = line.substring(0,line.indexOf(";"));
                    tempUser.transactionsHistory.add(temp);
                }

                usersList.add(new User(tempUser));
            }

        } catch (IOException e) {
            System.out.print("Error during the file reading");
        }
        eraseData();
    }

    public void eraseData(){
        String directoryPath = "src/main/resources";
        String filename = "bank_data.txt";
        File file = new File(directoryPath + File.separator + filename);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File eliminato con successo.");
            } else {
                System.out.println("Errore durante l'eliminazione del file.");
            }
        } else {
            System.out.println("Il file non esiste, nessuna eliminazione necessaria.");
        }
    }

}
