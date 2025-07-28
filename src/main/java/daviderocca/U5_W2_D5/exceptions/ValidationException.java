package daviderocca.U5_W2_D5.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private String errorMessages;

    public ValidationException(String errorMessages) {
        super("Errori vari di validazione!");
        this.errorMessages = errorMessages;
    }
}