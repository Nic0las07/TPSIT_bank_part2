package org.Nicolas;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Bank operations.

 */
public class AppTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTest(new AppTest("testDate"));
        suite.addTest(new AppTest("testInvestment"));
        suite.addTest(new AppTest("testWithdrawDeposit"));

        return suite;
    }

    /**
     * Rigorous Test for Date advancement and time functionality
     */
    public void testDate() {
        /*
        Date date = new Date(23, 11, 2025);

        date.advancement(365, 0, 0);
        assertEquals("Date should advance by 365 days", "23/11/2026", date.getTime());

        date.advancement(5, 2, 0);
        assertEquals("Date should advance to 5th February next year", "28/1/2027", date.getTime());

        date.advancement(33, 0, 1);
        assertEquals("Date should advance correctly", "2/3/2028", date.getTime());

        date.advancement(45, 0, 0);
        assertEquals("Date should advance by 45 days", "16/4/2028", date.getTime());

        date.advancement(0, 20, 0);
        assertEquals("Date should advance by 20 months", "16/12/2029", date.getTime());

        date.advancement(152, 2, 0);
        assertEquals("Date should advance by 152 days and 2 months", "17/7/2030", date.getTime());

        */
    }


    /**
     * Rigorous Test for Investment operations
     */
    public void testInvestment() {
        /*
        Bank bank = new Bank(1000, new User("Paolo"), new Date(23, 11));

        bank.invest(100, "short", "low");
        bank.invest(50, "medium", "low");
        bank.invest(50, "long", "low");
        bank.invest(120, "short", "high");
        bank.invest(50, "medium", "high");
        bank.invest(7000, "long", "high");

        */
    }

    /**
     * Rigorous Test for Withdraw and Deposit functionality
     */
    public void testWithdrawDeposit() {
        /*
        Bank bank = new Bank(0, new User("Paolo"), new Date(23, 11));

        assertEquals("Initial money should be 0", 0, bank.getMoney(), 0.01);

        bank.withdraw(20);
        assertEquals("Money should still be 0 after withdrawing more than available", 0, bank.getMoney(), 0.01);

        bank.deposit(50.99);
        assertEquals("Money after depositing 50.99 should be 50.99", 50.99, bank.getMoney(), 0.01);

        bank.withdraw(20.2);
        assertEquals("Money after withdrawing 20.2 should be 30.79", 30.79, bank.getMoney(), 0.01);

        bank.advanceTime(0, 3, 0);
        assertEquals("Balance should not change after time advancement", 30.79, bank.getMoney(), 0.01);

        bank.advanceTime(0, 0, 1);
        assertEquals("Balance should still be the same after further time advancement", 30.79, bank.getMoney(), 0.01);

         */
    }

    /**
     * Rigorous Test :-), always true
     */
    public void testApp() {
        assertTrue("This test is always true", true);
    }
}