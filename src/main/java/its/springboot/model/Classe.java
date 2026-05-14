package its.springboot.model;

public class Classe {
	private Long id;
	private String sezione;
	private int numeroStudenti;
	
	public Classe(String sezione, int numeroStudenti) {
		setSezione(sezione);
		setNumeroStudenti(numeroStudenti);
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
