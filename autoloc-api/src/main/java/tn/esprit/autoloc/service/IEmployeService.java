package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.EmployeDTO;

import java.util.List;

public interface IEmployeService {

    List<EmployeDTO> lister();

    EmployeDTO trouverParId(Long id);

    EmployeDTO creer(EmployeDTO dto);

    EmployeDTO modifier(Long id, EmployeDTO dto);

    void supprimer(Long id);

    List<EmployeDTO> listerParAgence(Long idAgence);
}
