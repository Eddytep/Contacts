package com.eddytep.hyperskill.contacts.dao;

import com.eddytep.hyperskill.contacts.domain.record.Record;

import java.util.List;

public interface AddressBookDAO {

    void addRecord(Record record);

    List<Record> getRecords();

    Integer getCountOfRecords();

    void editRecord(Record currentRecord, Record editedRecord);

    void removeRecord(Record record);

    List<Record> searchRecords(String regex);
}
