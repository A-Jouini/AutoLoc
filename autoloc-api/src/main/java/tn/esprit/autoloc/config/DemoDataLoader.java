package tn.esprit.autoloc.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Insère quelques véhicules de démonstration au démarrage (profil "dev").
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "autoloc.demo-data.enabled", havingValue = "true")
public class DemoDataLoader implements CommandLineRunner {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public void run(String... args) {
        List<Vehicule> demo = List.of(
                new Vehicule(null, "123 TU 4567", "Renault", "Clio", CategorieVehicule.CITADINE,
                        new BigDecimal("80.00"), StatutVehicule.DISPONIBLE),
                new Vehicule(null, "234 TU 5678", "Peugeot", "508", CategorieVehicule.BERLINE,
                        new BigDecimal("150.00"), StatutVehicule.DISPONIBLE),
                new Vehicule(null, "345 TU 6789", "Kia", "Sportage", CategorieVehicule.SUV,
                        new BigDecimal("180.00"), StatutVehicule.MAINTENANCE));

        List<Vehicule> nouveaux = demo.stream()
                .filter(v -> !vehiculeRepository.existsByImmatriculation(v.getImmatriculation()))
                .toList();
        vehiculeRepository.saveAll(nouveaux);
        log.info("{} véhicule(s) de démonstration inséré(s)", nouveaux.size());
    }
}
