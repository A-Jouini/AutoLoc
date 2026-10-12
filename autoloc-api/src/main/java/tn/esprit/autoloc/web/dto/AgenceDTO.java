package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import tn.esprit.autoloc.domain.*;

public record AgenceDTO(
        Long id,
        @NotBlank String nom,
        @NotBlank String ville,
        @NotBlank String adresse,
        String telephone) { }
