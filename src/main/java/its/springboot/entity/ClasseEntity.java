package its.springboot.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "classi")
public class ClasseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sezione;
    private int numeroStudenti;
    @OneToMany(mappedBy = "classe")
    private List<StudenteEntity> studenti= new ArrayList<StudenteEntity>();
    public ClasseEntity() {}

    public ClasseEntity(String sezione, int numeroStudenti) {
        this.sezione = sezione;
        this.numeroStudenti = numeroStudenti;
    }
    
    public List<StudenteEntity> getStudenti() { return studenti; }
    
    public void setStudenti(List<StudenteEntity> studenti) { this.studenti = studenti; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSezione() { return sezione; }
    public void setSezione(String sezione) { this.sezione = sezione; }

    public int getNumeroStudenti() { return numeroStudenti; }
    public void setNumeroStudenti(int numeroStudenti) { this.numeroStudenti = numeroStudenti; }
}
