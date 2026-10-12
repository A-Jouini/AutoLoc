package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientDTO;

import java.util.List;

@Tag(name = "Clients", description = "Gestion des clients")
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    @Operation(summary = "Lister")
    @GetMapping
    public List<ClientDTO> lister() {
        return clientService.lister();
    }

    @Operation(summary = "Obtenir par identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    @GetMapping("/{id}")
    public ClientDTO obtenir(@PathVariable Long id) {
        return clientService.trouverParId(id);
    }

    @Operation(summary = "Créer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO creer(@Valid @RequestBody ClientDTO dto) {
        return clientService.creer(dto);
    }

    @Operation(summary = "Modifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Introuvable"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PutMapping("/{id}")
    public ClientDTO modifier(@PathVariable Long id, @Valid @RequestBody ClientDTO dto) {
        return clientService.modifier(id, dto);
    }

    @Operation(summary = "Supprimer")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Introuvable"),
            @ApiResponse(responseCode = "409", description = "Ressource liée")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable Long id) {
        clientService.supprimer(id);
    }
}
