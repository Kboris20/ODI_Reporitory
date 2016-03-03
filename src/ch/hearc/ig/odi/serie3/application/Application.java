package ch.hearc.ig.odi.serie3.application;

import ch.hearc.ig.odi.serie3.business.Account;
import ch.hearc.ig.odi.serie3.business.Customer;
import java.util.HashMap;

/**
 *
 * @author boris.klett
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public void run() throws Exception {
        HashMap <String, Customer> customers = new HashMap <String, Customer>();
        
        Customer c = new Customer(1, "Boris", "Klett");
        c.addAccount("1", "compteBoris", 0.01);
        
        customers.put(c.getFirstName(), c);
        
        c = new Customer(2, "Wilvie", "Klett");
        c.addAccount("2", "compteWilvie", 0.01);
        
        customers.put(c.getFirstName(), c);
        
        c= customers.get("Boris");
        Account a = c.getAccountByNumber("1");
        System.out.println("Solde du compte: "+a.getName()+"\n"+a.getBalance());        
        c= customers.get("Wilvie");
        Account a1 = c.getAccountByNumber("2");
        System.out.println("Solde du compte: "+a1.getName()+"\n"+a1.getBalance()+"\n\n");
        
        System.out.println("Comptes crédités");
        a.credit(10);
        a1.credit(20);
        System.out.println("Solde du compte: "+a.getName()+"\n"+a.getBalance());
        System.out.println("Solde du compte: "+a1.getName()+"\n"+a1.getBalance()+"\n\n");
        
        System.out.println("Transfère effectué.. \n\n");
        Account.transfert(5.5, a, a1);
        
        System.out.println("après le transfère: \n");
        System.out.println("Solde du compte: "+a.getName()+"\n"+a.getBalance());
        System.out.println("Solde du compte: "+a1.getName()+"\n"+a1.getBalance()+"\n\n");
        
        System.out.println("Transfère effectué.. \n\n");
        Account.transfert(10, a1, a);
        
        System.out.println("après le transfère: \n");
        System.out.println("Solde du compte: "+a.getName()+"\n"+a.getBalance());
        System.out.println("Solde du compte: "+a1.getName()+"\n"+a1.getBalance()+"\n\n");
        
        System.out.println("Transfère effectué.. \n\n");
        Account.transfert(30, a1, a);
        
        System.out.println("après le transfère: \n");
        System.out.println("Solde du compte: "+a.getName()+"\n"+a.getBalance());
        System.out.println("Solde du compte: "+a1.getName()+"\n"+a1.getBalance()+"\n\n");
     
    }

    public static void main(String[] args) throws Exception {
        Application a = new Application();
        a.run();
    }

}
