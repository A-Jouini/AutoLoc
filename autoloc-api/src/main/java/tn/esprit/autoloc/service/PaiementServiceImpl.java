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
import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements IPaiementService {

    private final PaiementRepository paiementRepository;
    private final ContratRepository contratRepository;
    private final AutolocMapper mapper;

    @Override
    public PaiementDTO creer(PaiementDTO dto) {
        Contrat contrat = contratRepository.findById(dto.contratId())
                .orElseThrow(() -> new RessourceIntrouvableException("Contrat", dto.contratId()));
        if (!contrat.isValide()) {
            throw new RegleMetierException("Le contrat n'est pas valide");
        }
        BigDecimal dejaPaye = paiementRepository.totalPaye(contrat.getIdContrat());
        if (dejaPaye.add(dto.montant()).compareTo(contrat.getMontantTotal()) > 0) {
            throw new RegleMetierException("Le total des paiements dépasse le montant du contrat");
        }
        Paiement p = new Paiement();
        p.setContrat(contrat);
        p.setMontant(dto.montant());
        p.setModePaiement(dto.modePaiement());
        p.setDatePaiement(LocalDateTime.now());
        return mapper.toDTO(paiementRepository.save(p));
    }

    /** Un paiement enregistré ne peut pas être modifié (traçabilité) : seule la suppression est possible. */
    @Override
    public PaiementDTO modifier(Long id, PaiementDTO dto) {
        throw new RegleMetierException("Un paiement enregistré ne peut pas être modifié");
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDTO> listerParContrat(Long idContrat) {
        return paiementRepository.findByContratIdContrat(idContrat).stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDTO> lister() {
        return paiementRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PaiementDTO trouverParId(Long id) {
        return mapper.toDTO(chercher(id));
    }

    @Override
    public void supprimer(Long id) {
        paiementRepository.delete(chercher(id));
    }

    private Paiement chercher(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Paiement", id));
    }
}
