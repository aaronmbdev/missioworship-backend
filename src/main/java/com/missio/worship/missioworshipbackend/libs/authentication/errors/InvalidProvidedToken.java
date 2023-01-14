package com.missio.worship.missioworshipbackend.libs.authentication.errors;

public class InvalidProvidedToken extends Exception {
    public InvalidProvidedToken(String msg) {
        super(msg);
    }

    public InvalidProvidedToken() {
        super("Error de autorización. La sesión no es valida o ha caducado");
    }
}
