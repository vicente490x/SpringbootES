package its.springboot.dto;

public class StudenteDTO {

    private Long id;
    private String nome;
    private String cognome;
    private int eta;

    private Long classeId;     // ID della classe
    private String classeNome; // "3B", "4A", ecc.

    public StudenteDTO() {}

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

    public String getClasseNome() { return classeNome; }
    public void setClasseNome(String classeNome) { this.classeNome = classeNome; }
}
