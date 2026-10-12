package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.exception.RegleMetierException;
import tn.esprit.autoloc.exception.RessourceIntrouvableException;
import tn.esprit.autoloc.repository.*;
import tn.esprit.autoloc.web.dto.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {

    private final EquipementRepository equipementRepository;
    private final AutolocMapper mapper;

    @Override
    public EquipementDTO creer(EquipementDTO dto) {
        Equipement e = new Equipement();
        e.setLibelle(dto.libelle());
        return mapper.toDTO(equipementRepository.save(e));
    }

    @Override
    public EquipementDTO modifier(Long id, EquipementDTO dto) {
        Equipement e = chercher(id);
        e.setLibelle(dto.libelle());
        return mapper.toDTO(e);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipementDTO> lister() {
        return equipementRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EquipementDTO trouverParId(Long id) {
        return mapper.toDTO(chercher(id));
    }

    @Override
    public void supprimer(Long id) {
        equipementRepository.delete(chercher(id));
    }

    private Equipement chercher(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Equipement", id));
    }
}
