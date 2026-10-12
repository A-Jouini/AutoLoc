package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.*;

public record ClientDTO(
        Long id,
        @NotBlank String nom,
        @NotBlank String prenom,
        @NotBlank @Email String email,
        String telephone,
        @NotBlank String numPermis,
        LocalDate dateInscription) { }
