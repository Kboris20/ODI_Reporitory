/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.odi.serie3.exceptions;

/**
 *
 * @author Jérémy
 */
public class AccountAlreadyExistException extends Exception {
    public AccountAlreadyExistException() {
        System.out.println("Un compte avec ce numéro existe déjà");
    }
}
