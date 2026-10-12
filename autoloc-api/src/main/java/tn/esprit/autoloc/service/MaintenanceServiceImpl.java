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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehiculeRepository vehiculeRepository;
    private final AutolocMapper mapper;

    /** Enregistre une maintenance ; sans date de fin, le véhicule passe en MAINTENANCE. */
    @Override
    public MaintenanceDTO creer(MaintenanceDTO dto) {
        verifierDates(dto);
        Vehicule v = vehiculeRepository.findById(dto.vehiculeId())
                .orElseThrow(() -> new RessourceIntrouvableException("Vehicule", dto.vehiculeId()));
        if (v.getStatut() == StatutVehicule.LOUE) {
            throw new RegleMetierException("Le véhicule est actuellement loué");
        }
        Maintenance m = new Maintenance();
        m.setVehicule(v);
        m.setDateDebut(dto.dateDebut());
        m.setDateFin(dto.dateFin());
        m.setDescription(dto.description());
        if (dto.dateFin() == null) {
            v.setStatut(StatutVehicule.MAINTENANCE);
        }
        return mapper.toDTO(maintenanceRepository.save(m));
    }

    @Override
    public MaintenanceDTO modifier(Long id, MaintenanceDTO dto) {
        verifierDates(dto);
        Maintenance m = chercher(id);
        m.setDateDebut(dto.dateDebut());
        m.setDateFin(dto.dateFin());
        m.setDescription(dto.description());
        return mapper.toDTO(m);
    }

    /** Clôture la maintenance (date de fin = aujourd'hui) et remet le véhicule à DISPONIBLE. */
    @Override
    public MaintenanceDTO terminer(Long id) {
        Maintenance m = chercher(id);
        if (m.getDateFin() != null) {
            throw new RegleMetierException("Cette maintenance est déjà terminée");
        }
        m.setDateFin(LocalDate.now());
        m.getVehicule().setStatut(StatutVehicule.DISPONIBLE);
        return mapper.toDTO(m);
    }

    private void verifierDates(MaintenanceDTO dto) {
        if (dto.dateFin() != null && dto.dateFin().isBefore(dto.dateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être postérieure ou égale à la date de début");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDTO> lister() {
        return maintenanceRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceDTO trouverParId(Long id) {
        return mapper.toDTO(chercher(id));
    }

    @Override
    public void supprimer(Long id) {
        maintenanceRepository.delete(chercher(id));
    }

    private Maintenance chercher(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Maintenance", id));
    }
}
