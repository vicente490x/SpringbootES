package its.springboot.dto;

public class ClasseDTO {

	private Long id;
    private String sezione;
    private int numeroStudenti;

    public ClasseDTO() {}

    public ClasseDTO(String sezione, int numeroStudenti) {
        this.sezione = sezione;
        this.numeroStudenti = numeroStudenti;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public int getNumeroStudenti() {
        return numeroStudenti;
    }

    public void setNumeroStudenti(int numeroStudenti) {
        this.numeroStudenti = numeroStudenti;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
