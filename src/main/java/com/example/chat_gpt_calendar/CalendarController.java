package com.example.chat_gpt_calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.DayOfWeek;
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

        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY; // Задайте здесь день недели, с которого начинается календарная неделя

        // Находим первый день месяца, который соответствует первому дню недели
        while (firstDayOfMonth.getDayOfWeek() != firstDayOfWeek) {
            firstDayOfMonth = firstDayOfMonth.minusDays(1);
        }

        // Находим последний день месяца, который соответствует последнему дню недели
        while (lastDayOfMonth.getDayOfWeek() != firstDayOfWeek.minus(1)) {
            lastDayOfMonth = lastDayOfMonth.plusDays(1);
        }

        // Добавляем дни предыдущего месяца перед первым днем текущего месяца
        LocalDate previousMonthLastDay = firstDayOfMonth.minusDays(1);
        while (previousMonthLastDay.getDayOfWeek() != firstDayOfWeek.minus(1)) {
            previousMonthLastDay = previousMonthLastDay.minusDays(1);
        }

        List<LocalDate> days = new ArrayList<>();
        LocalDate currentDay = previousMonthLastDay.plusDays(1);
        while (!currentDay.isAfter(lastDayOfMonth)) {
            days.add(currentDay);
            currentDay = currentDay.plusDays(1);
        }

        // Добавляем дни следующего месяца после последнего дня текущего месяца
        LocalDate nextMonthFirstDay = lastDayOfMonth.plusDays(1);
        while (days.size() < 42) { // Всего 42 ячейки для отображения 6 недель
            days.add(nextMonthFirstDay);
            nextMonthFirstDay = nextMonthFirstDay.plusDays(1);
        }

        return days;
    }

    private List<List<LocalDate>> splitIntoWeeks(List<LocalDate> days) {
        List<List<LocalDate>> weeks = new ArrayList<>();
        int offset = 1; // Значение 1 соответствует понедельнику
        for (int i = 0; i < days.size(); i += 7) {
            List<LocalDate> week = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                int index = i + j;
                if (index < days.size() && days.get(index).getDayOfWeek().getValue() == offset) {
                    week.add(days.get(index));
                    offset = (offset % 7) + 1; // Изменяем смещение на следующий день недели
                } else {
                    week.add(null); // Добавляем пустой элемент для пустой ячейки
                }
            }
            weeks.add(week);
        }
        return weeks;
    }

}