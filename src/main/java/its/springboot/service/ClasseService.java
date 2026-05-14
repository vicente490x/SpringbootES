package its.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import its.springboot.dto.ClasseDTO;
import its.springboot.model.Classe;

@Service
public class ClasseService {

    private List<Classe> classi = new ArrayList<>();

    public ClasseService() {
        // DATI INIZIALI
        classi.add(new Classe("5A", 25));
        classi.add(new Classe("5B", 28));
        classi.add(new Classe("5C", 30));
        classi.add(new Classe("4A", 27));
        classi.add(new Classe("4B", 26));
        classi.add(new Classe("4C", 29));
        classi.add(new Classe("3A", 24));
        classi.add(new Classe("3B", 22));
        classi.add(new Classe("3C", 31));
        classi.add(new Classe("2A", 26));
        classi.add(new Classe("2B", 27));
        classi.add(new Classe("2C", 28));
        classi.add(new Classe("1A", 25));
        classi.add(new Classe("1B", 24));
        classi.add(new Classe("1C", 29));
    }

    private ClasseDTO convertToDTO(Classe c) {
		ClasseDTO dto = new ClasseDTO();
		dto.setId(c.getId());
		dto.setSezione(c.getSezione());
		dto.setNumeroStudenti(c.getNumeroStudenti());
		return dto;
	}
    
    private Classe convertToEntity(ClasseDTO dto) {
    // GET lista filtrata o completa
		Classe c = new Classe(dto.getSezione(), dto.getNumeroStudenti());
		c.setId(dto.getId());
		return c;
	}
    
    
    public List<ClasseDTO> getClassi() {
		return classi.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	public Classe getClasse(Long id) {
		return classi.stream()
				.filter(c -> c.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	public ClasseDTO insertClasse(ClasseDTO dto) {
		Classe c = convertToEntity(dto);
		c.setId((long) (classi.size() + 1)); // ID auto-incrementale
		classi.add(c);
		return convertToDTO(c);
	}
	
	public List<ClasseDTO> insertListClasse(List<ClasseDTO> dtos) {
		List<ClasseDTO> inserted = new ArrayList<>();
		for (ClasseDTO dto : dtos) {
			inserted.add(insertClasse(dto));
		}
		return inserted;
	}

	public ClasseDTO updateClasse(Long id, ClasseDTO dto) {
		for (int i = 0; i < classi.size(); i++) {
			if (classi.get(i).getId().equals(id)) {
				Classe c = convertToEntity(dto);
				c.setId(id); // Mantieni lo stesso ID
				classi.set(i, c);
				return convertToDTO(c);
			}
		}
		return null; // Non trovato
	}

	public boolean deleteClasse(Long id) {
		return classi.removeIf(c -> c.getId().equals(id));
	}
}
