package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import tn.esprit.autoloc.domain.*;

public record EquipementDTO(
        Long id,
        @NotBlank String libelle) { }
