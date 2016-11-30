package controller;

import java.util.List;

import dao.LeilaoDAO;
import modelo.Leilao;

public class EncerradorDeLeilao {

	private LeilaoDAO leilaoDAO;
	
	public EncerradorDeLeilao(LeilaoDAO leilaoDAO){
		this.leilaoDAO=  leilaoDAO;
	}
	public void encerrar(){
		List<Leilao> listaLeilao = leilaoDAO.naoConcorrentes();
		
		for (Leilao leilao : listaLeilao) {
			leilao.setEncerrado(true);
			leilaoDAO.update(leilao);
		}
	}
	
	public List<Leilao>  getEncerrados(){
		List<Leilao> encerrados = leilaoDAO.getEncerrados();
		return encerrados;
	}
	public List<Leilao> getCorrentes() {
		List<Leilao> correntes = leilaoDAO.getCorrentes();
		return correntes;
	} 

	
}
