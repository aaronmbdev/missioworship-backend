package com.missio.worship.missioworshipbackend.libs.users.errors;

public class MissingRequiredException extends Exception {
    public MissingRequiredException(String msg) {
        super(msg);
    }
}
