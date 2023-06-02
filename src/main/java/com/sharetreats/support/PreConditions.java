package com.sharetreats.support;

public class PreConditions {
    public static void validate(boolean expression, String message) {
        if(!expression) {
            throw new IllegalArgumentException(message);
            // 파라미터 값이 잘못됐을때 던지는거
            // 커스텀 익셉션 만들기
            // 커스텀 익셉션에서 에러코드를 받을 수 있게끔 만들기
            // String을 받는게 아니라 ErrorCode 객체를 받게하기
        }
    }
}
