package daviderocca.U5_W2_D5.controllers;


import daviderocca.U5_W2_D5.entities.Viaggio;
import daviderocca.U5_W2_D5.exceptions.BadRequestException;
import daviderocca.U5_W2_D5.payloads.NewStatoViaggioDTO;
import daviderocca.U5_W2_D5.payloads.NewViaggioDTO;
import daviderocca.U5_W2_D5.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.stream.Collectors;

@RestController
@RequestMapping("/viaggio")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    @GetMapping
    public Page<Viaggio> getViaggi(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy) {
        return this.viaggioService.getViaggi(page, size, sortBy);
    }

    @GetMapping("/{viaggioId}")
    public Viaggio getViaggioById(@PathVariable Long viaggioId) { return this.viaggioService.findViaggioById(viaggioId);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio createViaggio(@RequestBody @Validated NewViaggioDTO body, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        return this.viaggioService.saveViaggio(body);
    }

    @PutMapping("/{viaggioId}")
    public Viaggio findViaggioByIdAndUpdate(@PathVariable Long viaggioId, @RequestBody @Validated NewViaggioDTO body) {
        return this.viaggioService.findViaggioByIdAndUpdate(viaggioId, body);
    }

    @PutMapping("/{viaggioId}/assegna/{username}")
    public Viaggio assegnaDipendenteAlViaggio(@PathVariable Long viaggioId, @PathVariable String username) {
        return viaggioService.assegnaDipendente(viaggioId, username);
    }

    @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteViaggioById(@PathVariable Long viaggioId) {
        this.viaggioService.findViaggioByIdAndDelete(viaggioId);
    }

    @PatchMapping("/{id}/stato")
    public Viaggio aggiornaStato(@PathVariable Long id, @RequestBody @Validated NewStatoViaggioDTO payload) {
        return viaggioService.aggiornaStatoViaggio(id, payload.statoViaggio());
    }


}
