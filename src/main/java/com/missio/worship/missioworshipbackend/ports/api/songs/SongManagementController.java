package com.missio.worship.missioworshipbackend.ports.api.songs;

import com.missio.worship.missioworshipbackend.libs.errors.ForbiddenResponse;
import com.missio.worship.missioworshipbackend.libs.errors.NotFoundResponse;
import com.missio.worship.missioworshipbackend.libs.errors.UnauthorizedResponse;
import com.missio.worship.missioworshipbackend.ports.datastore.entities.Song;
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

@Tag(name = "Controlador de canciones", description = "Endpoints para interactuar con canciones del sistema")
public interface SongManagementController {

    @GetMapping("rithms")
    @Operation(summary = "Obtener posibles ritmos.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información obtenida correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Song.class))
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
    Mono<ResponseEntity<Object>> getRithms(@RequestHeader(value = "Authorization", required = false) String bearerToken);

    @GetMapping()
    @Operation(summary = "Obtener todas las canciones de forma paginada.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información obtenida correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Song.class))
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
    Mono<ResponseEntity<Object>> getAllSongs(
            @Parameter(description = "Cantidad de elementos por página. Minimo 0") Integer limit,
            @Parameter(description = "Cantidad de elementos a omitir. Se calcula como offset_anterior " +
                    "+ limit. Debe ser múltiplo de limit") Integer offset,
            @Parameter(description = "Filtro de canciones activas, permite ver todas, solo activas o solo propuestas. " +
                    "El valor puede ser all, active, unactive. Por defecto es all") String activeFilter,
            @Parameter(description = "Término de búsqueda que hace match con canción")  String search,
            @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @GetMapping("{id}")
    @Operation(summary = "Obtener información de una canción.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información obtenida correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Song.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado la canción solicitada",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotFoundResponse.class))
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
    Mono<ResponseEntity<Object>> getSong(@PathVariable Integer id,
                                                @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @DeleteMapping("{id}")
    @Operation(summary = "Eliminar una canción.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información eliminada correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Song.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado la canción solicitada",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotFoundResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Sólo un admin puede borrar una canción",
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
    Mono<ResponseEntity<Object>> deleteSong(@PathVariable Integer id,
                                                   @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @PutMapping("{id}")
    @Operation(summary = "Actualizar información de una canción.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información modificada correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Song.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se ha encontrado la canción solicitada",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotFoundResponse.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Sólo un admin puede editar una canción",
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
    Mono<ResponseEntity<Object>> updateSong(@PathVariable Integer id,
                                                   @RequestBody SongInput song,
                                                   @RequestHeader(value = "Authorization", required = false) String bearerToken);

    @PostMapping()
    @Operation(summary = "Crear una canción nueva")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información creada correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Song.class))
                            }),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Se ha intentado crear una canción con valores que solo puede " +
                                    "setear un administrador. Faltan permisos para continuar",
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
    Mono<ResponseEntity<Object>> createSong(@RequestBody SongInput song,
                                                   @RequestHeader(value = "Authorization", required = false) String bearerToken);
}
