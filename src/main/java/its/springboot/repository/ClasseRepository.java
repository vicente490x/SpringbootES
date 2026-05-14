package its.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import its.springboot.entity.ClasseEntity;

public interface ClasseRepository extends JpaRepository<ClasseEntity, Long> {
}
