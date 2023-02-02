package com.tools.model.enums;

public enum OrderingStatus {
    ISSUED("Оформляется"),
    NOT_CONFIRMED("Не подтверждено"),
    CONFIRMED("Подтверждено"),
    SHIPPED("Отгружено");
    private final String name;

    public String getName() {
        return name;
    }

    OrderingStatus(String name) {
        this.name = name;
    }
}
