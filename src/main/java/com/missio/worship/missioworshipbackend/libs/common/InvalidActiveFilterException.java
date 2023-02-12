package com.missio.worship.missioworshipbackend.libs.common;

public class InvalidActiveFilterException extends Exception {
    public InvalidActiveFilterException(String value) {
        super("Valor de filtro inválido. El filtro de activos sólo puede valer all, active o inactive, valor actual; " + value);
    }
}
