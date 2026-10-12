package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import org.springframework.format.annotation.DateTimeFormat;
import tn.esprit.autoloc.domain.CategorieVehicule;
import java.time.LocalDate;

import java.util.List;

@Tag(name = "Véhicules", description = "Gestion de la flotte et recherche de disponibilité")
@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister")
    @GetMapping
    public List<VehiculeDTO> lister() {
        return vehiculeService.lister();
    }

    @Operation(summary = "Obtenir par identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    @GetMapping("/{id}")
    public VehiculeDTO obtenir(@PathVariable Long id) {
        return vehiculeService.trouverParId(id);
    }

    @Operation(summary = "Créer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO creer(@Valid @RequestBody VehiculeDTO dto) {
        return vehiculeService.creer(dto);
    }

    @Operation(summary = "Modifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Introuvable"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PutMapping("/{id}")
    public VehiculeDTO modifier(@PathVariable Long id, @Valid @RequestBody VehiculeDTO dto) {
        return vehiculeService.modifier(id, dto);
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
        vehiculeService.supprimer(id);
    }

    @Operation(summary = "Rechercher les véhicules disponibles sur une période (ville et catégorie facultatives)")
    @GetMapping("/disponibles")
    public List<VehiculeDTO> rechercherDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) CategorieVehicule categorie) {
        return vehiculeService.rechercherDisponibles(debut, fin, ville, categorie);
    }
}
