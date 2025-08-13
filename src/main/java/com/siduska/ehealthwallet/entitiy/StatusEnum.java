package com.siduska.ehealthwallet.entitiy;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum StatusEnum {

    PENDING,
    APPROVED,
    REJECTED;

    private static final Set<String> NAMES = Arrays.stream(values())
            .map(Enum::name)
            .collect(Collectors.toSet());

    public static boolean exists(String value) {
        return value != null && NAMES.contains(value.toUpperCase());
    }

}
