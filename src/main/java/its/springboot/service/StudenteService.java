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

    // ============================
    // ENTITY → DTO
    // ============================
    public StudenteDTO toDTO(StudenteEntity s) {
        StudenteDTO dto = new StudenteDTO();

        dto.setId(s.getId());
        dto.setNome(s.getNome());
        dto.setCognome(s.getCognome());
        dto.setEta(s.getEta());

        if (s.getClasse() != null) {
            dto.setClasseId(s.getClasse().getId());
            dto.setClasseNome(s.getClasse().getSezione().getLabel());
        }

        return dto;
    }

    // ============================
    // DTO → ENTITY
    // ============================
    public StudenteEntity toEntity(StudenteDTO dto) {

        StudenteEntity s = new StudenteEntity();
        s.setId(dto.getId());
        s.setNome(dto.getNome());
        s.setCognome(dto.getCognome());
        s.setEta(dto.getEta());

        if (dto.getClasseId() != null) {
            ClasseEntity classe = classeRepo.findById(dto.getClasseId())
                    .orElseThrow(() -> new RuntimeException("Classe non trovata"));
            s.setClasse(classe);
        }

        return s;
    }

    // ============================
    // CRUD
    // ============================
    
    public List<StudenteDTO> getStudentiByClasse(Long classeId) {

        ClasseEntity classe = classeRepo.findById(classeId)
                .orElseThrow(() -> new RuntimeException("Classe non trovata"));

        return studenteRepo.findByClasse_Id(classeId)
                .stream()
                .map(s -> {
                    StudenteDTO dto = toDTO(s);
                    dto.setClasseNome(classe.getSezione().getLabel()); // 🔥 aggiunto
                    return dto;
                })
                .collect(Collectors.toList());
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

    // ============================
    // INSERT STUDENTE
    // ============================
    public StudenteDTO insertStudente(StudenteDTO dto) {

        ClasseEntity classe = classeRepo.findById(dto.getClasseId())
                .orElseThrow(() -> new RuntimeException("Classe non trovata"));

        // incrementa numero studenti
        classe.setNumeroStudenti(classe.getNumeroStudenti() + 1);
        classeRepo.save(classe);

        StudenteEntity entity = toEntity(dto);
        StudenteEntity saved = studenteRepo.save(entity);

        return toDTO(saved);
    }

    // ============================
    // INSERT LISTA STUDENTI
    // ============================
    public List<StudenteDTO> insertListStudenti(List<StudenteDTO> dtos) {

        for (StudenteDTO dto : dtos) {
            ClasseEntity classe = classeRepo.findById(dto.getClasseId())
                    .orElseThrow(() -> new RuntimeException("Classe non trovata"));

            classe.setNumeroStudenti(classe.getNumeroStudenti() + 1);
            classeRepo.save(classe);
        }

        List<StudenteEntity> entities = dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return studenteRepo.saveAll(entities)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ============================
    // UPDATE STUDENTE
    // ============================
    public StudenteDTO updateStudente(Long id, StudenteDTO dto) {

        StudenteEntity s = studenteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Studente non trovato"));

        ClasseEntity vecchiaClasse = s.getClasse();
        ClasseEntity nuovaClasse = classeRepo.findById(dto.getClasseId())
                .orElseThrow(() -> new RuntimeException("Classe non trovata"));

        // se cambia classe → aggiorna conteggi
        if (!vecchiaClasse.getId().equals(nuovaClasse.getId())) {
            vecchiaClasse.setNumeroStudenti(vecchiaClasse.getNumeroStudenti() - 1);
            nuovaClasse.setNumeroStudenti(nuovaClasse.getNumeroStudenti() + 1);
            classeRepo.save(vecchiaClasse);
            classeRepo.save(nuovaClasse);
        }

        s.setNome(dto.getNome());
        s.setCognome(dto.getCognome());
        s.setEta(dto.getEta());
        s.setClasse(nuovaClasse);

        return toDTO(studenteRepo.save(s));
    }

 // ============================
 // DELETE STUDENTE
 // ============================
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
