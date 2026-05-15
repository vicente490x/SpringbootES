package its.springboot.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import its.springboot.dto.ClasseDTO;
import its.springboot.entity.ClasseEntity;
import its.springboot.enums.ClasseTipo;
import its.springboot.repository.ClasseRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ClasseService {

    private final ClasseRepository repo;

    public ClasseService(ClasseRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void initClassi() {
        if (repo.count() == 0) {
            for (ClasseTipo tipo : ClasseTipo.values()) {
                repo.save(new ClasseEntity(tipo, 0));
            }
        }
    }

    private ClasseDTO toDTO(ClasseEntity c) {
        ClasseDTO dto = new ClasseDTO();
        dto.setId(c.getId());
        dto.setSezione(c.getSezione().getLabel());
        dto.setNumeroStudenti(c.getNumeroStudenti());
        return dto;
    }

    private ClasseTipo fromLabel(String label) {
    			String normalized = label.trim().toUpperCase();
        return List.of(ClasseTipo.values()).stream()
                .filter(t -> t.getLabel().equalsIgnoreCase(normalized))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ClasseTipo non valido: " + label));
    }

    public List<ClasseDTO> getClassi() {
        return repo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClasseDTO getClasse(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public ClasseDTO insertClasse(ClasseDTO dto) {
        ClasseEntity c = new ClasseEntity(fromLabel(dto.getSezione()), dto.getNumeroStudenti());
        return toDTO(repo.save(c));
    }

    public ClasseDTO updateClasse(ClasseDTO dto) {
        ClasseEntity c = repo.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Classe non trovata"));

        c.setSezione(fromLabel(dto.getSezione()));
        c.setNumeroStudenti(dto.getNumeroStudenti());

        return toDTO(repo.save(c));
    }

    public boolean deleteClasse(Long id) {
        ClasseEntity c = repo.findById(id).orElse(null);
        if (c == null) return false;

        if (c.getNumeroStudenti() > 0)
            throw new RuntimeException("Impossibile eliminare la classe: contiene studenti");

        repo.deleteById(id);
        return true;
    }
}
