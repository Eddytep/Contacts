package com.eddytep.hyperskill.contacts;

import com.eddytep.hyperskill.contacts.dao.RecordDAOSelector;
import com.eddytep.hyperskill.contacts.dao.RecordDAOType;
import com.eddytep.hyperskill.contacts.domain.record.Record;
import com.eddytep.hyperskill.contacts.ui.tui.TUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.trace("Start program");
            TUI.run();

        logger.trace("Stop program");
    }
}
