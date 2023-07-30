package com.example.chat_gpt_calendar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public Map<String, String> monthNamesMap() {
        Map<String, String> monthNames = new HashMap<>();
        // Здесь указываем названия месяцев на английском в качестве ключей, а на русском в качестве значений
        monthNames.put("january", "Январь");
        monthNames.put("february", "Февраль");
        monthNames.put("march", "Март");
        monthNames.put("april", "Апрель");
        monthNames.put("may", "Май");
        monthNames.put("june", "Июнь");
        monthNames.put("july", "Июль");
        monthNames.put("august", "Август");
        monthNames.put("september", "Сентябрь");
        monthNames.put("october", "Октябрь");
        monthNames.put("november", "Ноябрь");
        monthNames.put("december", "Декабрь");
        return monthNames;
    }
}

