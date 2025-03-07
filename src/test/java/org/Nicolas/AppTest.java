package org.Nicolas;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.Nicolas.model.bank.Bank;
import org.Nicolas.model.bank.User;
import org.Nicolas.model.date.Date;
import java.io.File;
import java.io.IOException;

public class AppTest extends TestCase {
    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new AppTest("testRegisterUser"));
        suite.addTest(new AppTest("testLoginUser"));
        suite.addTest(new AppTest("testDeposit"));
        suite.addTest(new AppTest("testWithdraw"));
        suite.addTest(new AppTest("testAdvanceTime"));
        suite.addTest(new AppTest("testInvest"));
        suite.addTest(new AppTest("testDateAdvancement"));
        suite.addTest(new AppTest("testDateDifference"));
        suite.addTest(new AppTest("testSaveAndLoadData"));
        return suite;
    }

    public void testRegisterUser() {
        Bank bank = new Bank("Test Bank", new Date());
        assertTrue(bank.registerUser("newUser1", "pass123"));
        assertTrue(bank.registerUser("newUser2", "pass456"));
        assertFalse(bank.registerUser("newUser1", "password123"));
    }

    public void testLoginUser() {
        Bank bank = new Bank("Test Bank", new Date());
        bank.registerUser("testUser", "password123");
        assertNotNull(bank.loginUser("testUser", "password123"));
        assertNull(bank.loginUser("wrongUser", "password123"));
    }

    public void testDeposit() {
        Bank bank = new Bank("Test Bank", new Date());
        bank.registerUser("testUser", "password123");
        User currentUser = bank.loginUser("testUser", "password123");
        currentUser.setWalletMoney(500.0);
        assertTrue(bank.deposit(currentUser, 200.0));
        assertEquals(200.0, currentUser.getBankBalance());
        assertFalse(bank.deposit(currentUser, 300.01));
        assertEquals(200.0, currentUser.getBankBalance());
    }

    public void testWithdraw() {
        Bank bank = new Bank("Test Bank", new Date());
        bank.registerUser("testUser", "password123");
        User currentUser = bank.loginUser("testUser", "password123");
        currentUser.setBankBalance(500.0);
        assertTrue(bank.withdraw(currentUser, 200.0));
        assertEquals(300.0, currentUser.getBankBalance());
        assertFalse(bank.withdraw(currentUser, 300.01));
        assertEquals(300.0, currentUser.getBankBalance());
    }

    public void testAdvanceTime() {
        Bank bank = new Bank("Test Bank", new Date());
        bank.registerUser("testUser", "password123");
        User currentUser = bank.loginUser("testUser", "password123");
        currentUser.setBankBalance(1000.0);
        bank.advanceTime(0, 2, 0);
        assertEquals(1200.0, currentUser.getBankBalance());
        currentUser.setBankBalance(0.0);
        bank.advanceTime(4356, 25, 21);
        assertEquals(42000.0, currentUser.getBankBalance());
    }

    public void testInvest() {
        Bank bank = new Bank("Test Bank", new Date());
        bank.registerUser("testUser", "password123");
        User currentUser = bank.loginUser("testUser", "password123");
        currentUser.setBankBalance(1000);
        assertTrue(bank.invest(currentUser, 500, "short", "low"));
        currentUser.setBankBalance(-100.0);
        assertFalse(bank.invest(currentUser, 500, "short", "low"));
        currentUser.setBankBalance(100.0);
        assertFalse(bank.invest(currentUser, 100.01, "short", "low"));
    }

    public void testDateAdvancement() {
        Date date = new Date(29, 2, 2024);
        date.advancement(3452, 934, 678);
        assertEquals("11/6/2789", date.getTime());
        date.advancement(95736, 4692, 1);
        assertEquals("24/7/3443", date.getTime());
        date.advancement(34, 8, 3);
        assertEquals("27/4/3447", date.getTime());
    }

    public void testDateDifference() {
        Date date = new Date(23, 11, 2025);
        Date pastDate = new Date(1, 1, 2023);
        assertEquals(34, date.getDifferenceMonths(pastDate));
    }

    public void testSaveAndLoadData() throws IOException {
        Bank bank = new Bank("Test Bank", new Date());
        bank.registerUser("testUser", "password123");
        bank.saveData();

        String filePath = "src/main/resources/bank_data.txt";
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        Bank newBank = new Bank("Test Bank", new Date());
        newBank.loadData();
        assertNotNull(newBank.loginUser("testUser", "password123"));
    }

    public void testApp() {
        assertTrue(true);
    }
}
