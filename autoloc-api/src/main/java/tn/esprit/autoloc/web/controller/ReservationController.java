package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationDTO;

import java.util.List;

@Tag(name = "Réservations", description = "Création, confirmation et annulation des réservations")
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    @Operation(summary = "Lister")
    @GetMapping
    public List<ReservationDTO> lister() {
        return reservationService.lister();
    }

    @Operation(summary = "Obtenir par identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trouvé"),
            @ApiResponse(responseCode = "404", description = "Introuvable")
    })
    @GetMapping("/{id}")
    public ReservationDTO obtenir(@PathVariable Long id) {
        return reservationService.trouverParId(id);
    }

    @Operation(summary = "Créer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO creer(@Valid @RequestBody ReservationDTO dto) {
        return reservationService.creer(dto);
    }

    @Operation(summary = "Modifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modifié"),
            @ApiResponse(responseCode = "404", description = "Introuvable"),
            @ApiResponse(responseCode = "409", description = "Règle métier ou contrainte violée")
    })
    @PutMapping("/{id}")
    public ReservationDTO modifier(@PathVariable Long id, @Valid @RequestBody ReservationDTO dto) {
        return reservationService.modifier(id, dto);
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
        reservationService.supprimer(id);
    }

    @Operation(summary = "Confirmer une réservation en attente")
    @PutMapping("/{id}/confirmer")
    public ReservationDTO confirmer(@PathVariable Long id) {
        return reservationService.confirmer(id);
    }

    @Operation(summary = "Annuler une réservation")
    @PutMapping("/{id}/annuler")
    public ReservationDTO annuler(@PathVariable Long id) {
        return reservationService.annuler(id);
    }

    @Operation(summary = "Historique des réservations d'un client")
    @GetMapping("/client/{idClient}")
    public List<ReservationDTO> listerParClient(@PathVariable Long idClient) {
        return reservationService.listerParClient(idClient);
    }
}
