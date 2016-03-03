package ch.hearc.ig.odi.serie3.business;

import java.util.HashMap;

/**
 *
 * @author Jérémy
 */
public class Bank {

    private int number;
    private String name;
    private HashMap<String, Account> accounts = new HashMap<>();
    private HashMap<Integer, Customer> customers = new HashMap<>();

    public Bank(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public HashMap<Integer, Customer> getCustomers() {
        return this.customers;
    }

    public Account getAccountByNumber(String number) {
        return this.accounts.get(number);
    }

    public Customer getCustomerByNumber(int number) {
        return this.customers.get(number);
    }

    public void addCustomer(int number, String firstName, String lastName) {
        if (this.customers.get(number) == null) {
            this.customers.put(number, new Customer(number, firstName, lastName));
        }
    }

    public void addAccount(String number, String name, double rate, Customer customer) {
        if (this.accounts.get(number) == null) {
            Account a = new Account(number, name, rate, customer);
            this.accounts.put(number, a);
            this.customers.get(customer.getNumber()).addAccount(a);
        }
    }
}
