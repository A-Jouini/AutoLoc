package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.AgenceDTO;

import java.util.List;

public interface IAgenceService {

    List<AgenceDTO> lister();

    AgenceDTO trouverParId(Long id);

    AgenceDTO creer(AgenceDTO dto);

    AgenceDTO modifier(Long id, AgenceDTO dto);

    void supprimer(Long id);
}
