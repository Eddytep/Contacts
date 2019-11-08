package com.eddytep.hyperskill.contacts.domain.record;

public enum Gender {
    MALE("M"),
    FEMALE("F"),
    UNKNOWN_GENDER("");

    private final String shirtGender;

    Gender(String shirtGender) {
        this.shirtGender = shirtGender;
    }

    /**
     * Create new Gender by letter (M - MALE, F - FEMALE)
     * @param letter (M - MALE, F - FEMALE)
     * @return Gender instance
     */
    public static Gender getInstanceByLetter(String letter) {
        Gender gender;
        if ("M".equals(letter)) {
            gender = Gender.MALE;
        } else if ("F".equals(letter)) {
            gender = Gender.FEMALE;
        } else {
            gender = Gender.UNKNOWN_GENDER;
        }
        return gender;
    }

    @Override
    public String toString() {
        return shirtGender;
    }
}
