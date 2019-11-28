package com.eddytep.hyperskill.contacts.domain.command;

import com.eddytep.hyperskill.contacts.dao.RecordDAOSelector;
import com.eddytep.hyperskill.contacts.domain.DomainException;
import com.eddytep.hyperskill.contacts.ui.tui.DataExchangeContainer;
import com.eddytep.hyperskill.contacts.ui.tui.TUI;

public class EditRecordCommand implements Command {

    @Override
    public void execute() throws DomainException {
        DataExchangeContainer container = TUI.getExchangeContainer();
        RecordDAOSelector.getStorage()
                .editRecord(container.getRecord(), container.getFieldName(), container.getFieldValue());
    }
}
