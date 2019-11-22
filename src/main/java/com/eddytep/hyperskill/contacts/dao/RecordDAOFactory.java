package com.eddytep.hyperskill.contacts.dao;

public class RecordDAOFactory {

    public RecordDAO getAddressBookDAO(RecordDAOType type) {
        RecordDAO recordDAO = null;
        if (type == RecordDAOType.LOCAL) {
            recordDAO = new RecordDAOFile();
        } else if (type == RecordDAOType.DATABASE) {
            recordDAO = new RecordDAODatabase();
        }
        return recordDAO;
    }

}
