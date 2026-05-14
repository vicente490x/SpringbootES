package its.springboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import its.springboot.dto.ClasseDTO;
import its.springboot.entity.ClasseEntity;
import its.springboot.repository.ClasseRepository;

@Service
public class ClasseService {

    private final ClasseRepository repo;

    public ClasseService(ClasseRepository repo) {
        this.repo = repo;
    }

    private ClasseDTO toDTO(ClasseEntity c) {
        ClasseDTO dto = new ClasseDTO();
		dto.setId(c.getId());
		dto.setSezione(c.getSezione());
		dto.setNumeroStudenti(c.getNumeroStudenti());
		return dto;
    }

    private ClasseEntity toEntity(ClasseDTO dto) {
        ClasseEntity c = new ClasseEntity();
        c.setSezione(dto.getSezione());
        c.setNumeroStudenti(dto.getNumeroStudenti());
         // 🔥 IMPORTANTE PER UPDATE
        c.setId(dto.getId());
        return c;
    }

    public List<ClasseDTO> getClassi() {
        return repo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClasseDTO getClasse(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public ClasseDTO insertClasse(ClasseDTO dto) {
        ClasseEntity saved = repo.save(toEntity(dto));
        return toDTO(saved);
    }

    public ClasseDTO updateClasse(Long id, ClasseDTO dto) {
        ClasseEntity c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Classe non trovata"));

        c.setSezione(dto.getSezione());
        c.setNumeroStudenti(dto.getNumeroStudenti());

        return toDTO(repo.save(c));
    }

    public boolean deleteClasse(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
