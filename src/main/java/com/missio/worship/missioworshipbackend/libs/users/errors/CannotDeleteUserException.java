package com.missio.worship.missioworshipbackend.libs.users.errors;

public class CannotDeleteUserException extends Exception {
    public CannotDeleteUserException(String reason) {
        super(reason);
    }
}
