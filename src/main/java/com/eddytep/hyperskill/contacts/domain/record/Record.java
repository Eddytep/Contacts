package com.eddytep.hyperskill.contacts.domain.record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

public abstract class Record implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Record.class);

    protected static final transient DateTimeFormatter LocalDateTimeFormatter =
            DateTimeFormatter.ofPattern("dd.MM.uuuu' 'HH:mm", Locale.getDefault());

    protected static final transient DateTimeFormatter LocalDateFormatter =
            DateTimeFormatter.ofPattern("dd.MM.uuuu", Locale.getDefault());

    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLastEdit;

    public Record(String name, String address, String phoneNumber) {
        this.name = name;
        this.phoneNumber = isPhoneNumberCorrect(phoneNumber) ? phoneNumber : "";
        this.address = address;
        this.timeCreated = LocalDateTime.now();
        this.timeLastEdit = timeCreated;
    }

    public Record(int id, String name, String phoneNumber, String address,
                  LocalDateTime timeCreated, LocalDateTime timeLastEdit) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.timeCreated = timeCreated;
        this.timeLastEdit = timeLastEdit;
    }

    public static DateTimeFormatter getLocalDateFormatter() {
        return LocalDateFormatter;
    }

    public void setFieldValue(String fieldName, String fieldValue) throws DomainException {
        if ("name".equalsIgnoreCase(fieldName)) {
            setName(fieldValue);
        } else if ("phoneNumber".equalsIgnoreCase(fieldName)) {
            setPhoneNumber(fieldValue);
        } else if ("address".equalsIgnoreCase((fieldName))) {
            setAddress(fieldValue);
        } else {
            logger.warn("There is no to set fieldName = " + fieldName);
            throw new DomainException("There is no to set fieldName = " + fieldName);
        }
    }

    public int getID() {
        return id;
    }

    public String getName() {
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
            logger.warn("Phone number has wrong format");
            throw new IllegalPhoneNumberException("phone number \" " + phoneNumber + "\" has bad form");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public LocalDateTime getTimeLastEdit() {
        return timeLastEdit;
    }

    public void setNowOfTimeLastEdit() {
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

    public abstract String getShortInformation();
}
