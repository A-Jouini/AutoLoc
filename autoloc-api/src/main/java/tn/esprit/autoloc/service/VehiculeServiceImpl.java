package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.exception.RegleMetierException;
import tn.esprit.autoloc.exception.RessourceIntrouvableException;
import tn.esprit.autoloc.repository.*;
import tn.esprit.autoloc.web.dto.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final EquipementRepository equipementRepository;
    private final AutolocMapper mapper;

    @Override
    public VehiculeDTO creer(VehiculeDTO dto) {
        if (vehiculeRepository.existsByImmatriculation(dto.immatriculation())) {
            throw new RegleMetierException("Un véhicule existe déjà avec l'immatriculation " + dto.immatriculation());
        }
        Vehicule v = remplir(new Vehicule(), dto);
        v.setStatut(dto.statut() != null ? dto.statut() : StatutVehicule.DISPONIBLE);
        return mapper.toDTO(vehiculeRepository.save(v));
    }

    @Override
    public VehiculeDTO modifier(Long id, VehiculeDTO dto) {
        Vehicule v = chercher(id);
        remplir(v, dto);
        if (dto.statut() != null) {
            v.setStatut(dto.statut());
        }
        return mapper.toDTO(v);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> rechercherDisponibles(LocalDate debut, LocalDate fin, String ville,
                                                   CategorieVehicule categorie) {
        if (fin.isBefore(debut)) {
            throw new IllegalArgumentException("La date de fin doit être postérieure ou égale à la date de début");
        }
        return vehiculeRepository.rechercherDisponibles(debut, fin, ville, categorie)
                .stream().map(mapper::toDTO).toList();
    }

    private Vehicule remplir(Vehicule v, VehiculeDTO dto) {
        v.setImmatriculation(dto.immatriculation());
        v.setMarque(dto.marque());
        v.setModele(dto.modele());
        v.setCategorie(dto.categorie());
        v.setTarifJournalier(dto.tarifJournalier());
        v.setAgence(dto.agenceId() == null ? null : agenceRepository.findById(dto.agenceId())
                .orElseThrow(() -> new RessourceIntrouvableException("Agence", dto.agenceId())));
        if (dto.equipementIds() != null) {
            v.setEquipements(dto.equipementIds().stream()
                    .map(eid -> equipementRepository.findById(eid)
                            .orElseThrow(() -> new RessourceIntrouvableException("Equipement", eid)))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
        return v;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> lister() {
        return vehiculeRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO trouverParId(Long id) {
        return mapper.toDTO(chercher(id));
    }

    @Override
    public void supprimer(Long id) {
        vehiculeRepository.delete(chercher(id));
    }

    private Vehicule chercher(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Vehicule", id));
    }
}
