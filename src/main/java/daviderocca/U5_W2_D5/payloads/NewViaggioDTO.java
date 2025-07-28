package daviderocca.U5_W2_D5.payloads;

import daviderocca.U5_W2_D5.enums.StatoViaggio;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record NewViaggioDTO(@NotEmpty(message = "La destinazione è obbligatoria")
                           @Size(min = 2, max = 30, message = "La destinazione deve essere di lunghezza compresa tra 2 e 30")
                           String destinazione,
                            @NotNull(message = "La data di partenza è obbligatoria")
                            LocalDate dataDiPartenza,
                            @NotNull(message = "La data di ritorno è obbligatoria")
                           LocalDate dataDiRitorno,
                            @NotNull(message = "Lo stato del viaggio è obbligatorio")
                            StatoViaggio statoViaggio,
                            @NotNull(message = "L'ID del dipendente è obbligatorio")
                            String usernameDipendente) {
}
