package its.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import its.springboot.dto.StudenteDTO;
import its.springboot.entity.StudenteEntity;
import its.springboot.repository.StudenteRepository;

@Service
public class StudenteService {

    private final StudenteRepository repo;

    public StudenteService(StudenteRepository repo) {
        this.repo = repo;
    }

    //  Entity to DTO
    private StudenteDTO convertToDTO(StudenteEntity entity) {
        StudenteDTO dto = new StudenteDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCognome(entity.getCognome());
        dto.setEta(entity.getEta());
        return dto;
    }

    //  DTO to Entity
    private StudenteEntity convertToEntity(StudenteDTO dto) {
        StudenteEntity entity = new StudenteEntity();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setCognome(dto.getCognome());
        entity.setEta(dto.getEta());
        return entity;
    }

    // GET tutti
    public List<StudenteDTO> getStudenti() {
        List<StudenteEntity> entities = repo.findAll();
        List<StudenteDTO> dtos = new ArrayList<>();

        for (StudenteEntity e : entities) {
            dtos.add(convertToDTO(e));
        }

        return dtos;
    }

    // GET per ID
    public StudenteDTO getStudente(Long id) {
        return repo.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // INSERT
    public StudenteDTO insertStudente(StudenteDTO dto) {
        StudenteEntity entity = convertToEntity(dto);
        StudenteEntity saved = repo.save(entity);
        return convertToDTO(saved);
    }

    // UPDATE
    public StudenteDTO updateStudente(Long id, StudenteDTO dto) {
        StudenteEntity entity = repo.findById(id).orElse(null);
        if (entity == null) return null;

        entity.setNome(dto.getNome());
        entity.setCognome(dto.getCognome());
        entity.setEta(dto.getEta());

        StudenteEntity saved = repo.save(entity);
        return convertToDTO(saved);
    }

    // DELETE
    public boolean deleteStudente(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
