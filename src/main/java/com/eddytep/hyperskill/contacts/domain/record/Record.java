package com.eddytep.hyperskill.contacts.domain.record;


import com.eddytep.hyperskill.contacts.domain.exception.IllegalPhoneNumberException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public abstract class Record implements Serializable {

    protected static final transient DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'hh:mm", Locale.getDefault());

    private String name;
    private String phoneNumber;
    private String address;
    private final LocalDateTime timeCreated;
    private LocalDateTime timeLastEdit;

    protected Record(String name, String address, String phoneNumber) {
        this.name = name;
        this.phoneNumber = isPhoneNumberCorrect(phoneNumber) ? phoneNumber : "";
        this.address = address;
        this.timeCreated = LocalDateTime.now();
        this.timeLastEdit = timeCreated;
    }

    protected String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws IllegalPhoneNumberException {
        if (isPhoneNumberCorrect(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalPhoneNumberException("phone number \" " + phoneNumber + "\" has bad form");
        }
    }

    protected String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    protected LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    protected LocalDateTime getTimeLastEdit() {
        return timeLastEdit;
    }

    public void setNowOfTimeLastEdit(LocalDateTime timeLastEdit) {
        this.timeLastEdit = LocalDateTime.now();
    }

    public boolean hasNumber() {
        return !Objects.equals("", phoneNumber);
    }

    public static boolean isPhoneNumberCorrect(String phoneNumber) {
        String firstPartRegexVariant1 = "\\(\\d\\)";
        String firstPartRegexVariant2 = "\\d+";
        String firstPartRegexVariant3 = "(\\d+[ -])?\\([0-9a-zA-Z]{2,}\\)";
        String secondPartRegex = "([ -][0-9a-zA-Z]{2,})*";
        String regex = "\\+?("+firstPartRegexVariant1 + "|" + firstPartRegexVariant2 + "|" +
                firstPartRegexVariant3 + ")?" + secondPartRegex;
        return phoneNumber.matches(regex);
    }

    @Override
    public abstract String toString();

    public abstract String getInfo();

}
