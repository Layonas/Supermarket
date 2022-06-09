package Exceptions;

public class PayNotAcceptedException extends Exception{
    public PayNotAcceptedException() {
        System.out.println("You payment is not in valid format and therefore is unaccepted. \n");
    }
}
