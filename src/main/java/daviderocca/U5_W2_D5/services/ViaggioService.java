package daviderocca.U5_W2_D5.services;

import daviderocca.U5_W2_D5.entities.Dipendente;
import daviderocca.U5_W2_D5.entities.Viaggio;
import daviderocca.U5_W2_D5.enums.StatoViaggio;
import daviderocca.U5_W2_D5.exceptions.BadRequestException;
import daviderocca.U5_W2_D5.exceptions.NotFoundException;
import daviderocca.U5_W2_D5.payloads.NewViaggioDTO;
import daviderocca.U5_W2_D5.repositories.ViaggioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;

    @Autowired
    private DipendenteService dipendenteService;

    public Page<Viaggio> getViaggi(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return viaggioRepository.findAll(pageable);
    }

    public Viaggio saveViaggio(NewViaggioDTO payload){

        Viaggio newViaggio = new Viaggio();
        newViaggio.setDestinazione(payload.destinazione());
        newViaggio.setDataDiPartenza(payload.dataDiPartenza());
        newViaggio.setDataDiRitorno(payload.dataDiRitorno());
        newViaggio.setStatoViaggio(payload.statoViaggio());
        newViaggio.setDipendente(null);

        if(payload.dataDiPartenza().isAfter(payload.dataDiRitorno())) throw new BadRequestException("La data di partenza non può essere dopo la data di ritorno!");

        Viaggio savedViaggio = this.viaggioRepository.save(newViaggio);

        log.info("Il viaggio con ID: " + savedViaggio.getId() + " è stato correttamente salvato nel DB!");

        return savedViaggio;
    }


    public Viaggio findViaggioById(Long viaggioId) {
        return this.viaggioRepository.findById(viaggioId).orElseThrow(() -> new NotFoundException(viaggioId));
    }

    public Viaggio findViaggioByIdAndUpdate(Long viaggioId, NewViaggioDTO payload) {
        Viaggio found = findViaggioById(viaggioId);

        found.setDestinazione(payload.destinazione());
        found.setDataDiPartenza(payload.dataDiPartenza());
        found.setDataDiRitorno(payload.dataDiRitorno());
        found.setStatoViaggio(payload.statoViaggio());
        found.setDipendente(null);

        Viaggio modifiedViaggio = this.viaggioRepository.save(found);

        log.info("Il viaggio con ID: " + modifiedViaggio.getId() + " è stato modificato correttamente!");

        return modifiedViaggio;

    }

    public void findViaggioByIdAndDelete(Long viaggioId) {
        Viaggio found = findViaggioById(viaggioId);
        this.viaggioRepository.delete(found);
        log.info("Il viaggio con ID: " + found.getId() + " è stato cancellato correttamente dal DB!");
    }

    public Viaggio assegnaDipendente(Long viaggioId, String username) {
        Viaggio viaggio = findViaggioById(viaggioId);

        Dipendente dipendente = dipendenteService.findDipendenteByUsername(username);

        Optional<Viaggio> prenotazioneEsistente = viaggioRepository.findByDipendenteAndDataDiPartenza(dipendente, viaggio.getDataDiPartenza());

        if (prenotazioneEsistente.isPresent() && (!(prenotazioneEsistente.get().getId() == viaggioId))) {
            throw new BadRequestException("Il dipendente ha già una prenotazione per quella data.");
        }

        viaggio.setDipendente(dipendente);
        return viaggioRepository.save(viaggio);
    }

    public Viaggio aggiornaStatoViaggio(Long viaggioId, StatoViaggio nuovoStato) {
        Viaggio viaggio = findViaggioById(viaggioId);
        viaggio.setStatoViaggio(nuovoStato);
        Viaggio viaggioAggiornato = viaggioRepository.save(viaggio);
        log.info("Stato del viaggio con ID " + viaggioId + " aggiornato a " + nuovoStato);
        return viaggioAggiornato;
    }

}
