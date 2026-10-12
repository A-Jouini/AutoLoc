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
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final AutolocMapper mapper;

    @Override
    public ClientDTO creer(ClientDTO dto) {
        Client c = remplir(new Client(), dto);
        c.setDateInscription(dto.dateInscription() != null ? dto.dateInscription() : LocalDate.now());
        return mapper.toDTO(clientRepository.save(c));
    }

    @Override
    public ClientDTO modifier(Long id, ClientDTO dto) {
        return mapper.toDTO(remplir(chercher(id), dto));
    }

    private Client remplir(Client c, ClientDTO dto) {
        c.setNom(dto.nom());
        c.setPrenom(dto.prenom());
        c.setEmail(dto.email());
        c.setTelephone(dto.telephone());
        c.setNumPermis(dto.numPermis());
        return c;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> lister() {
        return clientRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTO trouverParId(Long id) {
        return mapper.toDTO(chercher(id));
    }

    @Override
    public void supprimer(Long id) {
        clientRepository.delete(chercher(id));
    }

    private Client chercher(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Client", id));
    }
}
