package com.eddytep.hyperskill.contacts.dao;

import com.eddytep.hyperskill.contacts.domain.record.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddressBookLocalDAO implements AddressBookDAO {

    private List<Record> records;

    @Override
    public void addRecord(Record record) {
        records.add(record);
    }

    @Override
    public List<Record> getRecords() {
        return records;
    }

    @Override
    public Integer getCountOfRecords() {
        return records.size();
    }

    @Override
    public void editRecord(Record currentRecord, Record editedRecord) {
        records.set(records.indexOf(currentRecord), editedRecord);
    }

    @Override
    public void removeRecord(Record record) {
        records.remove(record);
    }

    @Override
    public List<Record> searchRecords(String regex) {
        List<Record> out = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        for (Record record : records) {
            if (pattern.matcher(record.toString()).find()) {
                out.add(record);
            }
        }
        return out;
    }
}
