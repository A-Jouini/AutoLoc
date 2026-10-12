package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceDTO;

import java.util.List;

@Tag(name = "Maintenances", description = "Suivi des maintenances des véhicules")
@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Lister")
    @GetMapping
    public List<MaintenanceDTO> lister() {
        return maintenanceService.lister();
    }

    @Operation(summary = "Obtenir par identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    @GetMapping("/{id}")
    public MaintenanceDTO obtenir(@PathVariable Long id) {
        return maintenanceService.trouverParId(id);
    }

    @Operation(summary = "Créer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDTO creer(@Valid @RequestBody MaintenanceDTO dto) {
        return maintenanceService.creer(dto);
    }

    @Operation(summary = "Modifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Introuvable"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PutMapping("/{id}")
    public MaintenanceDTO modifier(@PathVariable Long id, @Valid @RequestBody MaintenanceDTO dto) {
        return maintenanceService.modifier(id, dto);
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
        maintenanceService.supprimer(id);
    }

    @Operation(summary = "Terminer une maintenance (le véhicule redevient disponible)")
    @PutMapping("/{id}/terminer")
    public MaintenanceDTO terminer(@PathVariable Long id) {
        return maintenanceService.terminer(id);
    }
}
