package com.eddytep.hyperskill.contacts.domain.record;

import java.time.LocalDate;

public class PersonRecord extends Record {
    private String surname;
    private LocalDate birthday;
    private Gender gender;

    public PersonRecord(String name, String surname, LocalDate birthday, Gender gender, String phoneNumber, String address) {
        super(name, address, phoneNumber);
        this.surname = surname;
        this.birthday = birthday;
        this.gender = gender;
    }

    private String getSurname() {
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

    public void setBirthday(String birthday) {
        this.birthday = LocalDate.parse(birthday);
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    private String getGenderString() {
        return gender == null || gender == Gender.UNKNOWN_GENDER ? "[no data]" : gender.toString();
    }

    private String getBirthdayString() {
        return birthday == null ? "[no data]" : birthday.toString();
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\n" +
                "Surname: " + getSurname() + "\n" +
                "Birth date: " + getBirthdayString() + "\n" +
                "Gender: " + getGenderString() + "\n" +
                "Number: " + getPhoneNumber() + "\n" +
                "Time created: " + getTimeCreated().format(formatter) + "\n" +
                "Time last edit: " + getTimeCreated().format(formatter) + "\n";
    }

    @Override
    public String getInfo() {
        return getName() + " " + getSurname();
    }
}
