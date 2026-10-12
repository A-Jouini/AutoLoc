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
public class EmployeServiceImpl implements IEmployeService {

    private final EmployeRepository employeRepository;
    private final AgenceRepository agenceRepository;
    private final AutolocMapper mapper;

    @Override
    public EmployeDTO creer(EmployeDTO dto) {
        return mapper.toDTO(employeRepository.save(remplir(new Employe(), dto)));
    }

    @Override
    public EmployeDTO modifier(Long id, EmployeDTO dto) {
        return mapper.toDTO(remplir(chercher(id), dto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeDTO> listerParAgence(Long idAgence) {
        return employeRepository.findByAgenceIdAgence(idAgence).stream().map(mapper::toDTO).toList();
    }

    private Employe remplir(Employe e, EmployeDTO dto) {
        e.setNom(dto.nom());
        e.setPrenom(dto.prenom());
        e.setRole(dto.role());
        e.setAgence(dto.agenceId() == null ? null : agenceRepository.findById(dto.agenceId())
                .orElseThrow(() -> new RessourceIntrouvableException("Agence", dto.agenceId())));
        return e;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeDTO> lister() {
        return employeRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeDTO trouverParId(Long id) {
        return mapper.toDTO(chercher(id));
    }

    @Override
    public void supprimer(Long id) {
        employeRepository.delete(chercher(id));
    }

    private Employe chercher(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Employe", id));
    }
}
