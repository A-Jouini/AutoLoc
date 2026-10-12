package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.*;

public record MaintenanceDTO(
        Long id,
        @NotNull LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        @NotNull Long vehiculeId) { }
