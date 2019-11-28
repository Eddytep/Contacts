package com.eddytep.hyperskill.contacts.domain.command;

import com.eddytep.hyperskill.contacts.dao.RecordDAOSelector;
import com.eddytep.hyperskill.contacts.domain.record.Record;
import com.eddytep.hyperskill.contacts.ui.tui.TUI;

import java.util.List;

public class ListOfRecordsCommand implements Command {
    @Override
    public void execute() {
        List<Record> recordList = RecordDAOSelector.getStorage().getRecordList();
        TUI.getExchangeContainer().setRecordList(recordList);
    }
}
