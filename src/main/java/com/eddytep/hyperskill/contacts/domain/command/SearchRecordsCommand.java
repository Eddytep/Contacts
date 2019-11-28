package com.eddytep.hyperskill.contacts.domain.command;

import com.eddytep.hyperskill.contacts.dao.RecordDAOSelector;
import com.eddytep.hyperskill.contacts.domain.record.Record;
import com.eddytep.hyperskill.contacts.ui.tui.TUI;

import java.util.List;

public class SearchRecordsCommand implements Command {
    @Override
    public void execute() {
        String searchQueryRegex = TUI.getExchangeContainer().getSearchQueryRegex();
        List<Record> foundedRecords = RecordDAOSelector.getStorage().searchRecords(searchQueryRegex);
        TUI.getExchangeContainer().setRecordList(foundedRecords);
    }
}
