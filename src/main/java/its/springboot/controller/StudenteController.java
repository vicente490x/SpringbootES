package its.springboot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/insert")
    public StudenteDTO insertStudente(@RequestBody StudenteDTO dto) {
        return service.insertStudente(dto);
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
