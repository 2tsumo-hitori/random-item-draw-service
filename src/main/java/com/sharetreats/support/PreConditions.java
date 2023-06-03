package com.sharetreats.support;

public class PreConditions {
    public static void validate(boolean expression, String message) {
        if(!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
