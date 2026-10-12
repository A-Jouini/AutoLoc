package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.MaintenanceDTO;

import java.util.List;

public interface IMaintenanceService {

    List<MaintenanceDTO> lister();

    MaintenanceDTO trouverParId(Long id);

    MaintenanceDTO creer(MaintenanceDTO dto);

    MaintenanceDTO modifier(Long id, MaintenanceDTO dto);

    void supprimer(Long id);

    MaintenanceDTO terminer(Long id);
}
