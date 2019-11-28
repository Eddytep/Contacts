package com.eddytep.hyperskill.contacts.dao;

import com.eddytep.hyperskill.contacts.domain.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class RecordDAOFile implements RecordDAO, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(RecordDAOFile.class);

    private static final String FILE_NAME = "AddressBook.db";
    private List<Record> records;


    RecordDAOFile() throws DAOException {
        try {
            readRecordsFromFile();
        } catch (DAOException e) {
            records = new ArrayList<>();
            logger.debug("Create file " + FILE_NAME);
            saveRecords();
        }
    }

    @SuppressWarnings("unchecked")
    private void readRecordsFromFile() throws DAOException {
        logger.trace("Load records from " + FILE_NAME);
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileInputStream = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            records = (ArrayList<Record>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.warn("Cannot read a file", e);
            throw new DAOException("Failed to read file", e);
        } finally {
            try {
                if (objectInputStream != null) objectInputStream.close();
                logger.trace("objectInputStream is closed");
                if (fileInputStream != null) fileInputStream.close();
                logger.trace("fileInputStream is closed");
            } catch (IOException | NullPointerException e) {
                logger.warn("Cannot close objectInputStream or fileInputString", e);
            }
        }
    }

    @Override
    public void addRecord(Record record) {
        records.add(record);
    }

    @Override
    public List<Record> getRecordList() {
        return records;
    }

    @Override
    public Integer getCountOfRecords() {
        return records.size();
    }

    @Override
    public void replaceRecord(Record oldRecord, Record newRecord) {
        records.set(records.indexOf(oldRecord), newRecord);
    }

    @Override
    public void editRecord(Record record, String fieldName, Object fieldValue) {
        record.setFieldValue(fieldName, fieldValue);
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

    public void saveRecords() throws DAOException {
        logger.trace("Saving data to " + FILE_NAME);
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(FILE_NAME);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(records);
        } catch (IOException e) {
            logger.error("Failed to write file", e);
            throw new DAOException("Failed to write file", e);
        } finally {
            try {
                if (objectOutputStream != null) objectOutputStream.close();
                logger.trace("objectOutputStream is closed");
                if (fileOutputStream != null) fileOutputStream.close();
                logger.trace("fileOutputStream is closed");
            } catch (IOException e) {
                logger.error("Cannot to close objectOutputStream or fileOutputStream", e);
            }
        }
    }

}
