package com.missio.worship.missioworshipbackend.libs.authentication.errors;

public class InvalidProvidedToken extends Exception {
    public InvalidProvidedToken(String msg) {
        super(msg);
    }

    public InvalidProvidedToken() {
        super("El token introducido no es válido. La solicitud carece de autorización para continuar.");
    }
}
