package com.eddytep.hyperskill.contacts.dao;

import com.eddytep.hyperskill.contacts.domain.record.Record;

import java.util.List;

public interface RecordDAO {

    void addRecord(Record record);

    List<Record> getRecordList();

    Integer getCountOfRecords();

    void replaceRecord(Record oldRecord, Record newRecord);

    void editRecord(Record record, String fieldName, Object fieldValue);

    void removeRecord(Record record);

    List<Record> searchRecords(String regex);
}
