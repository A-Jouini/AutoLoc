package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.EquipementDTO;

import java.util.List;

public interface IEquipementService {

    List<EquipementDTO> lister();

    EquipementDTO trouverParId(Long id);

    EquipementDTO creer(EquipementDTO dto);

    EquipementDTO modifier(Long id, EquipementDTO dto);

    void supprimer(Long id);
}
