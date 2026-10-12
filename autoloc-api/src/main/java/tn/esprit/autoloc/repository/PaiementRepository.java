package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Paiement;

import java.math.BigDecimal;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByContratIdContrat(Long idContrat);

    @Query("select coalesce(sum(p.montant), 0) from Paiement p where p.contrat.idContrat = :idContrat")
    BigDecimal totalPaye(@Param("idContrat") Long idContrat);
}
