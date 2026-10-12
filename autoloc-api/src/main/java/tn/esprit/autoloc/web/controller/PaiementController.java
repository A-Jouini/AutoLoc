package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IPaiementService;
import tn.esprit.autoloc.web.dto.PaiementDTO;

import java.util.List;

@Tag(name = "Paiements", description = "Enregistrement des paiements d'un contrat")
@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @Operation(summary = "Lister")
    @GetMapping
    public List<PaiementDTO> lister() {
        return paiementService.lister();
    }

    @Operation(summary = "Obtenir par identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    @GetMapping("/{id}")
    public PaiementDTO obtenir(@PathVariable Long id) {
        return paiementService.trouverParId(id);
    }

    @Operation(summary = "Créer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaiementDTO creer(@Valid @RequestBody PaiementDTO dto) {
        return paiementService.creer(dto);
    }

    @Operation(summary = "Modifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Introuvable"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PutMapping("/{id}")
    public PaiementDTO modifier(@PathVariable Long id, @Valid @RequestBody PaiementDTO dto) {
        return paiementService.modifier(id, dto);
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
        paiementService.supprimer(id);
    }

    @Operation(summary = "Lister les paiements d'un contrat")
    @GetMapping("/contrat/{idContrat}")
    public List<PaiementDTO> listerParContrat(@PathVariable Long idContrat) {
        return paiementService.listerParContrat(idContrat);
    }
}
