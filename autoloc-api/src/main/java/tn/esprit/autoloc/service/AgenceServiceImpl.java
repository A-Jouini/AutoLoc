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
public class AgenceServiceImpl implements IAgenceService {

    private final AgenceRepository agenceRepository;
    private final AutolocMapper mapper;

    @Override
    public AgenceDTO creer(AgenceDTO dto) {
        return mapper.toDTO(agenceRepository.save(remplir(new Agence(), dto)));
    }

    @Override
    public AgenceDTO modifier(Long id, AgenceDTO dto) {
        return mapper.toDTO(remplir(chercher(id), dto));
    }

    private Agence remplir(Agence a, AgenceDTO dto) {
        a.setNom(dto.nom());
        a.setVille(dto.ville());
        a.setAdresse(dto.adresse());
        a.setTelephone(dto.telephone());
        return a;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> lister() {
        return agenceRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceDTO trouverParId(Long id) {
        return mapper.toDTO(chercher(id));
    }

    @Override
    public void supprimer(Long id) {
        agenceRepository.delete(chercher(id));
    }

    private Agence chercher(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Agence", id));
    }
}
