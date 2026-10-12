package tn.esprit.autoloc.exception;

/** Violation d'une règle métier (ex. véhicule déjà réservé sur la période). */
public class RegleMetierException extends RuntimeException {

    public RegleMetierException(String message) {
        super(message);
    }
}
