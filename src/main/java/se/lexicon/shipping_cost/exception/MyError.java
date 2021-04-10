package se.lexicon.shipping_cost.exception;

import java.time.LocalDateTime;

public class MyError {

    private LocalDateTime timestamp;
    private String message;
    private String messageDetails;
    private String param;

    private MyError() {
        timestamp = LocalDateTime.now();
    }

    MyError(Throwable ex) {
        this();
        this.message = "Unexpected error";
        this.messageDetails = ex.getMessage();
    }

    MyError(String message, Throwable ex) {
        this();
        this.message = message;
        this.messageDetails = ex.getMessage();
    }

    MyError(String message, String param, Throwable ex) {
        this();
        this.message = message;
        this.param = param;
        this.messageDetails = ex.getMessage();
    }

    MyError(String message, String param) {
        this();
        this.message = message;
        this.param = param;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageDetails() {
        return messageDetails;
    }

    public String getParam() {
        return param;
    }

}