package com.missio.worship.missioworshipbackend.ports.api.absences;

import com.missio.worship.missioworshipbackend.libs.errors.BadRequestResponse;
import com.missio.worship.missioworshipbackend.libs.errors.ForbiddenResponse;
import com.missio.worship.missioworshipbackend.libs.errors.NotFoundResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Controlador de auscencias", description = "Control y consulta sobre los días que no están. NOTA! Las fechas se introducen en formato yyyy-MM-dd")
public interface AbsencesController {

    @GetMapping()
    @Operation(summary = "Devuelve el listado de ausencias de un usuario entre dos fechas. Si no contiene el id del usuario usa el del token.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información obtenida correctamente"),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No se ha enviado el token de sesión o ha caducado",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UnauthorizedResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "El usuario no existe en el sistema",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotFoundResponse.class))
                            })
            })
    Mono<ResponseEntity<Object>> getAbsencesPerDate(@RequestParam(required = false) Integer userId,
                                                    @RequestParam String begin,
                                                    @RequestParam String end,
                                                    @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @PostMapping("absent")
    @Operation(summary = "Declara la no asistencia de un usuario para una fecha. Si no contiene id de usuario usa el del token. Si incluye id de usuario, el token debe ser de administrador.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información registrada correctamente"),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No se ha enviado el token de sesión o ha caducado",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UnauthorizedResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Solo un admin puede cambiar asistencia de alguien que no sea él mismo.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ForbiddenResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "El usuario a marcar no existe en el sistema",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotFoundResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error en el formato. Verifique que la fecha tiene un formato adecuado dd/MM/YYYY y que se incluyen todos los campos.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = BadRequestResponse.class)
                                    )
                            }
                    )
            })
    Mono<ResponseEntity<Object>> willBeAbsent(@RequestBody AbsenceBodyInput input, @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @PostMapping("attending")
    @Operation(summary = "Idem que willBeAbsent pero para deshacer la declaración de no asistencia. Si no se ha marcado que no viene, no hace nada.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información registrada correctamente"),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No se ha enviado el token de sesión o ha caducado",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UnauthorizedResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Solo un admin puede cambiar asistencia de alguien que no sea él mismo.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ForbiddenResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "El usuario a marcar no existe en el sistema",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotFoundResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error en el formato. Verifique que la fecha tiene un formato adecuado dd/MM/YYYY y que se incluyen todos los campos.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = BadRequestResponse.class)
                                    )
                            }
                    )
            })
    Mono<ResponseEntity<Object>> willNotBeAbsent(@RequestBody AbsenceBodyInput input, @RequestHeader(value = "Authorization", required = false) String bearerToken);


    @GetMapping("all")
    @Operation(summary = "Devuelve los usuarios que asistirán en una fecha determinada.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información obtenida correctamente"),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No se ha enviado el token de sesión o ha caducado",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UnauthorizedResponse.class))
                            }),
            })
    Mono<ResponseEntity<Object>> getUsersAbsentInDate(@RequestParam String date,
                                                      @RequestHeader(value = "Authorization", required = false) String bearerToken);
}
