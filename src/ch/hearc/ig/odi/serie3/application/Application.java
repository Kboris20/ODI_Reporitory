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

/**
 *
 * @author boris.klett
 */
public class Application {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final Bank bank = new Bank(1, "UBS");

    public static void main(String[] args) throws Exception {
        bank.addCustomer(new Customer(10, "Jeremy", "Barfuss"));
        bank.addAccount(new Account("1", "Epargne", 1.25, bank.getCustomerByNumber(10)), bank.getCustomerByNumber(10));
        Integer choice;

        do {
            choice = null;
            // APPEL DU MENU PRINCIPAL
            getMenu();

            // RECUPERATION DU CHOIX UTILISATEUR
            while (choice == null || choice > 9) {
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
                    editerClient();
                    break;
                case 4:
                    editerCompte();
                    break;
                case 5:
                    listerClients();
                    break;
                case 6:
                    afficherClient();
                    break;
                case 7:
                    crediterCompte();
                    break;
                case 8:
                    debiterCompte();
                    break;
                case 9:
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
        System.out.println("3. Editer un client");
        System.out.println("4. Editer un compte");
        System.out.println("5. Lister les clients");
        System.out.println("6. Afficher un client et ses comptes");
        System.out.println("7. Créditer un compte");
        System.out.println("8. Débiter un compte");
        System.out.println("9. Transférer un montant");
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
                    acc.setCustomer(c);
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
            bank.addAccount(acc, bank.getCustomerByNumber(c.getNumber()));
            System.out.println("Compte ajouté avec succès");
        } catch (AccountAlreadyExistException | UnknownCustomerException ex) {
            ex.getMessage();
        }
    }

    public static void editerClient() throws IOException {
        Customer c = null;
        boolean bError = false;
        int iOldNumber = 0;
        String value;

        try {
            System.out.println("\n=== EDITER UN CLIENT ===");

            // RECUPERATION DU CLIENT
            while (c == null) {
                System.out.print("Numéro du client : ");
                try {
                    iOldNumber = Integer.parseInt(READER.readLine());
                    c = bank.getCustomerByNumber(iOldNumber);

                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                }
            }

            System.out.println(">> Edition de " + c.toString());

            // MODIFIER LE NUMERO DU CLIENT
            do {
                System.out.print("Nouveau numéro du client : ");
                try {
                    value = READER.readLine();
                    if (!value.equals("")) {
                        c.setNumber(Integer.parseInt(value));
                        bank.getCustomers().put(Integer.parseInt(value), bank.getCustomers().remove(iOldNumber));
                    }
                    bError = false;
                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                    bError = true;
                }
            } while (bError == true);

            // MODIFIER LE PRENOM DU CLIENT
            System.out.print("Nouveau prénom du client : ");
            value = READER.readLine();
            if (!value.equals("")) {
                c.setFirstName(value);
            }

            // MODIFIER LE NOM DU CLIENT
            System.out.print("Nouveau nom du client : ");
            value = READER.readLine();
            if (!value.equals("")) {
                c.setLastName(value);
            }

            System.out.println("Client modifié avec succès");
        } catch (UnknownCustomerException ex) {
            ex.getMessage();
        }
    }

    public static void editerCompte() throws IOException {
        Account acc = null;
        boolean bError = false;
        String strOldNumber;
        String value;

        try {
            System.out.println("\n=== ÉDITER UN COMPTE ===");

            // RECUPERATION DU COMPTE
            System.out.println("Numéro du compte : ");
            strOldNumber = READER.readLine();
            acc = bank.getAccountByNumber(strOldNumber);

            System.out.println(">> Edition de " + acc.toString());

            // MODIFIER LE NUMERO DU COMPTE
            System.out.print("Nouveau numéro du compte : ");
            value = READER.readLine();
            if (!value.equals("")) {
                acc.setNumber(value);
                bank.getAccounts().put(value, bank.getAccounts().remove(strOldNumber));
                acc.getCustomer().getAccounts().put(value, acc.getCustomer().getAccounts().remove(strOldNumber));
            }

            // MODIFIER LE NOM DU COMPTE
            System.out.print("Nouveau nom du compte : ");
            value = READER.readLine();
            if (!value.equals("")) {
                acc.setName(value);
            }

            // MODIFIER LE TAUX DU COMPTE
            do {
                System.out.print("Taux du compte : ");
                try {
                    value = READER.readLine();
                    if (!value.equals("")) {
                        acc.setRate(Double.parseDouble(value));
                    }
                    bError = false;
                } catch (NumberFormatException ex) {
                    System.out.println("/!\\ Veuillez entrer un nombre");
                    bError = true;
                }
            } while (bError == true);

            System.out.println("Compte modifié avec succès");
        } catch (UnknownAccountException ex) {
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
                    System.out.println("\t" + a.toString());
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
