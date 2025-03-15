package org.Nicolas;

import org.Nicolas.model.bank.Bank;
import org.Nicolas.model.bank.User;
import org.Nicolas.model.date.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    private Bank bank;

    @BeforeEach
    void setUp() {
        Date date = new Date();
        bank = new Bank("Test Bank", date);
    }

    @Test
    void testRegisterUser() {
        bank.eraseData();
        assertTrue(bank.registerUser("newUser1", "pass123"));
        assertTrue(bank.registerUser("newUser2", "pass456"));
        assertFalse(bank.registerUser("newUser1", "password123"));
        bank.eraseData();
    }

    @Test
    void testLoginUser() {
        bank.registerUser("testUser", "password123");
        assertNotNull(bank.loginUser("testUser", "password123"));
        assertNull(bank.loginUser("wrongUser", "password123"));
    }

    @Test
    void testDeposit() {
        bank.eraseData();
        bank.registerUser("testUser", "password123");
        User currentUser = bank.loginUser("testUser", "password123");
        currentUser.setWalletMoney(500.0);
        assertTrue(bank.deposit(currentUser, 200.0));
        assertEquals(200.0, currentUser.getBankBalance());
        assertFalse(bank.deposit(currentUser, 300.01));
        assertEquals(200.0, currentUser.getBankBalance());
        assertFalse(bank.deposit(currentUser, -5));
        bank.eraseData();
    }

    @Test
    void testWithdraw() {
        bank.eraseData();
        bank.registerUser("testUser", "password123");
        User currentUser = bank.loginUser("testUser", "password123");
        currentUser.setBankBalance(500.0);
        assertTrue(bank.withdraw(currentUser, 200.0));
        assertEquals(300.0, currentUser.getBankBalance());
        assertFalse(bank.withdraw(currentUser, 300.01));
        assertEquals(300.0, currentUser.getBankBalance());
        assertFalse(bank.withdraw(currentUser, -5));
        bank.eraseData();
    }

    @Test
    void testAdvanceTime() {
        bank.eraseData();
        bank.registerUser("testUser", "password123");
        User currentUser = bank.loginUser("testUser", "password123");
        currentUser.setBankBalance(1000.0);
        bank.advanceTime(0, 2, 0);
        assertEquals(1200.0, currentUser.getBankBalance());
        currentUser.setBankBalance(0.0);
        bank.advanceTime(4356, 25, 21);
        assertEquals(42000.0, currentUser.getBankBalance());
        bank.eraseData();
    }

    @Test
    void testInvest() {
        bank.eraseData();
        bank.registerUser("testUser", "password123");
        User currentUser = bank.loginUser("testUser", "password123");
        currentUser.setBankBalance(1000);
        assertTrue(bank.invest(currentUser, 500, "short", "low"));
        currentUser.setBankBalance(-100.0);
        assertFalse(bank.invest(currentUser, 500, "short", "low"));
        currentUser.setBankBalance(100.0);
        assertFalse(bank.invest(currentUser, 100.01, "short", "low"));
        assertFalse(bank.invest(currentUser, -5, "short", "low"));
        bank.eraseData();
    }

    @Test
    void testDateAdvancement() {
        Date date = new Date(29, 2, 2024);
        date.advancement(3452, 934, 678);
        assertEquals("11/6/2789", date.getTime());
        date.advancement(95736, 4692, 1);
        assertEquals("24/7/3443", date.getTime());
        date.advancement(34, 8, 3);
        assertEquals("27/4/3447", date.getTime());
    }

    @Test
    void testDateDifference() {
        Date date = new Date(23, 11, 2025);
        Date pastDate = new Date(1, 1, 2023);
        assertEquals(34, date.getDifferenceMonths(pastDate));
    }

    @Test
    void testSaveAndLoadData() throws IOException {
        bank.eraseData();
        bank.registerUser("testUser", "password123");
        bank.saveData();

        String filePath = "src/main/resources/bank_data.txt";
        File file = new File(filePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        Bank newBank = new Bank("Test Bank", new Date());
        newBank.loadData();
        assertNotNull(newBank.loginUser("testUser", "password123"));
        bank.eraseData();
    }
}
