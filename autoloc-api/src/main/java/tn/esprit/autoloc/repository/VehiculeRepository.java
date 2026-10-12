package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.time.LocalDate;
import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    boolean existsByImmatriculation(String immatriculation);

    /** Véhicules disponibles sur la période, filtrés (optionnellement) par ville et catégorie. */
    @Query("""
            select v from Vehicule v
            where v.statut <> tn.esprit.autoloc.domain.StatutVehicule.MAINTENANCE
              and (:ville is null or lower(v.agence.ville) = lower(:ville))
              and (:categorie is null or v.categorie = :categorie)
              and not exists (
                    select r from Reservation r
                    where r.vehicule = v
                      and r.statut in (tn.esprit.autoloc.domain.StatutReservation.EN_ATTENTE,
                                       tn.esprit.autoloc.domain.StatutReservation.CONFIRMEE)
                      and r.dateDebut <= :fin and r.dateFin >= :debut)
            """)
    List<Vehicule> rechercherDisponibles(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin,
                                         @Param("ville") String ville,
                                         @Param("categorie") CategorieVehicule categorie);
}
