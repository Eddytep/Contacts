package com.eddytep.hyperskill.contacts.domain.record;

public class IllegalPhoneNumberException extends RuntimeException {

    public IllegalPhoneNumberException(String message) {
        super(message);
    }

    public IllegalPhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
