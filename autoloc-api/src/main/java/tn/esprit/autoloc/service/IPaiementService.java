package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.PaiementDTO;

import java.util.List;

public interface IPaiementService {

    List<PaiementDTO> lister();

    PaiementDTO trouverParId(Long id);

    PaiementDTO creer(PaiementDTO dto);

    PaiementDTO modifier(Long id, PaiementDTO dto);

    void supprimer(Long id);

    List<PaiementDTO> listerParContrat(Long idContrat);
}
