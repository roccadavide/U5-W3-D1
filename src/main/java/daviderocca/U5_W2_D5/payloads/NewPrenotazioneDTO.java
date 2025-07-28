package daviderocca.U5_W2_D5.payloads;


import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record NewPrenotazioneDTO(@NotNull(message = "La data di prenotazione è obbligatoria!")
                                 LocalDate dataRichiestaPrenotazione,
                                 String noteDipendente,
                                 @NotNull(message = "L'ID del viaggio è obbligatorio")
                                 Long idViaggio,
                                 @NotNull(message = "L'ID del dipendente è obbligatorio")
                                 String usernameDipendente) {
}
