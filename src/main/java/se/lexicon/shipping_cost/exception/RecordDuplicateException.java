package se.lexicon.shipping_cost.exception;

public class RecordDuplicateException extends Exception {

    private String message;

    public RecordDuplicateException(String message) {
        super(message);
    }

    public RecordDuplicateException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}