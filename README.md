# AutoLoc

**Plateforme de gestion de location de véhicules multi-agences**

Projet réalisé dans le cadre de l'UP ASI — Architecture des Systèmes d'Information (ESPRIT).

## Objectifs du projet

AutoLoc a pour but de centraliser et d'automatiser la gestion de la location de véhicules au sein d'un réseau d'agences :

- Gérer le parc de véhicules de chaque agence (ajout, disponibilité, entretien, retrait).
- Permettre aux clients de rechercher un véhicule disponible et d'effectuer une réservation.
- Suivre le cycle de vie d'une location : réservation, prise en charge, restitution, facturation.
- Offrir aux responsables une vision consolidée de l'activité de leur agence (taux d'occupation, chiffre d'affaires).
- Administrer la plateforme : agences, utilisateurs, rôles et paramètres globaux.

## Acteurs identifiés

| Acteur | Description |
|---|---|
| **Client** | Personne qui loue un véhicule. Consulte le catalogue, réserve, suit et annule ses réservations. |
| **Agent d'agence** | Employé d'une agence. Gère les réservations, la remise et la restitution des véhicules, l'état des lieux. |
| **Responsable d'agence (Manager)** | Supervise une agence : gestion du parc, des agents, des tarifs et suivi des indicateurs. |
| **Administrateur** | Gère l'ensemble de la plateforme : agences, comptes utilisateurs, rôles et configuration. |

## Cas d'utilisation (première version)

### Client
- Créer un compte / s'authentifier
- Rechercher un véhicule disponible (agence, dates, catégorie)
- Effectuer une réservation
- Consulter, modifier ou annuler ses réservations
- Consulter l'historique de ses locations et factures

### Agent d'agence
- Consulter et valider les réservations de son agence
- Enregistrer la remise d'un véhicule (contrat, état des lieux de départ)
- Enregistrer la restitution d'un véhicule (kilométrage, carburant, dommages)
- Mettre à jour la disponibilité d'un véhicule

### Responsable d'agence
- Gérer le parc de véhicules de l'agence (CRUD)
- Gérer les agents de l'agence
- Définir les tarifs et promotions
- Planifier les entretiens des véhicules
- Consulter les statistiques de l'agence

### Administrateur
- Gérer les agences (CRUD)
- Gérer les utilisateurs et leurs rôles
- Consulter les statistiques globales de la plateforme
- Paramétrer la plateforme

## Stack technique

| Catégorie | Outils / Bibliothèques |
|---|---|
| Langage / Build | Java 17+, Maven |
| Framework | Spring Boot, Spring Data JPA, Spring MVC, Spring AOP, Spring Scheduler |
| Base de données | MySQL 8 (développement), H2 en mémoire (tests) |
| Productivité | Lombok, SLF4J/Logback, MapStruct (optionnel) |
| Documentation API | springdoc-openapi (Swagger UI) |
| Tests | JUnit 5, Mockito, MockMvc, Jacoco |
| Qualité | SonarLint, SonarQube (optionnel) |
| Outillage | Git/GitHub, Postman, IntelliJ IDEA Ultimate |

## Environnement de développement (Atelier 0)

- [x] JDK 17 installé et vérifié (`java -version`)
- [x] IntelliJ IDEA Ultimate installé, licence étudiante activée
- [ ] MySQL installé, service démarré, base `autoloc_db` créée
- [x] Postman installé
- [x] Dépôt Git « AutoLoc » initialisé
- [x] README v0 rédigé
- [ ] Capture d'écran de l'environnement (`docs/environnement.png`)
