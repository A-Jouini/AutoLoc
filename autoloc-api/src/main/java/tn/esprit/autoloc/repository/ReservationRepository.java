package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClientIdClient(Long idClient);

    /** Nombre de réservations actives du véhicule qui chevauchent la période (hors réservation exclue). */
    @Query("""
            select count(r) from Reservation r
            where r.vehicule.idVehicule = :idVehicule
              and r.statut in (tn.esprit.autoloc.domain.StatutReservation.EN_ATTENTE,
                               tn.esprit.autoloc.domain.StatutReservation.CONFIRMEE)
              and r.dateDebut <= :fin and r.dateFin >= :debut
              and (:exclu is null or r.idReservation <> :exclu)
            """)
    long compterChevauchements(@Param("idVehicule") Long idVehicule, @Param("debut") LocalDate debut,
                               @Param("fin") LocalDate fin, @Param("exclu") Long exclu);
}
