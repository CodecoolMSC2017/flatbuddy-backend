package com.codecool.flatbuddy.model.enums;


public enum NotificationTypeEnum {
    MATCH("match"),
    ADVERTISEMENT("advertisement"),
    SLOT("slot");
    private final String value;
    NotificationTypeEnum(String value) {
        this.value=value;
    }
    public String getValue(){
        return value;
    }
}
