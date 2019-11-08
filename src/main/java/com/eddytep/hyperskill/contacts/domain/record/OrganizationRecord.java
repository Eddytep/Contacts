package com.eddytep.hyperskill.contacts.domain.record;

public class OrganizationRecord extends Record {

    public OrganizationRecord(String name, String address, String phoneNumber) {
        super(name, address, phoneNumber);
    }

    @Override
    public String toString() {
        return "Organization name: " + getName() + "\n" +
                "Address: " + getAddress() + "\n" +
                "Number: " + getPhoneNumber() + "\n" +
                "Time created: " + getTimeCreated().format(formatter) + "\n" +
                "Time last edit: " + getTimeLastEdit().format(formatter) + "\n";
    }

    @Override
    public String getInfo() {
        return getName();
    }
}

