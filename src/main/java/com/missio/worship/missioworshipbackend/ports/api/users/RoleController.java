package com.missio.worship.missioworshipbackend.ports.api.users;

import com.missio.worship.missioworshipbackend.libs.errors.BadRequestResponse;
import com.missio.worship.missioworshipbackend.libs.errors.ForbiddenResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

@Tag(name = "Controlador de Roles", description = "CRUD sencillo para la gestión de roles")
public interface RoleController {

    @PostMapping
    @Operation(summary = "Crear nuevo usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Información obtenida correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = User.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "El nivel de autorización no es adecuado. Solo un admin puede hacer esto.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ForbiddenResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No se ha enviado el token de sesión o ha caducado",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UnauthorizedResponse.class))
                            })
            })
    Mono<ResponseEntity<Object>> listRoles(@RequestHeader(value = "Authorization", required = false) String bearerToken);

    @PostMapping
    @Operation(summary = "Crear nuevo rol")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información creada correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = User.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "El nivel de autorización no es adecuado. Solo un admin puede hacer esto.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ForbiddenResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No se ha enviado el token de sesión o ha caducado",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UnauthorizedResponse.class))
                            })
            })
    Mono<ResponseEntity<Object>> createRole(String name, @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @DeleteMapping("{id}")
    @Operation(summary = "Crear nuevo usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información eliminada correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = User.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "El nivel de autorización no es adecuado. Solo un admin puede hacer esto.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ForbiddenResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No se ha enviado el token de sesión o ha caducado",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UnauthorizedResponse.class))
                            })
            })
    Mono<ResponseEntity<Object>> deleteRole(Integer id, @RequestHeader(value = "Authorization", required = false) String bearerToken);
}
