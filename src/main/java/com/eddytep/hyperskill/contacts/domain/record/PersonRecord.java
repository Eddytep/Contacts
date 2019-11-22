package com.eddytep.hyperskill.contacts.domain.record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class PersonRecord extends Record {

    private static final Logger logger = LoggerFactory.getLogger(PersonRecord.class);

    private String surname;
    private LocalDate birthday;
    private Gender gender;

    public PersonRecord(String name, String surname, LocalDate birthday, Gender gender, String phoneNumber, String address) {
        super(name, address, phoneNumber);
        this.surname = surname;
        this.birthday = birthday;
        this.gender = gender;
    }

    public PersonRecord(int id, String name, String surname, LocalDate birthday, Gender gender, String phoneNumber,
                        String address, LocalDateTime timeCreated, LocalDateTime timeLastEdit) {
        super(id, name, phoneNumber, address, timeCreated, timeLastEdit);
        this.surname = surname;
        this.birthday = birthday;
        this.gender = gender;
    }

    @Override
    public void setFieldValue(String fieldName, String fieldValue) throws DomainException {
        try {
            if ("surname".equalsIgnoreCase(fieldName)) {
                setSurname(fieldValue);
            } else if ("birthday".equalsIgnoreCase(fieldName)) {
                setBirthday(fieldValue);
            } else if ("gender".equalsIgnoreCase(fieldName)) {
                setGender(Gender.getInstanceBy(fieldValue));
            } else {
                super.setFieldValue(fieldName, fieldValue);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("There is no to set fieldName = " + fieldName, e);
            throw new DomainException("There is no to set fieldName = " + fieldName, e);
        }

    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(String birthday) throws DomainException {
        try {
            this.birthday = LocalDate.parse(birthday, getLocalDateFormatter());
        } catch (DateTimeParseException e) {
            logger.warn("Cannot to parse a date string", e);
            throw new DomainException("Cannot to parse a date string", e);
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGenderString() {
        return gender == null || gender == Gender.UNKNOWN_GENDER ? "[no data]" : gender.toString();
    }

    public String getBirthdayString() {
        return birthday == null ? "[no data]" : birthday.toString();
    }

    @Override
    public String toString() {
        return  "ID: " + getID() + "\n" +
                "Name: " + getName() + "\n" +
                "Surname: " + getSurname() + "\n" +
                "Birth date: " + getBirthday().format(LocalDateFormatter) + "\n" +
                "Gender: " + getGenderString() + "\n" +
                "Number: " + getPhoneNumber() + "\n" +
                "Time created: " + getTimeCreated().format(LocalDateTimeFormatter) + "\n" +
                "Time last edit: " + getTimeLastEdit().format(LocalDateTimeFormatter) + "\n";
    }

    @Override
    public String getShortInformation() {
        return getName() + " " + getSurname();
    }
}
