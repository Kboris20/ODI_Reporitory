package ch.hearc.ig.odi.serie3.business;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author boris.klett
 *
 */
public class Account {

    Customer customer;
    private String number;
    private String name;
    private double balance = 0;
    private double rate = 0.001;

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     *
     * @param number
     * @param name
     * @param rate
     * @param customer
     */
    public Account(String number, String name, double rate, Customer customer) {
        this.number = number;
        this.name = name;
        this.rate = rate;
        this.customer = customer;
    }

    private static void OutOBoundException(double amount, String message) {
        if (amount < 1) {

            try {
                throw new Exception(message);
            } catch (Exception ex) {
                //Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
    }

    private static void BalanceOutOfSolde(double amount, double balance, String message) {
        if (balance - amount < 0) {

            try {
                throw new Exception(message);
            } catch (Exception ex) {
                //Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
            }
        }
    }

    /**
     *
     * @param amount
     *
     *
     */
    public void credit(double amount) {
        if (amount > 0) {
            this.balance = this.balance + amount;
        }

    }

    /**
     *
     * @param amount
     */
    public void debit(double amount) {

        this.balance = this.balance - amount;
    }

    /**
     *
     * @param amount
     * @param source
     * @param target
     */
    public static void transfert(double amount, Account source, Account target) {
        Account.OutOBoundException(amount, "Le montant à transférer doit être suppérieur à 0");
        Account.BalanceOutOfSolde(amount, source.getBalance(), "!!!! Opération réfusée.. votre solde est de: " + source.getBalance() + " vous ne pouvez pas transférer: "+amount+" !!!!");
        if (source.getBalance() - amount > -1) {
            source.debit(amount);
            target.credit(amount);
        }
    }

}
