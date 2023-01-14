package com.missio.worship.missioworshipbackend.libs.users.errors;

public class CannotCreateUserException extends Exception {
    public CannotCreateUserException(String reason) {
        super(reason);
    }
}
