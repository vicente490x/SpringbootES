package its.springboot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import its.springboot.dto.ClasseDTO;
import its.springboot.service.ClasseService;

@RestController
@RequestMapping("/classe")
public class ClasseController {

    private final ClasseService service;

    public ClasseController(ClasseService service) {
        this.service = service;
    }

    // ============================
    // LISTA CLASSI
    // ============================
    @GetMapping("/lista")
    public List<ClasseDTO> getClassi() {
        return service.getClassi();
    }

    // ============================
    // GET SINGOLA CLASSE
    // ============================
    @GetMapping("/{id}")
    public ClasseDTO getClasse(@PathVariable Long id) {
        return service.getClasse(id);
    }

    // ============================
    // INSERIMENTO CLASSE
    // ============================
    @PostMapping("/insert")
    public ClasseDTO insertClasse(@RequestBody ClasseDTO dto) {
        return service.insertClasse(dto);
    }

    // ============================
    // UPDATE CLASSE
    // ============================
    @PutMapping("/update/{id}")
    public ClasseDTO updateClasse(@PathVariable Long id, @RequestBody ClasseDTO dto) {
        return service.updateClasse(id, dto);
    }

    // ============================
    // DELETE CLASSE (con blocco)
    // ============================
    @DeleteMapping("/delete/{id}")
    public String deleteClasse(@PathVariable Long id) {
        try {
            return service.deleteClasse(id)
                    ? "Classe eliminata"
                    : "Classe non trovata";
        } catch (RuntimeException e) {
            return e.getMessage(); // 🔥 manda il messaggio al frontend
        }
    }
}
