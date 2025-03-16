package org.Nicolas.model.bank;

import org.Nicolas.model.date.Date;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Bank {

    private String bankName;
    private final ArrayList<User> usersList;
    private final Date time;

    public Bank(String bankName, Date time) {
        this.bankName = bankName;
        this.time = time;
        this.usersList = new ArrayList<>();
        this.loadData();
    }

    public Bank(String bankName) {
        this(bankName, new Date());
    }

    public Bank(Bank bank){
        this.bankName = bank.bankName;
        this.usersList = new ArrayList<>(bank.usersList);
        this.time = bank.time;
    }

    public String getTime(){
        return this.time.getTime();
    }

    public boolean registerUser(String username, String password){
        for(User user : usersList){
            if(Objects.equals(user.username, username)){return false;}
        }
        User user = new User(username, password);
        user.graphData.add("1/1/2000;0.0;0.0");
        usersList.add(new User(user));
        saveData();
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
        if(user.getWalletMoney() - amount < 0 || amount < 0){return false;}
        user.bankBalance += amount;
        user.walletMoney -= amount;
        String transaction = time.getTime() + " : " + "Deposit of " + amount + "$ to the bank balance";
        user.transactionsHistory.add(transaction);
        String transactionData = time.getTime() + ";" + user.bankBalance + ";" + user.walletMoney;
        user.graphData.add(transactionData);
        saveData();
        return true;
    }

    public boolean withdraw(User user, double amount) {
        if(!usersList.contains(user)){return false;}
        if(user.bankBalance - amount < 0 || amount < 0){return false;}
        user.bankBalance -= amount;
        user.walletMoney += amount;
        String transaction = time.getTime() + " : " + "Withdrawal of " + amount + "$ from " + "the bank balance";
        user.transactionsHistory.add(transaction);
        String transactionData = time.getTime() + ";" + user.bankBalance + ";" + user.walletMoney;
        user.graphData.add(transactionData);
        saveData();
        return true;
    }

    public void advanceTime(int day, int month, int year) {
        Date previousTime = new Date(this.time);
        this.time.advancement(day, month, year);
        int bonus = this.time.getDifferenceMonths(previousTime) * 100;
        for (User user : usersList) {
            user.bankBalance += bonus;
            user.transactionsHistory.add(time.getTime() + " : " + "Gained " + bonus + "$ from the bank bonus");

            String transactionData = time.getTime() + ";" + user.bankBalance + ";" + user.walletMoney;
            user.graphData.add(transactionData);
        }

        saveData();
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

        String transactionData = time.getTime() + ";" + user.bankBalance + ";" + user.walletMoney;
        user.graphData.add(transactionData);

        if(finalInvestment > 0){
            transaction = time.getTime() + " : " + "Earned " + finalInvestment + "$ from the " + duration + " " + risk + "-risk investment";
        }else{
            transaction = time.getTime() + " : " + "Lost " + -finalInvestment + "$ from the " + duration + " " + risk + "-risk investment";
        }

        user.transactionsHistory.add(transaction);
        saveData();
        return true;
    }

    private boolean createFile(File file){
        try {
            if (file.createNewFile()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void saveData(){
        String directoryPath = "src/main/resources";
        String filename = "bank_data.txt";
        File file = new File(directoryPath + File.separator + filename);

        if(!createFile(file)){return;}

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


        directoryPath = "src/main/resources";
        filename = "graphData.txt";
        file = new File(directoryPath + File.separator + filename);

        if(!createFile(file)){return;}

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath(), false))){
            for (User user : usersList) {
                for(String transaction : user.graphData){
                    bw.write(transaction.substring(0,transaction.indexOf(";")));
                    bw.write(",");
                    transaction = transaction.substring(transaction.indexOf(";")+1);
                    bw.write(transaction.substring(0,transaction.indexOf(";")));
                    bw.write(",");
                    bw.write(transaction.substring(transaction.indexOf(";")+1));
                    bw.write(";");
                }
                bw.write("\n");
            }
        }catch(Exception e){
            System.out.print("Error during the file writing");
        }
    }

    public void loadData() {
        loadBankData("src/main/resources/bank_data.txt");
        loadGraphData("src/main/resources/graphData.txt");
    }

    private void loadBankData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                String[] parts = line.split(";");
                bankName = parts[0];
                String[] dateParts = parts[1].split("/");
                time.setDay(Integer.parseInt(dateParts[0]));
                time.setMonth(Integer.parseInt(dateParts[1]));
                time.setYear(Integer.parseInt(dateParts[2]));
            }

            while ((line = br.readLine()) != null) {
                String[] userParts = line.split(";");
                User tempUser = new User(userParts[0], userParts[1]);
                tempUser.bankBalance = Double.parseDouble(userParts[2]);
                tempUser.walletMoney = Double.parseDouble(userParts[3]);

                for (int i = 4; i < userParts.length; i++) {
                    tempUser.transactionsHistory.add(userParts[i]);
                }
                usersList.add(new User(tempUser));
            }
        } catch (IOException e) {
            System.out.print("Error during the file reading");
        }
    }

    private void loadGraphData(String filePath) {

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                for(String transaction : parts){
                    String[] userParts = transaction.split(",");
                    usersList.get(index).graphData.add(userParts[0] + ";" + userParts[1] + ";" + userParts[2]);
                }
                index++;
            }
        } catch (IOException e) {
            System.out.print("Error during the file reading");
        }
    }

    public void createGraphDataFile(User user){
        this.eraseGraphDataFile();
        String directoryPath = "src/main/resources/graph";
        String filename = "data.csv";
        File file = new File(directoryPath + File.separator + filename);

        if(!createFile(file)){return;}

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath(), false))){
            for(String transaction : user.graphData){
                bw.write(transaction);
                bw.write("\n");
            }
        }catch(Exception e){
            System.out.print("Error during the file writing");
        }
    }

    public void createGraphic(User user) {
        if(user.graphData.isEmpty()){return;}
        this.createGraphDataFile(user);

        try {
            String pythonFilePath = new File("src/main/resources/graph/main.py").getAbsolutePath();

            ProcessBuilder pb = new ProcessBuilder("python", pythonFilePath);
            pb.directory(new File("src/main/resources/graph"));

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[Python] " + line);
            }
            while ((line = errorReader.readLine()) != null) {
                System.err.println("[Python ERROR] " + line);
            }

            process.waitFor();

            File graph = new File("src/main/resources/graph/graph.png").getAbsoluteFile();
            if (graph.exists()) {
                Desktop.getDesktop().open(graph);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.eraseGraphDataFile();
    }

    private void eraseFile(String directoryPath, String filename){
        File file = new File(directoryPath + File.separator + filename);
        if (file.exists()) {
            System.out.println(file.delete());
        }
    }

    public void eraseData(){
        eraseFile("src/main/resources", "bank_data.txt");
        eraseFile("src/main/resources", "graphData.txt");
    }

    void eraseGraphDataFile(){
        eraseFile("src/main/resources/graph", "data.csv");
    }
}
