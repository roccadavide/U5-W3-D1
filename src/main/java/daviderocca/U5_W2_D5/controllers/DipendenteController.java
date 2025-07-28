package daviderocca.U5_W2_D5.controllers;

import daviderocca.U5_W2_D5.entities.Dipendente;
import daviderocca.U5_W2_D5.exceptions.BadRequestException;
import daviderocca.U5_W2_D5.payloads.NewDipendenteDTO;
import daviderocca.U5_W2_D5.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendente")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping
    public Page<Dipendente> getDipendenti(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return this.dipendenteService.getDipendente(page, size, sortBy);
    }

    @GetMapping("/{username}")
    public Dipendente getDipendenteByUsername(@PathVariable String username) { return this.dipendenteService.findDipendenteByUsername(username);}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente createDipendente(@RequestBody @Validated NewDipendenteDTO body, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining(", ")));
        }

        return this.dipendenteService.saveDipendente(body);
    }

    @PutMapping("/{username}")
    public Dipendente findDipendenteByUsernameAndUpdate(@PathVariable String username, @RequestBody @Validated NewDipendenteDTO body) {
        return this.dipendenteService.findDipendenteByUsernameAndUpdate(username, body);
    }


//    @PatchMapping("/{username}/profileImg")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void uploadImage(@RequestParam("profileImg")MultipartFile file, @PathVariable String username) {
//        System.out.println(file.getOriginalFilename());
//        System.out.println(file.getSize());
//        this.dipendenteService.uploadAvatar(file, username);
//    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendenteByUsername(@PathVariable String username) {
        this.dipendenteService.findDipendenteByUsernameAndDelete(username);
    }

}
