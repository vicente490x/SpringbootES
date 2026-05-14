package its.springboot.service;

import java.util.List;
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

    // 🔥 POPOLA AUTOMATICAMENTE LE CLASSI ALL’AVVIO
    @PostConstruct
    public void initClassi() {
        if (repo.count() == 0) {
            for (ClasseTipo tipo : ClasseTipo.values()) {
                ClasseEntity c = new ClasseEntity(tipo, 0);
                repo.save(c);
            }
            System.out.println("✔ Classi inizializzate nel database");
        }
    }

    private ClasseDTO toDTO(ClasseEntity c) {
        ClasseDTO dto = new ClasseDTO();
        dto.setId(c.getId());
        dto.setSezione(c.getSezione().getLabel()); // ENUM → "3B"
        dto.setNumeroStudenti(c.getNumeroStudenti());
        return dto;
    }

    private ClasseEntity toEntity(ClasseDTO dto) {
        ClasseEntity c = new ClasseEntity();

        ClasseTipo tipo = fromLabel(dto.getSezione());
        c.setSezione(tipo);

        c.setNumeroStudenti(dto.getNumeroStudenti());
        c.setId(dto.getId());

        return c;
    }

    private ClasseTipo fromLabel(String label) {
        for (ClasseTipo tipo : ClasseTipo.values()) {
            if (tipo.getLabel().equalsIgnoreCase(label)) {
                return tipo;
            }
        }
        throw new RuntimeException("ClasseTipo non valido: " + label);
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

        c.setSezione(fromLabel(dto.getSezione()));
        c.setNumeroStudenti(dto.getNumeroStudenti());

        return toDTO(repo.save(c));
    }

    public boolean deleteClasse(Long id) {

        ClasseEntity classe = repo.findById(id)
                .orElse(null);

        if (classe == null) return false;

        if (classe.getNumeroStudenti() > 0) {
            throw new RuntimeException("Impossibile eliminare la classe: contiene studenti");
        }

        repo.deleteById(id);
        return true;
    }

}
