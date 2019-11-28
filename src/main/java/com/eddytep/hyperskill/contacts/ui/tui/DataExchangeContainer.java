package com.eddytep.hyperskill.contacts.ui.tui;

import com.eddytep.hyperskill.contacts.domain.record.Record;

import java.util.List;

public class DataExchangeContainer {

    private String fieldName;
    private Object fieldValue;
    private String searchQueryRegex;
    private Record record;
    private List<Record> recordList;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public String getSearchQueryRegex() {
        return searchQueryRegex;
    }

    public void setSearchQueryRegex(String searchQueryRegex) {
        this.searchQueryRegex = searchQueryRegex;
    }
}