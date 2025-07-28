package daviderocca.U5_W2_D5.payloads;

import daviderocca.U5_W2_D5.enums.StatoViaggio;
import jakarta.validation.constraints.NotNull;

public record NewStatoViaggioDTO(@NotNull(message = "Lo stato del viaggio è obbligatorio")
                                 StatoViaggio statoViaggio) {
}
