package ch.hearc.ig.odi.serie3.application;

import ch.hearc.ig.odi.serie3.business.Account;
import ch.hearc.ig.odi.serie3.business.Customer;

/**
 *
 * @author boris.klett
 */
public class Application {


    public static void main(String[] args) throws Exception {
         // Création de 2 clients test
        Customer c1 = new Customer(1, "Jérémy", "Bärfus");
        c1.addAcount("10-195966-0", "Compte courant", 0.01);
        c1.addAcount("62-524812-1", "Compte épargne", 0.25);

        Customer c2 = new Customer(2, "Michel", "Comptable");
        c2.addAcount("10-352790-1", "Impots", 0.01);

        // Test des crédits
        c1.getAccountByNumber("10-195966-0").credit(800);
        c1.getAccountByNumber("62-524812-1").credit(800000);
        c2.getAccountByNumber("10-352790-1").credit(3000);
        c2.getAccountByNumber("10-352790-1").credit(-100); // Erreur : montant négatif

        // Test des débits
        c1.getAccountByNumber("10-195966-0").debit(1000); // Erreur : solde 800 - débit 1000
        c1.getAccountByNumber("10-195966-0").debit(-100); // Erreur : montant négatif
        c1.getAccountByNumber("62-524812-1").debit(5000);

        // Test des transferts
        Account.transfer(1000, c1.getAccountByNumber("10-195966-0"), c2.getAccountByNumber("10-352790-1")); // Erreur : solde 800 - transfert 1000
        Account.transfer(-100, c1.getAccountByNumber("10-195966-0"), c2.getAccountByNumber("10-352790-1")); // Erreur : montant négatif
        Account.transfer(1000, c2.getAccountByNumber("10-352790-1"), c1.getAccountByNumber("10-195966-0"));

        // Affichage des clients et leurs comptes
    }

}
