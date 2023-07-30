package com.example.chat_gpt_calendar;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DateEntryService {
    private List<DateEntry> dateEntries = new ArrayList<>();

    public List<DateEntry> getAllDateEntries() {
        return dateEntries;
    }

    public void saveDateEntry(DateEntry dateEntry) {
        dateEntries.add(dateEntry);
    }
}
