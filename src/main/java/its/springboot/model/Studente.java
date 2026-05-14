package its.springboot.model;

public class Studente {
	private String nome;
	private String cognome;
	private String dataNascita;
	
	public Studente(String nome, String cognome, String dataNascita) {
		setNome(nome);
		setCognome(cognome);
		setDataNascita(dataNascita);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	
}
