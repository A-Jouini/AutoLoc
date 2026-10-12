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
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final VehiculeRepository vehiculeRepository;
    private final AutolocMapper mapper;

    @Override
    public ReservationDTO creer(ReservationDTO dto) {
        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new RessourceIntrouvableException("Client", dto.clientId()));
        Vehicule vehicule = vehiculeRepository.findById(dto.vehiculeId())
                .orElseThrow(() -> new RessourceIntrouvableException("Vehicule", dto.vehiculeId()));
        verifierDisponibilite(vehicule, dto, null);

        Reservation r = new Reservation();
        r.setClient(client);
        r.setVehicule(vehicule);
        r.setDateDebut(dto.dateDebut());
        r.setDateFin(dto.dateFin());
        r.setStatut(StatutReservation.EN_ATTENTE);
        return mapper.toDTO(reservationRepository.save(r));
    }

    @Override
    public ReservationDTO modifier(Long id, ReservationDTO dto) {
        Reservation r = chercher(id);
        if (r.getStatut() != StatutReservation.EN_ATTENTE) {
            throw new RegleMetierException("Seule une réservation en attente peut être modifiée");
        }
        Vehicule vehicule = vehiculeRepository.findById(dto.vehiculeId())
                .orElseThrow(() -> new RessourceIntrouvableException("Vehicule", dto.vehiculeId()));
        verifierDisponibilite(vehicule, dto, id);
        r.setVehicule(vehicule);
        r.setDateDebut(dto.dateDebut());
        r.setDateFin(dto.dateFin());
        return mapper.toDTO(r);
    }

    @Override
    public ReservationDTO confirmer(Long id) {
        Reservation r = chercher(id);
        if (r.getStatut() != StatutReservation.EN_ATTENTE) {
            throw new RegleMetierException("Seule une réservation en attente peut être confirmée");
        }
        r.setStatut(StatutReservation.CONFIRMEE);
        return mapper.toDTO(r);
    }

    @Override
    public ReservationDTO annuler(Long id) {
        Reservation r = chercher(id);
        if (r.getStatut() == StatutReservation.TERMINEE || r.getStatut() == StatutReservation.ANNULEE) {
            throw new RegleMetierException("Cette réservation ne peut plus être annulée");
        }
        r.setStatut(StatutReservation.ANNULEE);
        return mapper.toDTO(r);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> listerParClient(Long idClient) {
        return reservationRepository.findByClientIdClient(idClient).stream().map(mapper::toDTO).toList();
    }

    private void verifierDisponibilite(Vehicule vehicule, ReservationDTO dto, Long idExclu) {
        if (dto.dateFin().isBefore(dto.dateDebut())) {
            throw new IllegalArgumentException("La date de fin doit être postérieure ou égale à la date de début");
        }
        if (vehicule.getStatut() == StatutVehicule.MAINTENANCE) {
            throw new RegleMetierException("Le véhicule est en maintenance");
        }
        if (reservationRepository.compterChevauchements(vehicule.getIdVehicule(), dto.dateDebut(),
                dto.dateFin(), idExclu) > 0) {
            throw new RegleMetierException("Le véhicule est déjà réservé sur cette période");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> lister() {
        return reservationRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationDTO trouverParId(Long id) {
        return mapper.toDTO(chercher(id));
    }

    @Override
    public void supprimer(Long id) {
        reservationRepository.delete(chercher(id));
    }

    private Reservation chercher(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Reservation", id));
    }
}
