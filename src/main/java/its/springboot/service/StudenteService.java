package its.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import its.springboot.dto.StudenteDTO;
import its.springboot.model.Studente;

@Service
public class StudenteService {

    private List<Studente> studenti = new ArrayList<>();
    private Long nextId = 1L;

    // ENTITY → DTO
    private StudenteDTO toDTO(Studente s) {
        return new StudenteDTO(
            s.getId(),
            s.getNome(),
            s.getCognome(),
            s.getEta(),
            s.getClasseId()
        );
    }

    // DTO → ENTITY
    private Studente toEntity(StudenteDTO dto) {
        Studente s = new Studente(
            dto.getNome(),
            dto.getCognome(),
            dto.getEta(),
            dto.getClasseId()
        );
        s.setId(dto.getId());
        return s;
    }

    // GET tutti
    public List<StudenteDTO> getStudenti() {
        List<StudenteDTO> result = new ArrayList<>();
        for (Studente s : studenti) result.add(toDTO(s));
        return result;
    }

    // GET per ID
    public StudenteDTO getStudente(Long id) {
        for (Studente s : studenti)
            if (s.getId().equals(id))
                return toDTO(s);
        return null;
    }

    // INSERT singolo
    public StudenteDTO insertStudente(StudenteDTO dto) {
        Studente s = toEntity(dto);
        s.setId(nextId++);
        studenti.add(s);
        return toDTO(s);
    }

    // INSERT multiplo
    public List<StudenteDTO> insertListStudenti(List<StudenteDTO> dtos) {
        List<StudenteDTO> result = new ArrayList<>();
        for (StudenteDTO dto : dtos)
            result.add(insertStudente(dto));
        return result;
    }

    // GET studenti per classe
    public List<StudenteDTO> getStudentiByClasse(Long classeId) {
        List<StudenteDTO> result = new ArrayList<>();
        for (Studente s : studenti)
            if (s.getClasseId().equals(classeId))
                result.add(toDTO(s));
        return result;
    }

    // UPDATE
    public StudenteDTO updateStudente(Long id, StudenteDTO dto) {
        for (Studente s : studenti) {
            if (s.getId().equals(id)) {
                s.setNome(dto.getNome());
                s.setCognome(dto.getCognome());
                s.setEta(dto.getEta());
                s.setClasseId(dto.getClasseId());
                return toDTO(s);
            }
        }
        return null;
    }

    // DELETE
    public boolean deleteStudente(Long id) {
        return studenti.removeIf(s -> s.getId().equals(id));
    }
}
