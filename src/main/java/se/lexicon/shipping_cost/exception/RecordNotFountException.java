package se.lexicon.shipping_cost.exception;

public class RecordNotFountException extends Exception {

    private String message;

    public RecordNotFountException(String message) {
        super(message);
    }
}