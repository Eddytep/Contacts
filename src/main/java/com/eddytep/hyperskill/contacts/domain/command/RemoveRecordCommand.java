package com.eddytep.hyperskill.contacts.domain.command;

import com.eddytep.hyperskill.contacts.dao.RecordDAOSelector;
import com.eddytep.hyperskill.contacts.domain.record.Record;
import com.eddytep.hyperskill.contacts.ui.tui.TUI;

public class RemoveRecordCommand implements Command {
    @Override
    public void execute() {
        Record record = TUI.getExchangeContainer().getRecord();
        RecordDAOSelector.getStorage().removeRecord(record);
    }
}
