package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.exception.RegleMetierException;
import tn.esprit.autoloc.exception.RessourceIntrouvableException;
import tn.esprit.autoloc.repository.*;
import tn.esprit.autoloc.web.dto.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {

    private final ContratRepository contratRepository;
    private final ReservationRepository reservationRepository;
    private final AutolocMapper mapper;

    /** Génère le contrat d'une réservation confirmée : montant = tarif journalier x nombre de jours. */
    @Override
    public ContratDTO creer(ContratDTO dto) {
        Reservation r = reservationRepository.findById(dto.reservationId())
                .orElseThrow(() -> new RessourceIntrouvableException("Reservation", dto.reservationId()));
        if (r.getStatut() != StatutReservation.CONFIRMEE) {
            throw new RegleMetierException("Un contrat ne peut être généré que pour une réservation confirmée");
        }
        if (r.getContrat() != null) {
            throw new RegleMetierException("Cette réservation a déjà un contrat");
        }
        long jours = ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()) + 1;

        Contrat c = new Contrat();
        c.setReservation(r);
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(r.getVehicule().getTarifJournalier().multiply(BigDecimal.valueOf(jours)));
        c.setValide(true);
        r.getVehicule().setStatut(StatutVehicule.LOUE);
        r.setContrat(c);
        return mapper.toDTO(contratRepository.save(c));
    }

    /** Seule la validité du contrat est modifiable (le montant découle de la réservation). */
    @Override
    public ContratDTO modifier(Long id, ContratDTO dto) {
        Contrat c = chercher(id);
        if (dto.valide() != null) {
            c.setValide(dto.valide());
        }
        return mapper.toDTO(c);
    }

    /** Supprime le contrat et, par cascade, ses paiements. */
    @Override
    public void supprimer(Long id) {
        Contrat c = chercher(id);
        c.getReservation().setContrat(null);
        contratRepository.delete(c);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContratDTO> lister() {
        return contratRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ContratDTO trouverParId(Long id) {
        return mapper.toDTO(chercher(id));
    }

    private Contrat chercher(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Contrat", id));
    }
}
