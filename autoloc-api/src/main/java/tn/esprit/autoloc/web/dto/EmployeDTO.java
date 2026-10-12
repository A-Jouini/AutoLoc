package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import tn.esprit.autoloc.domain.*;

public record EmployeDTO(
        Long id,
        @NotBlank String nom,
        @NotBlank String prenom,
        @NotNull RoleEmploye role,
        Long agenceId) { }
