package com.codecool.flatbuddy.model.Enum;

public enum  MatchStatusEnum {
    SENTPENDING(1),
    ACCEPTED(2),
    RECEIVEDPENDING(3),
    DECLINED(4);
    private final int value;
    MatchStatusEnum(int value) {
        this.value=value;
    }
    public int getValue(){
        return value;
    }
}
