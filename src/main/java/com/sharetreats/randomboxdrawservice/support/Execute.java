package com.sharetreats.randomboxdrawservice.support;

public class Execute {
    public static void execute(ActionCallback callback) {
        try {
            callback.execute();
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
