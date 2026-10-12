package tn.esprit.autoloc.service;

import tn.esprit.autoloc.web.dto.ReservationDTO;

import java.util.List;

public interface IReservationService {

    List<ReservationDTO> lister();

    ReservationDTO trouverParId(Long id);

    ReservationDTO creer(ReservationDTO dto);

    ReservationDTO modifier(Long id, ReservationDTO dto);

    void supprimer(Long id);

    ReservationDTO confirmer(Long id);

    ReservationDTO annuler(Long id);

    List<ReservationDTO> listerParClient(Long idClient);
}
