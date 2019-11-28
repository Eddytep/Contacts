package com.eddytep.hyperskill.contacts.dao;

import com.eddytep.hyperskill.contacts.domain.record.Gender;
import com.eddytep.hyperskill.contacts.domain.record.OrganizationRecord;
import com.eddytep.hyperskill.contacts.domain.record.PersonRecord;
import com.eddytep.hyperskill.contacts.domain.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RecordDAODatabase implements RecordDAO {

    private static final Logger logger = LoggerFactory.getLogger(RecordDAODatabase.class);
    private static final int PERSON_TYPE = 1;
    private static final int ORGANIZATION_TYPE = 2;

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "system";
    private static final String PASSWORD = "eddytep";
    private static final Properties PROPERTIES = new Properties();

    public RecordDAODatabase() {
        PROPERTIES.setProperty("user", USERNAME);
        PROPERTIES.setProperty("password", PASSWORD);
    }

    @Override
    public void addRecord(Record record) throws DAOException {
        logger.debug("Add record");
        final String insertRecord =
                "INSERT INTO CONTACTS.RECORDS(TYPE, NAME, SURNAME, PHONE_NUMBER, ADDRESS, BIRTHDAY, GENDER) " +
                        "VALUES (?,?,?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(insertRecord)
        ) {
            if (record.getClass() == PersonRecord.class) {
                addPerson(statement, record);
            } else if (record.getClass() == OrganizationRecord.class) {
                addOrganization(statement, record);
            } else {
                logger.error("There is not handler for class " + record.getClass());
                throw new DAOException("There is not handler for class " + record.getClass());
            }
            statement.execute();
        } catch (SQLException e) {
            logger.warn("Cannot to add the record", e);
            throw new DAOException("Cannot to add the record", e);
        }
        logger.debug("Record is added");
    }

    private void addPerson(PreparedStatement statement, Record record) throws DAOException {
        try {
            statement.setInt(1, PERSON_TYPE);
            statement.setString(2, record.getName());
            statement.setString(3, ((PersonRecord) record).getSurname());
            statement.setString(4, record.getPhoneNumber());
            statement.setString(5, record.getAddress());
            statement.setObject(6, ((PersonRecord) record).getBirthday());
            Gender gender = ((PersonRecord) record).getGender();
            statement.setString(7, gender != null ? gender.toString() : "");
        } catch (SQLException e) {
            logger.warn("Cannot to get a record from prepared statement", e);
            throw new DAOException("Cannot to get a record from prepared statement", e);
        }
    }

    private void addOrganization(PreparedStatement statement, Record record) throws DAOException {
        try {
            statement.setInt(1, ORGANIZATION_TYPE);
            statement.setString(2, record.getName());
            statement.setString(3, "");
            statement.setString(4, record.getPhoneNumber());
            statement.setString(5, record.getAddress());
            statement.setObject(6, null);
            statement.setString(7, "");
        } catch (SQLException e) {
            logger.warn("Cannot to get a record from prepared statement", e);
            throw new DAOException("Cannot to get a record from prepared statement", e);
        }
    }

    @Override
    public List<Record> getRecordList() throws DAOException {
        logger.debug("Getting all records from database");
        List<Record> allRecords = null;
        String getAllRecords = "SELECT * FROM CONTACTS.RECORDS";
        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(getAllRecords);
             ResultSet result = statement.executeQuery()
        ) {
            allRecords = getRecordsFrom(result);
        } catch (SQLException e) {
            logger.warn("Cannot get record list ", e);
            throw new DAOException("Cannot get record list", e);
        }
        logger.debug("All records is obtained");
        return allRecords;
    }

    private List<Record> getRecordsFrom(ResultSet result) {
        List<Record> recordList = new ArrayList<>();
        Record record;
        try {
            while (result.next()) {
                int type = result.getInt("TYPE");
                if (type == PERSON_TYPE) {
                    record = getPersonRecordFrom(result);
                } else if (type == ORGANIZATION_TYPE) {
                    record = getOrganizationRecordForm(result);
                } else {
                    logger.error("There is not handler for class type " + type);
                    throw new DAOException("There is not handler for class type " + type);
                }
                recordList.add(record);
            }
        } catch (SQLException e) {
            logger.warn("Cannot get records", e);
            throw new DAOException("Cannot get records", e);
        }
        return recordList;
    }

    private Record getPersonRecordFrom(ResultSet result) throws DAOException {
        Record record;
        try {
            String gender = result.getString("GENDER");
            record = new PersonRecord(
                    result.getInt("ID"),
                    result.getString("NAME"),
                    result.getString("SURNAME"),
                    result.getObject("BIRTHDAY", LocalDate.class),
                    gender != null ? Gender.getInstanceBy(result.getString("GENDER")) : Gender.UNKNOWN_GENDER,
                    result.getString("PHONE_NUMBER"),
                    result.getString("ADDRESS"),
                    result.getObject("TIME_CREATED", LocalDateTime.class),
                    result.getObject("TIME_LAST_EDIT", LocalDateTime.class)
            );
        } catch (SQLException | IllegalArgumentException e) {
            logger.warn("Cannot to save result set into record", e);
            throw new DAOException("Cannot to save result set into record", e);
        }
        return record;
    }

    private Record getOrganizationRecordForm(ResultSet result) throws DAOException {
        Record record;
        try {
            record = new OrganizationRecord(
                    result.getInt("ID"),
                    result.getString("NAME"),
                    result.getString("PHONE_NUMBER"),
                    result.getString("ADDRESS"),
                    result.getObject("TIME_CREATED", LocalDateTime.class),
                    result.getObject("TIME_LAST_EDIT", LocalDateTime.class)
            );
            return record;
        } catch (SQLException | IllegalArgumentException e) {
            logger.warn("Cannot to save result set into record", e);
            throw new DAOException("Cannot to save result set into record", e);
        }
    }

    @Override
    public Integer getCountOfRecords() throws DAOException {
        logger.debug("Getting a number of records in DB");
        int size = 0;
        try {
            size = getRecordList().size();
            logger.debug("A number of records is obtained");
            return size;
        } catch (NullPointerException e) {
            logger.warn("Cannot to get a number of records", e);
            throw new DAOException("Cannot to get a number of records", e);
        }
    }

    @Override
    public void replaceRecord(Record oldRecord, Record newRecord) {
        logger.debug("Replacing the record");
        removeRecord(oldRecord);
        addRecord(newRecord);
        logger.debug("The record is replaced");
    }

    @Override
    public void editRecord(Record record, String fieldName, Object fieldValue) throws DAOException {
        logger.debug("Editing a record");
        String editRecord = "UPDATE CONTACTS.RECORDS SET " + fieldName + " = ? WHERE ID = ?";
        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(editRecord)
        ) {
            if ("NAME".equalsIgnoreCase(fieldName) ||
                    "SURNAME".equalsIgnoreCase(fieldName) ||
                    "ADDRESS".equalsIgnoreCase(fieldName)) {
                statement.setString(1, (String) fieldValue);
            } else if ("PHONE_NUMBER".equalsIgnoreCase(fieldName) ||
                    "phoneNumber".equalsIgnoreCase(fieldName)) {
                statement.setString(1, (String) fieldValue);
            } else if ("BIRTHDAY".equalsIgnoreCase(fieldName)) {
                statement.setObject(1, fieldValue);
            } else if ("GENDER".equalsIgnoreCase(fieldName)) {
                Gender gender = (Gender) fieldValue;
                statement.setString(1, gender != null ? gender.toString() : "");
            } else {
                logger.error("There is not the field name:" + fieldName);
                throw new DAOException("There is not the field name:" + fieldName);
            }
            statement.setInt(2, record.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Cannot to edit a record", e);
            throw new DAOException("Cannot to edit a record", e);
        }
        logger.debug("A record is edited");
    }

    @Override
    public void removeRecord(Record record) throws DAOException {
        logger.debug("Deleting the record from database");
        String deleteRecordById = "DELETE FROM CONTACTS.RECORDS WHERE ID = ?";
        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(deleteRecordById)
        ) {
            statement.setInt(1, record.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Cannot to delete the record", e);
            throw new DAOException("Cannot to delete the record", e);
        }
        logger.debug("The record is deleted");
    }

    @Override
    public List<Record> searchRecords(String regex) throws DAOException {
        logger.debug("Searching records. Regex = \'" + regex + "\'");
        String searchRecords =
                "SELECT * FROM CONTACTS.RECORDS WHERE LOWER(NAME || ' ' || SURNAME) LIKE ?";
        List<Record> recordList;
        Record record;
        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(searchRecords)
        ) {
            statement.setString(1, "%" + regex.toLowerCase() + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                recordList = getRecordsFrom(resultSet);
            }
        } catch (DAOException | SQLException e) {
            logger.warn("Cannot to search records",e);
            throw new DAOException("Cannot to search records", e);
        }
        logger.debug("Search completed");
        return recordList;
    }

    @Override
    public void saveRecords() {
        //The method should be empty.
    }

}
