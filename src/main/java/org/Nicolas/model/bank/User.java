package org.Nicolas.model.bank;

import java.util.ArrayList;

public class User {

    String username;
    String password;
    double bankBalance;
    double walletMoney;
    ArrayList<String> transactionsHistory;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.walletMoney = 100.0;
        transactionsHistory = new ArrayList<>();
    }

    public User(User user){
        this.username = user.username;
        this.password = user.password;
        this.bankBalance = user.bankBalance;
        this.walletMoney = user.walletMoney;
        transactionsHistory = new ArrayList<>();
        if(user.transactionsHistory != null){
            this.transactionsHistory.addAll(user.transactionsHistory);
        }
    }

    public void printHistory(){
        for(String trans : transactionsHistory){
            System.out.println(trans + "\n");
        }
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public double getBankBalance(){
        return bankBalance;
    }

    public double getWalletMoney(){
        return walletMoney;
    }

    public void setWalletMoney(double walletMoney){
        this.walletMoney = walletMoney;
    }

    public void setBankBalance(double bankBalance){
        this.bankBalance = bankBalance;
    }

}
