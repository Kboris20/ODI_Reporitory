package ch.hearc.ig.odi.serie3.application;

import ch.hearc.ig.odi.serie3.business.Account;
import ch.hearc.ig.odi.serie3.business.Bank;
import ch.hearc.ig.odi.serie3.business.Customer;
import ch.hearc.ig.odi.serie3.exceptions.AccountAlreadyExistException;
import ch.hearc.ig.odi.serie3.exceptions.CustomerAlreadyExistException;
import ch.hearc.ig.odi.serie3.exceptions.InsufficientBalanceException;
import ch.hearc.ig.odi.serie3.exceptions.NegativeAmmountException;
import ch.hearc.ig.odi.serie3.exceptions.UnknownAccountException;
import ch.hearc.ig.odi.serie3.exceptions.UnknownCustomerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author boris.klett
 */
public class Application {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static Bank bank = new Bank(1, "UBS");

    public static void main(String[] args) throws Exception {

        Integer choice;

        do {
            choice = null;
            // APPEL DU MENU PRINCIPAL
            getMenu();

            // RECUPERATION DU CHOIX UTILISATEUR
            while (choice == null || choice > 7) {
                System.out.print(">> ");
                try {
                    choice = Integer.parseInt(READER.readLine());
                } catch (NumberFormatException ex) {
                    System.out.println("Veuillez entrer un numéro valable");
                }
            }

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
    }

    public static void creerClient() throws IOException {
        Customer c = new Customer();
        try {
            System.out.println("\n === CREER UN CLIENT ===");

            // RECUPERATION DU NUMERO DE CLIENT
            while (c.getNumber() == null) {
                try {
                    System.out.print("Numéro du client : ");
                    c.setNumber(Integer.parseInt(READER.readLine()));
                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                }
            }

            // RECUPERATION DU PRENOM DU CLIENT
            System.out.print("Prénom du client : ");
            c.setFirstName(READER.readLine());

            // RECUPERATION DU NOM DU CLIENT
            System.out.print("Nom du client : ");
            c.setLastName(READER.readLine());

            // AJOUT DU CLIENT A LA BANQUE
            bank.addCustomer(c);
            System.out.println("Client ajouté avec succès");
        } catch (CustomerAlreadyExistException ex) {
            ex.getMessage();
        }
    }

    public static void ajouterCompte() throws IOException {
        Customer c = null;
        Account acc = new Account();
        try {
            System.out.println("\n === AJOUTER UN COMPTE ===");
            
            // RECUPERATION DU NUMERO DU CLIENT
            while (c == null) {
                try {
                    System.out.print("Numéro du client : ");
                    c = bank.getCustomerByNumber(Integer.parseInt(READER.readLine()));
                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                }
            }
            
            // RECUPERATION DU NUMERO DE COMPTE
            System.out.print("Numéro du compte : ");
            acc.setNumber(READER.readLine());
            
            // RECUPERATION DU NOM DE COMPTE
            System.out.print("Nom du compte : ");
            acc.setName(READER.readLine());
            
            // RECUPERATION DU TAUX DE COMPTE
            while (acc.getRate() == null) {
                System.out.print("Taux du compte : ");
                try {
                    acc.setRate(Double.parseDouble(READER.readLine()));
                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                }
            }
            
            // AJOUT DU COMPTE A LA BANQUE
            bank.addAccount(acc, c);
            System.out.println("Compte ajouté avec succès");
        } catch (AccountAlreadyExistException | UnknownCustomerException ex) {
            ex.getMessage();
        }
    }

    public static void listerClients() {
        System.out.println("\n=== LISTE DES CLIENTS ===");

        // AFFICHAGE DES CLIENTS
        if (bank.getCustomers().size() > 0) {
            for (Entry customers : bank.getCustomers().entrySet()) {
                Customer c = (Customer) customers.getValue();
                System.out.println(c.toString());
            }
        } else {
            System.out.println("Aucun client dans la banque");
        }
    }

    public static void afficherClient() throws IOException {
        Customer c = null;

        try {
            System.out.println("\n=== AFFICHER UN CLIENT ===");

            // RECUPERATION DU CLIENT
            while (c == null) {
                System.out.print("Numéro du client : ");
                try {
                    c = bank.getCustomerByNumber(Integer.parseInt(READER.readLine()));
                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                }
            }

            // AFFICHAGE DU CLIENT ET DES COMPTES
            System.out.println(c.toString());
            if (c.getAccounts().size() > 0) {
                for (Entry accounts : c.getAccounts().entrySet()) {
                    Account a = (Account) accounts.getValue();
                    System.out.println(a.toString());
                }
            } else {
                System.out.println("\tCe client ne possède aucun compte");
            }
        } catch (UnknownCustomerException ex) {
            ex.getMessage();
        }
    }

    public static void crediterCompte() throws IOException {
        Account acc;
        Double amount = null;

        try {
            System.out.println("\n=== CRÉDITER UN COMPTE ===");

            // RECUPERATION DU COMPTE
            System.out.println("Numéro du compte : ");
            acc = bank.getAccountByNumber(READER.readLine());

            // RECUPERATION DU MONTANT A CREDITER
            while (amount == null) {
                System.out.println("Montant à créditer : ");
                try {
                    amount = Double.parseDouble(READER.readLine());
                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                }
            }

            // CREDITER LE COMPTE
            acc.credit(amount);
            System.out.println("Compte crédité avec succès");
        } catch (NegativeAmmountException | UnknownAccountException ex) {
            ex.getMessage();
        }
    }

    public static void debiterCompte() throws IOException {
        Account acc;
        Double amount = null;

        try {
            System.out.println("\n=== DÉBITER UN COMPTE ===");

            // RECUPERATION DU COMPTE
            System.out.println("Numéro du compte : ");
            acc = bank.getAccountByNumber(READER.readLine());

            // RECUPERATION DU MONTANT
            while (amount == null) {
                System.out.println("Montant à débiter : ");
                try {
                    amount = Double.parseDouble(READER.readLine());
                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                }
            }

            // DEBITER LE COMPTE
            acc.debit(amount);
            System.out.println("Compte débité avec succès");
        } catch (NegativeAmmountException | InsufficientBalanceException | UnknownAccountException ex) {
            ex.getMessage();
        }
    }

    public static void transfert() throws IOException {
        Account accCredit;
        Account accDebit;
        Double amount = null;

        try {
            System.out.println("\n=== TRANSFÉRER UNE SOMME ===");

            // RECUPERATION DU COMPTE A CREDITER
            System.out.println("Numéro du compte à créditer : ");
            accCredit = bank.getAccountByNumber(READER.readLine());

            // RECUPERATION DU COMPTE A DEBITER
            System.out.println("Numéro du compte à débiter : ");
            accDebit = bank.getAccountByNumber(READER.readLine());

            // RECUPERATION DU MONTANT A TRANSFERER
            while (amount == null) {
                System.out.println("Montant à transférer : ");
                try {
                    amount = Double.parseDouble(READER.readLine());
                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                }
            }

            // TRANSFERT DU MONTANT
            Account.transfer(amount, accDebit, accCredit);
            System.out.println("Transféré avec succès");
        } catch (NegativeAmmountException | InsufficientBalanceException | UnknownAccountException ex) {
            ex.getMessage();
        }
    }

}
