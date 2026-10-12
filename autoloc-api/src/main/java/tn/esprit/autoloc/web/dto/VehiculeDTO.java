package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autoloc.domain.*;

public record VehiculeDTO(
        Long id,
        @NotBlank String immatriculation,
        @NotBlank String marque,
        @NotBlank String modele,
        @NotNull CategorieVehicule categorie,
        @NotNull @Positive BigDecimal tarifJournalier,
        StatutVehicule statut,
        Long agenceId,
        List<Long> equipementIds) { }
