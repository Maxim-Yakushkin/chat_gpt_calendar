package com.example.chat_gpt_calendar;

public enum LocalizedMonth {
    JANUARY("January", "Январь"),
    FEBRUARY("February", "Февраль"),
    MARCH("March", "Март"),
    APRIL("April", "Апрель"),
    MAY("May", "Май"),
    JUNE("June", "Июнь"),
    JULY("July", "Июль"),
    AUGUST("August", "Август"),
    SEPTEMBER("September", "Сентябрь"),
    OCTOBER("October", "Октябрь"),
    NOVEMBER("November", "Ноябрь"),
    DECEMBER("December", "Декабрь");

    private final String englishName;
    private final String localizedName;

    LocalizedMonth(String englishName, String localizedName) {
        this.englishName = englishName;
        this.localizedName = localizedName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getLocalizedName() {
        return localizedName;
    }
}

