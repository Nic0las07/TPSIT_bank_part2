package org.Nicolas.app;

import org.Nicolas.model.bank.Bank;
import org.Nicolas.model.bank.BankGui;
import org.Nicolas.model.date.Date;


public class Main {
    public static void main(String[] args) {
        Date mainDate = new Date(1,1,2000);
        Bank mainBank = new Bank("International Bank", mainDate);
        BankGui bankGui = new BankGui(mainBank);
        bankGui.mainGui();

        Runtime.getRuntime().addShutdownHook(new Thread(mainBank::saveData));
    }
}