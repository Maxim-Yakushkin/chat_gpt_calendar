package com.example.chat_gpt_calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalendarController {

    private DateEntry selectedDate = new DateEntry();

    @GetMapping("/")
    public String showCalendar(Model model) {
        LocalDate today = selectedDate.getSelectedDate() != null ? selectedDate.getSelectedDate() : LocalDate.now();
        List<LocalDate> currentMonthDays = getCurrentMonthDays(today);
        List<List<LocalDate>> weeks = splitIntoWeeks(currentMonthDays);

        model.addAttribute("weeks", weeks);
        model.addAttribute("selectedDate", selectedDate.getSelectedDate());
        return "calendar";
    }

    @PostMapping("/selectDate")
    public String selectDate(DateEntry dateEntry) {
        selectedDate.setSelectedDate(dateEntry.getSelectedDate());
        return "redirect:/";
    }

    @PostMapping("/prevMonth")
    public String showPrevMonth() {
        LocalDate selected = selectedDate.getSelectedDate();
        if (selected != null) {
            selectedDate.setSelectedDate(selected.minusMonths(1));
        }
        return "redirect:/";
    }

    @PostMapping("/nextMonth")
    public String showNextMonth() {
        LocalDate selected = selectedDate.getSelectedDate();
        if (selected != null) {
            selectedDate.setSelectedDate(selected.plusMonths(1));
        }
        return "redirect:/";
    }

    private List<LocalDate> getCurrentMonthDays(LocalDate date) {
        LocalDate firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());

        List<LocalDate> days = new ArrayList<>();
        LocalDate currentDay = firstDayOfMonth;
        while (!currentDay.isAfter(lastDayOfMonth)) {
            days.add(currentDay);
            currentDay = currentDay.plusDays(1);
        }
        return days;
    }

    private List<List<LocalDate>> splitIntoWeeks(List<LocalDate> days) {
        List<List<LocalDate>> weeks = new ArrayList<>();
        for (int i = 0; i < days.size(); i += 7) {
            weeks.add(days.subList(i, Math.min(i + 7, days.size())));
        }
        return weeks;
    }
}