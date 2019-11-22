package com.eddytep.hyperskill.contacts.domain.record;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    UNKNOWN_GENDER("");

    private String genderValue;

    Gender(String genderValue) {
        this.genderValue = genderValue;
    }

    public static Gender getInstanceBy(String value) {
        if (Gender.MALE.toString().equals(value)) {
            return Gender.MALE;
        } else if (Gender.FEMALE.toString().equals(value)) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN_GENDER;
        }
    }

    @Override
    public String toString() {
        return genderValue;
    }
}
