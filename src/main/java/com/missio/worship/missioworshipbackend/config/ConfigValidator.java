package com.missio.worship.missioworshipbackend.config;

import com.missio.worship.missioworshipbackend.libs.errors.BootingError;
import org.springframework.util.StringUtils;

public class ConfigValidator {
    public static void configExistsValidator(String config) throws BootingError {
        if(!StringUtils.hasText(config)) {
            throw new BootingError("No se ha encontrado la clave secreta de Recaptcha. No se puede ejecutar.");
        }
    }
}
