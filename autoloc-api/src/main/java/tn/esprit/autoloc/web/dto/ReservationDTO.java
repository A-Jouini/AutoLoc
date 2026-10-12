package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.*;

public record ReservationDTO(
        Long id,
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin,
        StatutReservation statut,
        @NotNull Long clientId,
        @NotNull Long vehiculeId) { }
