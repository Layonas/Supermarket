package Exceptions;

public class NotEnoughChangeException extends Exception{
    public NotEnoughChangeException() {
        System.out.println("The shop currently does not have enough change.");
    }
}
