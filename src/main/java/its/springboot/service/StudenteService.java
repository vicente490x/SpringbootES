package its.springboot.service;

import java.util.List;
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

    public StudenteDTO toDTO(StudenteEntity s) {
        StudenteDTO dto = new StudenteDTO();
		dto.setId(s.getId());
		dto.setNome(s.getNome());
		dto.setCognome(s.getCognome());
		dto.setEta(s.getEta());
		dto.setClasseId(s.getClasse().getId());
		// 🔥 IMPORTANTE: se la classe è null, si verificherà un NullPointerException
		// Se vogliamo gestire il caso in cui uno studente non abbia una classe, dobbiamo fare un controllo:
		// dto.setClasseId(s.getClasse() != null ? s.getClasse().getId() : null);
		return dto;
	}

    public StudenteEntity toEntity(StudenteDTO dto) {
        ClasseEntity classe = classeRepo.findById(dto.getClasseId())
                .orElseThrow(() -> new RuntimeException("Classe non trovata"));

        StudenteEntity s = new StudenteEntity();
		s.setNome(dto.getNome());
		s.setCognome(dto.getCognome());
		s.setEta(dto.getEta());
		s.setClasse(classe);

        // 🔥 IMPORTANTE PER UPDATE
        s.setId(dto.getId());

        return s;
    }

    public List<StudenteDTO> getStudenti() {
        return studenteRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public StudenteDTO getStudente(Long id) {
        return studenteRepo.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public StudenteDTO insertStudente(StudenteDTO dto) {
        StudenteEntity entity = toEntity(dto);
        StudenteEntity saved = studenteRepo.save(entity);
        return toDTO(saved);
    }

    public List<StudenteDTO> insertListStudenti(List<StudenteDTO> dtos) {
        List<StudenteEntity> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return studenteRepo.saveAll(entities)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<StudenteDTO> getStudentiByClasse(Long classeId) {
        return studenteRepo.findByClasse_Id(classeId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public StudenteDTO updateStudente(Long id, StudenteDTO dto) {
        StudenteEntity s = studenteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Studente non trovato"));

        ClasseEntity classe = classeRepo.findById(dto.getClasseId())
                .orElseThrow(() -> new RuntimeException("Classe non trovata"));

        s.setNome(dto.getNome());
        s.setCognome(dto.getCognome());
        s.setEta(dto.getEta());
        s.setClasse(classe);

        return toDTO(studenteRepo.save(s));
    }

    public boolean deleteStudente(Long id) {
        if (!studenteRepo.existsById(id)) return false;
        studenteRepo.deleteById(id);
        return true;
    }
}
