package its.springboot.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import its.springboot.enums.ClasseTipo;

@Entity
@Table(name = "classi")
public class ClasseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ClasseTipo sezione;

    private int numeroStudenti;

    @OneToMany(mappedBy = "classe")
    private List<StudenteEntity> studenti = new ArrayList<>();

    public ClasseEntity() {}

    public ClasseEntity(ClasseTipo sezione, int numeroStudenti) {
        this.sezione = sezione;
        this.numeroStudenti = numeroStudenti;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ClasseTipo getSezione() { return sezione; }
    public void setSezione(ClasseTipo sezione) { this.sezione = sezione; }

    public int getNumeroStudenti() { return numeroStudenti; }
    public void setNumeroStudenti(int numeroStudenti) { this.numeroStudenti = numeroStudenti; }

    public List<StudenteEntity> getStudenti() { return studenti; }
    public void setStudenti(List<StudenteEntity> studenti) { this.studenti = studenti; }
}
