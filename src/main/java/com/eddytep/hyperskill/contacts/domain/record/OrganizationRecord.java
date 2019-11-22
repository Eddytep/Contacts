package com.eddytep.hyperskill.contacts.domain.record;

import java.time.LocalDateTime;

public class OrganizationRecord extends Record {

    public OrganizationRecord(String name, String address, String phoneNumber) {
        super(name, address, phoneNumber);
    }

    public OrganizationRecord(int id, String name, String phoneNumber, String address,
                              LocalDateTime timeCreated, LocalDateTime timeLastEdit) {
        super(id, name, phoneNumber, address, timeCreated, timeLastEdit);
    }

    @Override
    public String toString() {
        return "Organization name: " + getName() + "\n" +
                "Address: " + getAddress() + "\n" +
                "Number: " + getPhoneNumber() + "\n" +
                "Time created: " + getTimeCreated().format(LocalDateTimeFormatter) + "\n" +
                "Time last edit: " + getTimeLastEdit().format(LocalDateTimeFormatter) + "\n";
    }

    @Override
    public String getShortInformation() {
        return getName();
    }
}

