package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ContratDTO;

import java.util.List;

public interface IContratService {

    List<ContratDTO> lister();

    ContratDTO trouverParId(Long id);

    ContratDTO creer(ContratDTO dto);

    ContratDTO modifier(Long id, ContratDTO dto);

    void supprimer(Long id);
}
