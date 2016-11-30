package modelo;

import java.util.Date;
import java.util.List;

public class Leilao {

	private List<Lance> lances;
	
	private String nome;
	
	private Date data;

	public List<Lance> getLances() {
		return lances;
	}

	public void setLances(List<Lance> lances) {
		this.lances = lances;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
