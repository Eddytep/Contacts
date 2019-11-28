package com.eddytep.hyperskill.contacts.domain.command;

import com.eddytep.hyperskill.contacts.dao.RecordDAOSelector;
import com.eddytep.hyperskill.contacts.dao.RecordDAOType;

public class SelectFileStorageCommand implements Command {

    @Override
    public void execute() {
        RecordDAOSelector.setStorageTypeDAO(RecordDAOType.FILE);
    }
}
