package ch.hearc.ig.odi.serie3.business;

import java.util.HashMap;

/**
 *
 * @author Jérémy
 */
public class Bank {
    private int number;
    private String name;
    private HashMap<String, Account> accounts;
    private HashMap<Integer, Customer> customers;
    
    public Bank(int number, String name) {
        this.number = number;
        this.name = name;
    }
    
    public Account getAccountByNumber(String number) {
       return this.accounts.get(number);
    }
    
    public Customer getCustomerByNumber(int number) {
        return this.customers.get(number);
    }
    
    public void addCustomer(int number, String firstName, String lastName) {
        this.customers.put(number, new Customer(number, firstName, lastName));
    }
    
    public void addAccount(String number, String name, double rate, Customer customer) {
        this.accounts.put(number, new Account(number, name, rate, customer));
        this.customers.get(customer.getNumber()).addAcount(number, name, rate);
    }
}
