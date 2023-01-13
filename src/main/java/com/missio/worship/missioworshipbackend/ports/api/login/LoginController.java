package com.missio.worship.missioworshipbackend.ports.api.login;

import com.missio.worship.missioworshipbackend.ports.api.errors.BadRequestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

@Tag(name = "Controlador de inicio de sesión", description = "Endpoints para iniciar sesión y renovar tokens")
public interface LoginController {

    @PostMapping()
    @Operation(summary = "Autentica a un usuario. Recibe un token emitido por Google, lo valida y devuelve uno propio")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Información validada y token emitido correctamente."),
                    @ApiResponse(
                            responseCode = "400",
                            description = "El token recibido no es válido.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = BadRequestResponse.class)
                                    )
                            }
                    )
            })
    Mono<ResponseEntity<String>> loginAttempt();

    @PostMapping("/renew")
    @Operation(summary = "Renueva un token propio siempre que sea válido")
    Mono<ResponseEntity<String>> renewToken();
}
