package com.missio.worship.missioworshipbackend.libs.authentication.errors;


public class NotAdminException extends Exception{
    public NotAdminException(String msg){
        super(msg);
    }

    public NotAdminException() {
        super("El token introducido no es válido pero el usuario carece de autorización para realizar esta acción");
    }
}
