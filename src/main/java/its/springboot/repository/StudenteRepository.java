package its.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import its.springboot.entity.StudenteEntity;

public interface StudenteRepository extends JpaRepository<StudenteEntity, Long> {

    // Cerca studenti tramite l’ID della classe (FK)
    List<StudenteEntity> findByClasse_Id(Long classeId);
}
