package its.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import its.springboot.entity.StudenteEntity;

public interface StudenteRepository extends JpaRepository<StudenteEntity, Long> {
}
