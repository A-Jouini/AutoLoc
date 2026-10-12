package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ClientDTO;

import java.util.List;

public interface IClientService {

    List<ClientDTO> lister();

    ClientDTO trouverParId(Long id);

    ClientDTO creer(ClientDTO dto);

    ClientDTO modifier(Long id, ClientDTO dto);

    void supprimer(Long id);
}
