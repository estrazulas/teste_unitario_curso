package modelo;

import controller.LanceValorInvalido;
import exception.UsuarioNulloException;

public class Lance {

	private Usuario usuario;
	
	private double valor;
	
	public Lance(Usuario usuario, double d) throws UsuarioNulloException, LanceValorInvalido {
		if(usuario == null){
			throw new UsuarioNulloException();
		}
		if(d <= 0){
			throw new LanceValorInvalido();
		}
		this.usuario = usuario;
		this.valor=d;
		
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public double getValor() {
		return valor;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return " Usuário :"+usuario.getNome()+" Valor: "+valor;
	}
	
	@Override
	public boolean equals(Object obj) {
		return new Double(getValor()).equals(new Double(((Lance)obj).getValor()));
	}
}
