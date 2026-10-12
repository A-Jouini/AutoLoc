package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.*;

public record ContratDTO(
        Long id,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        Boolean valide,
        @NotNull Long reservationId) { }
