package org.Nicolas.model;

public class User {
    private double walletMoney;

    public User(){
        this.walletMoney = 100.0;
    }

    public void addToWallet(double amount){
        this.walletMoney += amount;
    }

    public double getWalletMoney(){
        return this.walletMoney;
    }
}
