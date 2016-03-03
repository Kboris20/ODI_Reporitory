package ch.hearc.ig.odi.serie3.business;

import java.util.Collection;
import java.util.HashMap;



public class Customer {

	HashMap<String, Account> accounts = new HashMap<String, Account>();
	private String firstName;
	private String lastName;
	private int number;

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * 
	 * @param number
	 * @param firstName
	 * @param lastName
	 */
	public Customer(int number, String firstName, String lastName) {
		this.number = number;
                this.firstName = firstName;
                this.lastName = lastName;
	}

	/**
	 * 
	 * @param number
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
            Customer c = new Customer(this.number, this.firstName, this.lastName);
            Account a = new Account(number, name, rate, c);
            this.accounts.put(number, a);
	}

}