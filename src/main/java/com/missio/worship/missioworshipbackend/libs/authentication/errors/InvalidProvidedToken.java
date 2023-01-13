package com.missio.worship.missioworshipbackend.libs.authentication.errors;

public class InvalidProvidedToken extends Exception {
    public InvalidProvidedToken(String msg) {
        super(msg);
    }
}
