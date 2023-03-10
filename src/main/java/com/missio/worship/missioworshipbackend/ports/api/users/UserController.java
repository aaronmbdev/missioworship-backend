package com.missio.worship.missioworshipbackend.ports.api.users;

import com.missio.worship.missioworshipbackend.libs.common.RestPaginationResponse;
import com.missio.worship.missioworshipbackend.libs.errors.BadRequestResponse;
import com.missio.worship.missioworshipbackend.libs.errors.ForbiddenResponse;
import com.missio.worship.missioworshipbackend.libs.errors.NotFoundResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import com.missio.worship.missioworshipbackend.libs.users.UserFullResponse;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Controlador de Usuario", description = "Endpoints para usuario de missio")
public interface UserController {

    @GetMapping("{id}")
    @Operation(summary = "Obtener información de un usuario.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                        responseCode = "200",
                        description = "Información obtenida correctamente",
                        content = {
                                @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = UserFullResponse.class))
                        }),
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
                        description = "El usuario solicitado no existe en el sistema",
                        content = {
                                @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = NotFoundResponse.class))
                        })
            })
    Mono<ResponseEntity<Object>> getUser(@PathVariable Integer id, @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar un usuario. Requiere token de administrador")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información eliminada correctamente"),
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
                            description = "El nivel de autorización no es adecuado. Solo un admin puede hacer esto.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ForbiddenResponse.class))
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "El usuario a eliminar no existe en el sistema",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotFoundResponse.class))
                            })
            })
    Mono<ResponseEntity<Object>> deleteUser(@PathVariable Integer id, @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @GetMapping()
    @Operation(summary = "Obtener todos los usuarios registrados de forma paginada.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información obtenida correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = RestPaginationResponse.class))
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
    Mono<ResponseEntity<Object>> getAllUsers(
            @Parameter(description = "Cantidad de elementos por página. Minimo 0") Integer limit,
            @Parameter(description = "Cantidad de elementos a omitir. Se calcula como offset_anterior + limit. Debe ser múltiplo de limit") Integer offset,
            @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @PutMapping("{id}")
    @Operation(summary = "Actualizar datos de usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información obtenida correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = User.class))
                            }),
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
                            description = "El nivel de autorización no es adecuado. Solo un admin puede hacer esto.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ForbiddenResponse.class))
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "El usuario solicitado no existe en el sistema",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotFoundResponse.class))
                            })
            })
    Mono<ResponseEntity<Object>> updateUser(@PathVariable Integer id, @RequestBody UserCreate user, @RequestHeader(value = "Authorization", required = false) String bearerToken);

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
                            responseCode = "400",
                            description = "Error en el formato. Verifique que la fecha tiene un formato adecuado dd/MM/YYYY y que se incluyen todos los campos.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = BadRequestResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No se ha enviado el token de sesión o ha caducado",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UnauthorizedResponse.class))
                            })
            })
    Mono<ResponseEntity<Object>> createUser(@RequestBody UserCreate user, @RequestHeader(value = "Authorization", required = false) String bearerToken);
}
