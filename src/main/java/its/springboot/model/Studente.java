package its.springboot.model;

public class Studente {

    private Long id;
    private String nome;
    private String cognome;
    private int eta;
    private Long classeId; // relazione con Classe

    public Studente() {}

    public Studente(String nome, String cognome, int eta, Long classeId) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.classeId = classeId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public int getEta() { return eta; }
    public void setEta(int eta) { this.eta = eta; }

    public Long getClasseId() { return classeId; }
    public void setClasseId(Long classeId) { this.classeId = classeId; }
}
