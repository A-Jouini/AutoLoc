package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.web.dto.EmployeDTO;

import java.util.List;

@Tag(name = "Employés", description = "Gestion des employés")
@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @Operation(summary = "Lister")
    @GetMapping
    public List<EmployeDTO> lister() {
        return employeService.lister();
    }

    @Operation(summary = "Obtenir par identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    @GetMapping("/{id}")
    public EmployeDTO obtenir(@PathVariable Long id) {
        return employeService.trouverParId(id);
    }

    @Operation(summary = "Créer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeDTO creer(@Valid @RequestBody EmployeDTO dto) {
        return employeService.creer(dto);
    }

    @Operation(summary = "Modifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Introuvable"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PutMapping("/{id}")
    public EmployeDTO modifier(@PathVariable Long id, @Valid @RequestBody EmployeDTO dto) {
        return employeService.modifier(id, dto);
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
        employeService.supprimer(id);
    }

    @Operation(summary = "Lister les employés d'une agence")
    @GetMapping("/agence/{idAgence}")
    public List<EmployeDTO> listerParAgence(@PathVariable Long idAgence) {
        return employeService.listerParAgence(idAgence);
    }
}
