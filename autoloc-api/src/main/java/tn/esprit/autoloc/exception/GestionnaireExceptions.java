package tn.esprit.autoloc.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GestionnaireExceptions {

    // 404 : ressource introuvable
    @ExceptionHandler(RessourceIntrouvableException.class)
    public ResponseEntity<ErreurReponse> handleIntrouvable(RessourceIntrouvableException ex) {
        return reponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 400 : données invalides (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurReponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return reponse(HttpStatus.BAD_REQUEST, message);
    }

    // 400 : corps de requête illisible ou argument incohérent
    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalArgumentException.class})
    public ResponseEntity<ErreurReponse> handleRequeteInvalide(Exception ex) {
        return reponse(HttpStatus.BAD_REQUEST, ex instanceof IllegalArgumentException
                ? ex.getMessage() : "Corps de la requête invalide");
    }

    // 409 : règle métier violée
    @ExceptionHandler(RegleMetierException.class)
    public ResponseEntity<ErreurReponse> handleRegleMetier(RegleMetierException ex) {
        return reponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 409 : contrainte d'intégrité (doublon, clé étrangère)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErreurReponse> handleIntegrite(DataIntegrityViolationException ex) {
        return reponse(HttpStatus.CONFLICT,
                "Opération refusée : contrainte d'intégrité violée (doublon ou ressource liée)");
    }

    // 500 : toute autre erreur imprévue
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErreurReponse> handleGenerale(Exception ex) {
        return reponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue");
    }

    private ResponseEntity<ErreurReponse> reponse(HttpStatus statut, String message) {
        return ResponseEntity.status(statut)
                .body(new ErreurReponse(statut.value(), message, LocalDateTime.now()));
    }
}
