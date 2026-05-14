package its.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "classi")
public class ClasseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sezione;
    private int numeroStudenti;

    public ClasseEntity() {}

    public ClasseEntity(String sezione, int numeroStudenti) {
        this.sezione = sezione;
        this.numeroStudenti = numeroStudenti;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSezione() { return sezione; }
    public void setSezione(String sezione) { this.sezione = sezione; }

    public int getNumeroStudenti() { return numeroStudenti; }
    public void setNumeroStudenti(int numeroStudenti) { this.numeroStudenti = numeroStudenti; }
}
