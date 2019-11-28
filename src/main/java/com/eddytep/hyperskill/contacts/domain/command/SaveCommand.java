package com.eddytep.hyperskill.contacts.domain.command;

import com.eddytep.hyperskill.contacts.dao.RecordDAOSelector;

public class SaveCommand implements Command {
    @Override
    public void execute() {
        RecordDAOSelector.getStorage().saveRecords();
    }
}
