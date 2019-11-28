package com.eddytep.hyperskill.contacts.ui.tui;

import com.eddytep.hyperskill.contacts.domain.command.*;
import com.eddytep.hyperskill.contacts.domain.record.Gender;
import com.eddytep.hyperskill.contacts.domain.record.OrganizationRecord;
import com.eddytep.hyperskill.contacts.domain.record.PersonRecord;
import com.eddytep.hyperskill.contacts.domain.record.Record;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * The class implement Terminal User Interface
 */
public class TUI {
    private static final Scanner scanner = new Scanner(System.in);

    private static DataExchangeContainer exchangeContainer = new DataExchangeContainer();

    private TUI() {}

    public static DataExchangeContainer getExchangeContainer() {
        return exchangeContainer;
    }

    public static void run() {
        (new SelectDBStorageCommand()).execute();
        System.out.println();
        while (true) {
            System.out.print("[menu] Enter action (add, list, search, count, storage, exit): > ");
            String action = scanner.nextLine();
            if ("add".equals(action)) {
                addRecordToNotepad();
            } else if ("list".equals(action)) {
                listRecords();
            } else if ("search".equals(action)) {
                searchRecords();
            } else if ("count".equals(action)) {
                countOfRecords();
            } else if ("storage".equals(action)) {
                chooseStorage();
            } else if ("exit".equals(action)) {
                saveRecordsToStorage();
                break;
            } else {
                System.out.println("Wrong action!");
            }
            System.out.println();
        }
    }

    private static void chooseStorage() {
        try {
            while (true) {
                System.out.print("Choose storage of records (database, file): > ");
                String type = scanner.nextLine();
                if ("database".equals(type)) {
                    (new SelectDBStorageCommand()).execute();
                    System.out.println("Database is selected");
                    break;
                } else if ("file".equals(type)) {
                    (new SelectFileStorageCommand()).execute();
                    System.out.println("Storage in file selected");
                    break;
                } else {
                    System.out.println("Wrong storage");
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Cannot to choose storage");
        }
    }

    private static void saveRecordsToStorage() {
        (new SaveCommand()).execute();
    }

    private static void addRecordToNotepad() {
        while (true) {
            System.out.print("Enter the type (person, organization): > ");
            String type = scanner.nextLine();
            if ("person".equals(type)) {
                addPerson();
                break;
            } else if ("organization".equals(type)) {
                addOrganization();
                break;
            } else {
                System.out.println("Wrong type");
            }
        }
        System.out.println("The record added.");
    }

    private static void addPerson() {
        System.out.print("Enter the name: > ");
        String name = scanner.nextLine();
        System.out.print("Enter the surname: > ");
        String surname = scanner.nextLine();
        System.out.print("Enter the birth date: > ");
        LocalDate birthDay = null;
        try {
            birthDay = LocalDate.parse(scanner.nextLine(),Record.getLocalDateFormatter());
        } catch (RuntimeException e) {
            System.out.println("Bad birth date!");
        }
        System.out.print("Enter the gender (" + Gender.MALE + ", " + Gender.FEMALE + "): > ");
        Gender gender = Gender.getInstanceBy(scanner.nextLine());
        if (gender == Gender.UNKNOWN_GENDER) System.out.println("Bad gender!");
        System.out.print("Enter the number: > ");
        String phoneNumber = scanner.nextLine();
        if (!Record.isPhoneNumberCorrect(phoneNumber)) {
            phoneNumber = "";
            System.out.println("Wrong number format!");
        }
        exchangeContainer.setRecord(new PersonRecord(name, surname, birthDay, gender, phoneNumber, ""));
        (new AddRecordCommand()).execute();
    }

    private static void addOrganization() {
        System.out.print("Enter the organization name: > ");
        String organizationName = scanner.nextLine();
        System.out.print("Enter the address: > ");
        String address = scanner.nextLine();
        System.out.print("Enter the number: > ");
        String phoneNumber = scanner.nextLine();
        if (!Record.isPhoneNumberCorrect(phoneNumber)) {
            phoneNumber = "";
            System.out.println("Wrong number format!");
        }
        exchangeContainer.setRecord(new OrganizationRecord(organizationName, address, phoneNumber));
        (new AddRecordCommand()).execute();
    }

    private static void listRecords() {
        (new ListOfRecordsCommand()).execute();
        List<Record> records = exchangeContainer.getRecordList();
        if (records == null || records.size() == 0) {
            System.out.println("No records");
            return;
        }
        for (int i = 0; i < records.size(); i++) {
            System.out.println(i + 1 + ". " + records.get(i).getShortInformation());
        }
        System.out.println();
        System.out.print("[list] Enter action ([number], back): > ");
        String action = scanner.nextLine();
        if ("back".equals(action)) {
            return;
        }
        try {
            int index = Integer.parseInt(action) - 1;
            System.out.println(records.get(index));
            changeRecord(index);
        } catch (NumberFormatException e) {
            System.out.println("Wrong action");
        }
    }

    private static void changeRecord(int index) {
        while (true) {
            System.out.print("[record] Enter action (edit, delete, menu): > ");
            String action = scanner.nextLine();
            if ("edit".equals(action)) {
                editRecord(index);
            } else if ("delete".equals(action)) {
                removeRecordFromNotepad(index);
            } else if ("menu".equals(action)) {
                return;
            } else {
                System.out.println("Wrong action!");
            }
            System.out.println();
        }
    }

    private static void searchRecords() {
        while (true) {
            System.out.print("Enter search query: > ");
            String regex = scanner.nextLine();
            exchangeContainer.setSearchQueryRegex(regex);
            (new SearchRecordsCommand()).execute();
            List<Record> foundedRecords = exchangeContainer.getRecordList();
            printRecordsList(foundedRecords);
            System.out.print("[search] Enter action ([number], back, again): > ");
            String action = scanner.nextLine();
            if ("again".equals(action)) {
                continue;
            } else if ("back".equals(action)) {
                break;
            } else if (action.matches("\\d+")) {
                int index = Integer.parseInt(action) - 1;
                if (foundedRecords.get(index) != null) {
                    System.out.println();
                    changeRecord(index);
                    return;
                } else {
                    System.out.println("There is not record in this position!");
                }
            } else {
                System.out.println("There is not the action!");
            }
        }
    }

    private static void countOfRecords() {
        (new ListOfRecordsCommand()).execute();
        System.out.println("The Phone Book has " + exchangeContainer.getRecordList().size() + " records.");
    }

    private static void editRecord(int index) {
        List<Record> recordList = getExchangeContainer().getRecordList();
        if (recordList.size() == 0) {
            System.out.println("No records to edit!");
        } else {
            if (recordList.get(index).getClass() == PersonRecord.class) {
                editPerson(index);
            } else if (recordList.get(index).getClass() == OrganizationRecord.class) {
                editOrganization(index);
            }
            System.out.println("The record updated!");
        }
    }

    private static void editPerson(int index) {
        Record record = getExchangeContainer().getRecordList().get(index);
        while (true) {
            System.out.print("Select a field (name, surname, birth, gender, number): > ");
            String input = scanner.nextLine();
            if ("name".equals(input)) {
                System.out.print("Enter the name: > ");
                exchangeContainer.setFieldValue(scanner.nextLine());
            } else if ("surname".equals(input)) {
                System.out.print("Enter the surname: > ");
                exchangeContainer.setFieldValue(scanner.nextLine());
            } else if ("birth".equals(input)) {
                input = "birthday";
                System.out.print("Enter the birth: > ");
                try {
                    exchangeContainer.setFieldValue(LocalDate.parse(scanner.nextLine(),Record.getLocalDateFormatter()));
                } catch (Exception e) {
                    System.out.println("Bad birthday");
                }
            } else if ("gender".equals(input)) {
                System.out.print("Enter the gender (" + Gender.MALE + ", " + Gender.FEMALE + "): > ");
                exchangeContainer.setFieldValue(Gender.getInstanceBy(scanner.nextLine()));
            } else if ("number".equals(input)) {
                System.out.print("Enter the number: > ");
                input = "phoneNumber";
                exchangeContainer.setFieldValue(scanner.nextLine());
            } else {
                System.out.println("Wrong a field");
                continue;
            }
            exchangeContainer.setRecord(record);
            exchangeContainer.setFieldName(input);
            (new EditRecordCommand()).execute();
            break;
        }
    }

    private static void editOrganization(int index) {
        List<Record> recordList = getExchangeContainer().getRecordList();
        while (true) {
            System.out.print("Select a field (name, address, number): > ");
            String input = scanner.nextLine();
            if ("name".equals(input)) {
                System.out.print("Enter the organization name: > ");
                recordList.get(index).setName(scanner.nextLine());
                break;
            } else if ("address".equals(input)) {
                System.out.println("Enter the address: > ");
                recordList.get(index).setAddress(scanner.nextLine());
                break;
            } else if ("number".equals(input)) {
                System.out.println("Enter the number: > ");
                recordList.get(index).setPhoneNumber(scanner.nextLine());
                break;
            }
        }
    }

    private static void printRecordsList(List<Record> records) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (Record record : records) {
            stringBuilder.append(i++).append(". ").append(record.getShortInformation()).append("\n");
        }
        System.out.println(stringBuilder);
    }

    private static void removeRecordFromNotepad(int index) {
        List<Record> records = exchangeContainer.getRecordList();
        if (records.size() == 0) {
            System.out.println("No records to remove!");
        } else {
            Record record = records.get(index);
            exchangeContainer.setRecord(record);
            (new RemoveRecordCommand()).execute();
        }
    }

}
