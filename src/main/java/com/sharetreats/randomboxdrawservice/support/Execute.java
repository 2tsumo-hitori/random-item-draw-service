package com.sharetreats.randomboxdrawservice.support;


import java.util.InputMismatchException;

import static com.sharetreats.randomboxdrawservice.RandomBoxDrawServiceApplication.scanner;

public class Execute {
    public static void execute(ActionCallback callback) {
        try {
            callback.execute();
        } catch(InputMismatchException | NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Boolean programExecution(ActionCallback... callbacks) {
        try {
            switch (scanner.next()) {
                case "1" -> execute(callbacks[0]);
                case "2" -> execute(callbacks[1]);
                case "3" -> {
                    return false;
                }
            }
        } catch(NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
        }
        return true;
    }
}
