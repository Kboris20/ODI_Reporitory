package ch.hearc.ig.odi.serie3.business;

import java.util.HashMap;

public class Customer {

    private String firstName;
    private String lastName;
    private int number;
    HashMap<String, Account> accounts = new HashMap<>();

    /**
     *
     * @param number
     * @param firstName
     * @param lastName
     */
    public Customer(Integer number, String firstName, String lastName) {
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getNumber() {
        return this.number;
    }

    /**
     *
     * @param number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFirstName() {
        return this.firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @param number
     * @return Account
     */
    public Account getAccountByNumber(String number) {
        return accounts.get(number);
    }

    /**
     *
     * @param number
     * @param name
     * @param rate
     */
    public void addAccount(String number, String name, double rate) {
        if (this.accounts.get(number) == null) {
            this.accounts.put(number, new Account(number, name, rate, this));
        }
    }

    public void addAccount(Account account) {
        this.accounts.put(account.getNumber(), account);
    }

    public HashMap<String, Account> getAccounts() {
        return this.accounts;
    }

    @Override
    public String toString() {
        return this.number + " - " + this.firstName + " " + this.lastName;
    }

}
