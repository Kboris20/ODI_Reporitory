package ch.hearc.ig.odi.serie3.application;

import ch.hearc.ig.odi.serie3.business.Account;
import ch.hearc.ig.odi.serie3.business.Bank;
import ch.hearc.ig.odi.serie3.business.Customer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;

/**
 *
 * @author boris.klett
 */
public class Application {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static Bank bank = new Bank(1, "UBS");

    public static void main(String[] args) throws Exception {

        bank.addCustomer(10, "Barfuss", "Jeremy");
        bank.addAccount("1", "Courant", 0.1, bank.getCustomerByNumber(10));
        bank.addAccount("2", "Epargne", 1.25, bank.getCustomerByNumber(10));

        int choice = 0;

        do {
            getMenu();
            choice = Integer.parseInt(READER.readLine());

            switch (choice) {
                case 1:
                    creerClient();
                    break;
                case 2:
                    ajouterCompte();
                    break;
                case 3:
                    listerClients();
                    break;
                case 4:
                    afficherClient();
                    break;
                case 5:
                    crediterCompte();
                    break;
                case 6:
                    debiterCompte();
                    break;
                case 7:
                    transfert();
                    break;
            }
        } while (choice != 0);

    }

    public static void getMenu() {
        System.out.println("\n\n=== MENU PRINCIPAL ===");
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1. Créer un client");
        System.out.println("2. Ajouter un compte");
        System.out.println("3. Lister les clients");
        System.out.println("4. Afficher un client et ses comptes");
        System.out.println("5. Créditer un compte");
        System.out.println("6. Débiter un compte");
        System.out.println("7. Transférer un montant");
        System.out.println("0. Quitter");
        System.out.print(">> ");
    }

    public static void creerClient() throws IOException {
        System.out.println("\n === CREER UN CLIENT ===");
        System.out.print("Numéro du client : ");
        int cNumber = Integer.parseInt(READER.readLine());
        System.out.print("Prénom du client : ");
        String cFirstName = READER.readLine();
        System.out.print("Nom du client : ");
        String cLastName = READER.readLine();
        bank.addCustomer(cNumber, cFirstName, cLastName);
        System.out.println("Client ajouté avec succès");
    }

    public static void ajouterCompte() throws IOException {
        System.out.println("\n === AJOUTER UN COMPTE ===");
        System.out.print("Numéro du client : ");
        int cNumber = Integer.parseInt(READER.readLine());
        System.out.print("Numéro du compte : ");
        String aNumber = READER.readLine();
        System.out.print("Nom du compte : ");
        String aName = READER.readLine();
        System.out.print("Taux du compte : ");
        double aRate = Double.parseDouble(READER.readLine());
        bank.addAccount(aNumber, aName, aRate, bank.getCustomerByNumber(cNumber));
        System.out.println("Compte ajouté avec succès");
    }

    public static void listerClients() {
        System.out.println("\n=== LISTE DES CLIENTS ===");
        for (Entry customers : bank.getCustomers().entrySet()) {
            Customer c = (Customer) customers.getValue();
            System.out.println(c.toString());
        }
    }

    public static void afficherClient() throws IOException {
        System.out.println("\n=== AFFICHER UN CLIENT ===");
        System.out.print("Numéro du client : ");
        int cNumber = Integer.parseInt(READER.readLine());
        System.out.println(bank.getCustomerByNumber(cNumber).toString());
        for (Entry accounts : bank.getCustomerByNumber(cNumber).getAccounts().entrySet()) {
            Account a = (Account) accounts.getValue();
            System.out.println(a.toString());
        }
    }

    public static void crediterCompte() throws IOException {
        System.out.println("\n=== CRÉDITER UN COMPTE ===");
        System.out.println("Numéro du compte : ");
        String aNumber = READER.readLine();
        System.out.println("Montant à créditer : ");
        double amount = Double.parseDouble(READER.readLine());
        bank.getAccountByNumber(aNumber).credit(amount);
        System.out.println("Compte crédité avec succès");
    }

    public static void debiterCompte() throws IOException {
        System.out.println("\n=== DÉBITER UN COMPTE ===");
        System.out.println("Numéro du compte : ");
        String aNumber = READER.readLine();
        System.out.println("Montant à débiter : ");
        double amount = Double.parseDouble(READER.readLine());
        bank.getAccountByNumber(aNumber).debit(amount);
        System.out.println("Compte débité avec succès");
    }

    public static void transfert() throws IOException {
        System.out.println("\n=== TRANSFÉRER UNE SOMME ===");
        System.out.println("Numéro du compte à créditer : ");
        String aCreditNumber = READER.readLine();
        System.out.println("Numéro du compte à débiter : ");
        String aDebitNumber = READER.readLine();
        System.out.println("Montant à transférer : ");
        double amount = Double.parseDouble(READER.readLine());
        Account.transfer(amount, bank.getAccountByNumber(aDebitNumber), bank.getAccountByNumber(aCreditNumber));
        System.out.println("Transféré avec succès");
    }

}
