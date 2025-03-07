package org.Nicolas.app;
import org.Nicolas.model.bank.Bank;
import org.Nicolas.model.bank.User;
import org.Nicolas.model.date.Date;

public class Main {
    public static void main(String[] args) {
        new Bank("San Paolo");
        Bank mainBank;
        Date tempDate = new Date();
        mainBank = new Bank("San Paolo", tempDate);
        mainBank.registerUser("Paolo1", "S23032007n");
        mainBank.registerUser("Paolo2", "S23032007n");
        mainBank.registerUser("Paolo3", "S23032007n");
        mainBank.registerUser("Paolo4", "S23032007n");
        mainBank.registerUser("Paolo5", "S23032007n");
        mainBank.registerUser("Paolo6", "S23032007n");
        mainBank.registerUser("Paolo7", "S23032007n");
        User currentUser = mainBank.loginUser("Paolo3", "S23032007n");
        mainBank.deposit(currentUser, 100.0);
        mainBank.withdraw(currentUser, 100.0);
        mainBank.deposit(currentUser, 100.0);
        mainBank.invest(currentUser, 100, "long", "low");
        currentUser = mainBank.loginUser("Paolo1", "S23032007n");
        mainBank.deposit(currentUser, 100.0);
        mainBank.withdraw(currentUser, 100.0);
        mainBank.deposit(currentUser, 100.0);
        mainBank.invest(currentUser, 100, "long", "low");
        currentUser = mainBank.loginUser("Paolo7", "S23032007n");
        mainBank.deposit(currentUser, 100.0);
        mainBank.withdraw(currentUser, 100.0);
        mainBank.deposit(currentUser, 100.0);
        mainBank.invest(currentUser, 100, "long", "low");
        currentUser = mainBank.loginUser("Paolo5", "S23032007n");
        mainBank.deposit(currentUser, 100.0);
        mainBank.withdraw(currentUser, 100.0);
        mainBank.deposit(currentUser, 100.0);
        mainBank.invest(currentUser, 100, "long", "low");
        currentUser.printHistory();
        mainBank.saveData();
        mainBank.eraseUsers();
        mainBank.loadData();
    }
}
