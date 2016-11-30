package teste;

import java.util.ArrayList;
import java.util.Date;

import controller.LanceValorInvalido;
import exception.UsuarioNulloException;
import modelo.Lance;
import modelo.Leilao;
import modelo.Usuario;

public class LeilaoBuilder {

	private Leilao instancia;
	
	
	public LeilaoBuilder(){
		instancia = new Leilao();
		instancia.setLances(new ArrayList<>());
	}
	
	public Leilao build(){
		return this.instancia;
	}
	
	public LeilaoBuilder comLance(String usuario, double valor) throws UsuarioNulloException, LanceValorInvalido{
		instancia.getLances().add(new Lance(new Usuario(usuario), valor));
		return this;
	}
	
	public LeilaoBuilder comData(Date data){
		instancia.setData(data);
		return this;
	}
	
	public LeilaoBuilder comNome(String nome){
		instancia.setNome(nome);
		return this;
	}
}
