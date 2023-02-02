package com.tools.model.enums;

public enum Category {
    HAMMERS("Молотки"),
    KEYS("Ключи"),
    ROLLERS("Валики"),
    BRUSHES("Кисти"),
    SCREWDRIVERS("Отвертки");

    private final String name;

    public String getName() {
        return name;
    }

    Category(String name) {
        this.name = name;
    }
}
