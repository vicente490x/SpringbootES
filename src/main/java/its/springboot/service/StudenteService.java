package its.springboot.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import its.springboot.dto.StudenteDTO;
import its.springboot.entity.ClasseEntity;
import its.springboot.entity.StudenteEntity;
import its.springboot.repository.ClasseRepository;
import its.springboot.repository.StudenteRepository;

@Service
public class StudenteService {

    private final StudenteRepository studenteRepo;
    private final ClasseRepository classeRepo;

    public StudenteService(StudenteRepository studenteRepo, ClasseRepository classeRepo) {
        this.studenteRepo = studenteRepo;
        this.classeRepo = classeRepo;
    }

    private StudenteDTO toDTO(StudenteEntity s) {
        StudenteDTO dto = new StudenteDTO();
        dto.setId(s.getId());
        dto.setNome(s.getNome());
        dto.setCognome(s.getCognome());
        dto.setEta(s.getEta());
        dto.setClasseId(s.getClasse().getId());
        dto.setClasseNome(s.getClasse().getSezione().getLabel());
        return dto;
    }

    private StudenteEntity toEntity(StudenteDTO dto) {
        ClasseEntity classe = classeRepo.findById(dto.getClasseId())
                .orElseThrow(() -> new NoSuchElementException("Classe non trovata"));

        return new StudenteEntity(
                dto.getNome(),
                dto.getCognome(),
                dto.getEta(),
                classe
        );
    }

    public List<StudenteDTO> getStudenti() {
        return studenteRepo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public StudenteDTO getStudente(Long id) {
        return studenteRepo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NoSuchElementException("Studente non trovato"));
    }

    public List<StudenteDTO> getStudentiByClasse(Long classeId) {
        ClasseEntity classe = classeRepo.findById(classeId)
                .orElseThrow(() -> new NoSuchElementException("Classe non trovata"));

        return studenteRepo.findByClasse_Id(classeId).stream()
                .map(s -> {
                    StudenteDTO dto = toDTO(s);
                    dto.setClasseNome(classe.getSezione().getLabel());
                    return dto;
                })
                .collect(Collectors.toList());
    }

  
    public StudenteDTO insertStudente(StudenteDTO dto) {

        ClasseEntity classe = classeRepo.findById(dto.getClasseId())
                .orElseThrow(() -> new NoSuchElementException("Classe non trovata"));

        classe.setNumeroStudenti(classe.getNumeroStudenti() + 1);
        classeRepo.save(classe);

        StudenteEntity saved = studenteRepo.save(toEntity(dto));
        return toDTO(saved);
    }

    public List<StudenteDTO> insertListStudenti(List<StudenteDTO> dtos) {
		return dtos.stream()
				.map(this::insertStudente)
				.collect(Collectors.toList());
	}
    public StudenteDTO updateStudente(StudenteDTO dto) {

        StudenteEntity s = studenteRepo.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Studente non trovato"));

        ClasseEntity vecchia = s.getClasse();
        ClasseEntity nuova = classeRepo.findById(dto.getClasseId())
                .orElseThrow(() -> new NoSuchElementException("Classe non trovata"));

        
        if (!vecchia.getId().equals(nuova.getId())) {
            vecchia.setNumeroStudenti(vecchia.getNumeroStudenti() - 1);
            nuova.setNumeroStudenti(nuova.getNumeroStudenti() + 1);
            classeRepo.save(vecchia);
            classeRepo.save(nuova);
        }

       
        s.setNome(dto.getNome());
        s.setCognome(dto.getCognome());
        s.setEta(dto.getEta());
        s.setClasse(nuova);

        return toDTO(studenteRepo.save(s));
    }

    
    public boolean deleteStudente(Long id) {

        StudenteEntity s = studenteRepo.findById(id)
                .orElse(null);

        if (s == null) return false;

        ClasseEntity classe = s.getClasse();
        classe.setNumeroStudenti(classe.getNumeroStudenti() - 1);
        classeRepo.save(classe);

        studenteRepo.deleteById(id);
        return true;
    }
}
