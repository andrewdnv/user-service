package com.github.andrewdnv.service.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum UserStatus {

    ACTIVE("active"),
    INACTIVE("inactive"),
    BLOCKED("blocked");

    @Getter
    private final String value;

    public static UserStatus fromValue(String value) {
        return Arrays.stream(UserStatus.values())
            .filter(e -> value.equals(e.getValue()))
            .findFirst()
            .orElse(null);
    }

}