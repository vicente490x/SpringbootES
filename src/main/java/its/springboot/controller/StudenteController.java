package its.springboot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import its.springboot.dto.StudenteDTO;
import its.springboot.service.StudenteService;

@RestController
@RequestMapping("/studente")
public class StudenteController {

    private final StudenteService service;

    public StudenteController(StudenteService service) {
        this.service = service;
    }

    @GetMapping("/lista")
    public List<StudenteDTO> getStudenti() {
        return service.getStudenti();
    }

    @GetMapping("/{id}")
    public StudenteDTO getStudente(@PathVariable Long id) {
        return service.getStudente(id);
    }

    @GetMapping("/classe/{classeId}")
    public List<StudenteDTO> getStudentiByClasse(@PathVariable Long classeId) {
        return service.getStudentiByClasse(classeId);
    }

    @PostMapping("/insert")
    public StudenteDTO insertStudente(@RequestBody StudenteDTO dto) {
        return service.insertStudente(dto);
    }

    @PostMapping("/insertMultiple")
    public List<StudenteDTO> insertMultiple(@RequestBody List<StudenteDTO> dtos) {
        return service.insertListStudenti(dtos);
    }

    @PutMapping("/update/{id}")
    public StudenteDTO updateStudente(@PathVariable Long id, @RequestBody StudenteDTO dto) {
        return service.updateStudente(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudente(@PathVariable Long id) {
        return service.deleteStudente(id)
                ? "Studente eliminato"
                : "Studente non trovato";
    }
}
