package com.example.familyhelpuae.exception;

public class EmailDublicationException extends Exception {
    public EmailDublicationException() {
        super("Email already exists");
    }
}
