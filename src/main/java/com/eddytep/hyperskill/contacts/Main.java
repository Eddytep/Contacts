package com.eddytep.hyperskill.contacts;

import com.eddytep.hyperskill.contacts.dao.DAOException;
import com.eddytep.hyperskill.contacts.dao.RecordDAO;
import com.eddytep.hyperskill.contacts.dao.RecordDAOFactory;
import com.eddytep.hyperskill.contacts.dao.RecordDAOType;
import com.eddytep.hyperskill.contacts.domain.record.Gender;
import com.eddytep.hyperskill.contacts.domain.record.PersonRecord;
import com.eddytep.hyperskill.contacts.domain.record.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public class Main {

    final private static RecordDAO database = new RecordDAOFactory().getAddressBookDAO(RecordDAOType.DATABASE);

    private static final Logger logger = LoggerFactory.getLogger(Main.class);



    public static void main(String[] args) {
        logger.trace("Start program");
        List<Record> recordList;
        Record record = new PersonRecord("John", "Snow",
                LocalDate.of(1980, 11, 15), Gender.MALE, "321", "USA");
        try {
            //database.addRecord(record);
            recordList = database.getRecordList();
            recordList.forEach(e -> System.out.println(e.getShortInformation()));
            System.out.println();
            recordList = database.searchRecords("john");
            recordList.forEach(e -> System.out.println(e.toString()));
//            database.editRecord(recordList.get(0),
//                    "birthday", LocalDate.parse("02.02.1961", Record.getLocalDateFormatter()));
            database.editRecord(recordList.get(0), "gender", Gender.FEMALE);
            recordList = database.searchRecords("john");
            recordList.forEach(e -> System.out.println(e.toString()));

        } catch (DAOException e) {
            System.out.println("Cannot get connection " + e);
        }
        logger.trace("Stop program");
    }
}
