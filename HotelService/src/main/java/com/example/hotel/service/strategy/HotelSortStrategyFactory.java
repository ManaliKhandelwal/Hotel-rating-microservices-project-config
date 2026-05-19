package com.example.hotel.service.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HotelSortStrategyFactory {

    private final Map<String, HotelSortStrategy> strategies = new HashMap<>();

    @Autowired
    public HotelSortStrategyFactory(List<HotelSortStrategy> strategyList) {
        for (HotelSortStrategy s : strategyList) {
            strategies.put(s.getName(), s);
        }
    }

    public HotelSortStrategy getStrategy(String name) {
        return strategies.getOrDefault(name, strategies.get("name"));
    }
}
