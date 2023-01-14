package com.missio.worship.missioworshipbackend.libs.users.errors;

public class EmailAlreadyRegisteredException extends CannotCreateUserException {
    public EmailAlreadyRegisteredException(String email) {
        super("El correo electrónico " + email + " ya existe en el sistema");
    }
}
