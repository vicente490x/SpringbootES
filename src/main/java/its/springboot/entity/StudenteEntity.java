package its.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "studenti")
public class StudenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private int eta;

    @ManyToOne
    @JoinColumn(name = "classe_id") 
    private ClasseEntity classe;

    public StudenteEntity() {}

    public StudenteEntity(String nome, String cognome, int eta, ClasseEntity classe) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.classe = classe;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public int getEta() { return eta; }
    public void setEta(int eta) { this.eta = eta; }

    public ClasseEntity getClasse() { return classe; }
    public void setClasse(ClasseEntity classe) { this.classe = classe; }
}
