package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.domain.CategorieVehicule;
import java.time.LocalDate;

import java.util.List;

public interface IVehiculeService {

    List<VehiculeDTO> lister();

    VehiculeDTO trouverParId(Long id);

    VehiculeDTO creer(VehiculeDTO dto);

    VehiculeDTO modifier(Long id, VehiculeDTO dto);

    void supprimer(Long id);

    List<VehiculeDTO> rechercherDisponibles(LocalDate debut, LocalDate fin, String ville, CategorieVehicule categorie);
}
