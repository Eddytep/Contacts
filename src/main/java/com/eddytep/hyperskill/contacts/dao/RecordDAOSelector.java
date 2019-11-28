package com.eddytep.hyperskill.contacts.dao;

public class RecordDAOSelector {

    private static RecordDAO storage = null;

    public static RecordDAO getStorage() {
        return storage;
    }

    public static void setStorageTypeDAO(RecordDAOType recordDAOType) {
        if (recordDAOType == RecordDAOType.FILE) {
            storage = new RecordDAOFile();
        } else if (recordDAOType == RecordDAOType.DATABASE) {
            storage = new RecordDAODatabase();
        }
    }
}
