package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import tn.esprit.autoloc.domain.*;

public record PaiementDTO(
        Long id,
        @NotNull @Positive BigDecimal montant,
        LocalDateTime datePaiement,
        @NotNull ModePaiement modePaiement,
        @NotNull Long contratId) { }
