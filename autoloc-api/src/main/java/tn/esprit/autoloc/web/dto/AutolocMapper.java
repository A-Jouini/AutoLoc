package tn.esprit.autoloc.web.dto;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.*;

/** Conversion entité -> DTO (le sens inverse est géré par les services, qui résolvent les associations). */
@Component
public class AutolocMapper {

    public AgenceDTO toDTO(Agence a) {
        return new AgenceDTO(a.getIdAgence(), a.getNom(), a.getVille(), a.getAdresse(), a.getTelephone());
    }

    public EquipementDTO toDTO(Equipement e) {
        return new EquipementDTO(e.getIdEquipement(), e.getLibelle());
    }

    public ClientDTO toDTO(Client c) {
        return new ClientDTO(c.getIdClient(), c.getNom(), c.getPrenom(), c.getEmail(),
                c.getTelephone(), c.getNumPermis(), c.getDateInscription());
    }

    public EmployeDTO toDTO(Employe e) {
        return new EmployeDTO(e.getIdEmploye(), e.getNom(), e.getPrenom(), e.getRole(),
                e.getAgence() == null ? null : e.getAgence().getIdAgence());
    }

    public VehiculeDTO toDTO(Vehicule v) {
        return new VehiculeDTO(v.getIdVehicule(), v.getImmatriculation(), v.getMarque(), v.getModele(),
                v.getCategorie(), v.getTarifJournalier(), v.getStatut(),
                v.getAgence() == null ? null : v.getAgence().getIdAgence(),
                v.getEquipements().stream().map(Equipement::getIdEquipement).toList());
    }

    public ReservationDTO toDTO(Reservation r) {
        return new ReservationDTO(r.getIdReservation(), r.getDateDebut(), r.getDateFin(), r.getStatut(),
                r.getClient().getIdClient(), r.getVehicule().getIdVehicule());
    }

    public ContratDTO toDTO(Contrat c) {
        return new ContratDTO(c.getIdContrat(), c.getDateSignature(), c.getMontantTotal(), c.isValide(),
                c.getReservation().getIdReservation());
    }

    public PaiementDTO toDTO(Paiement p) {
        return new PaiementDTO(p.getIdPaiement(), p.getMontant(), p.getDatePaiement(), p.getModePaiement(),
                p.getContrat().getIdContrat());
    }

    public MaintenanceDTO toDTO(Maintenance m) {
        return new MaintenanceDTO(m.getIdMaintenance(), m.getDateDebut(), m.getDateFin(), m.getDescription(),
                m.getVehicule().getIdVehicule());
    }
}
