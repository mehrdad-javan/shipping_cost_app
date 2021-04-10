package se.lexicon.shipping_cost.exception;

public class ArgumentException extends RuntimeException {

    private String message;


    public ArgumentException(String message) {
        super(message);
    }

}