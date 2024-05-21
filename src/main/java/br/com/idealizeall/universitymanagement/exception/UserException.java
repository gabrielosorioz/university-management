package br.com.idealizeall.universitymanagement.exception;

public class UserException extends Exception {

    public UserException(String message){
        super(message);
    }

    public UserException(String message, Throwable throwable){
        super(message,throwable);
    }
}
