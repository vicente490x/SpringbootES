package its.springboot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import its.springboot.model.Classe;
import its.springboot.service.ClasseService;
import its.springboot.dto.ClasseDTO;


@RestController
@RequestMapping("/classe")
public class ClasseController {

    private final ClasseService classeService;

    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    // GET
    @GetMapping("/lista")	
    public List<ClasseDTO> getClassi() {
		return classeService.getClassi();
	}
    
	@GetMapping("/{id}")
	public Classe getClasse(@PathVariable Long id) {
		return classeService.getClasse(id);
	}
	
	// POST
	@PostMapping("/insert")
	public ClasseDTO insertClasse(@RequestBody ClasseDTO dto) {
		return classeService.insertClasse(dto);
	}
	@PostMapping("/insertList")
	public List<ClasseDTO> insertListClassi(@RequestBody List<ClasseDTO> dtos) {
		return classeService.insertListClasse(dtos);
	}
	// PUT
	@PutMapping("/update/{id}")
	public ClasseDTO updateClasse(@PathVariable Long id, @RequestBody ClasseDTO dto) {
		return classeService.updateClasse(id, dto);
	}
	
	// DELETE
	@DeleteMapping("/delete/{id}")
	public String deleteClasse(@PathVariable Long id) {
		return classeService.deleteClasse(id)
				? "Classe eliminata"
				: "Classe non trovata";
	}
}
