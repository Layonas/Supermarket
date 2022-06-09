package Exceptions;

public class SoldOutException extends Exception{
    public SoldOutException() {
        System.out.println("The shop has run out of the item you are trying to buy.");
        System.out.println("Your transaction has been canceled. \n");
    }
}
