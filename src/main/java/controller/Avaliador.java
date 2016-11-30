package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import exception.UsuarioNulloException;
import modelo.Lance;
import modelo.Leilao;

public class Avaliador {

	private double maiorLance = Double.NEGATIVE_INFINITY;
	private double menorLance = Double.POSITIVE_INFINITY;

	public void avalia(Leilao leilao) throws LeilaoSemLancesException, UsuarioNulloException, LanceValorInvalido, DoisLancesSeq {
		if(leilao.getLances() == null || leilao.getLances().size() == 0){
			throw new LeilaoSemLancesException();
		}
		String usuarioAnterior ="";
		for(Lance lance : leilao.getLances()){
			
			
			if(lance.getUsuario() == null){
				throw new UsuarioNulloException();
			}
			
			if(usuarioAnterior.equals(lance.getUsuario().getNome())){
				throw new DoisLancesSeq();
			}
			
			if(lance.getValor() <=0){
				throw new LanceValorInvalido();
			}
			
			if(lance.getValor() > maiorLance){
				maiorLance = lance.getValor();
			}
			if(lance.getValor() < menorLance){
				menorLance = lance.getValor();
			}
			usuarioAnterior = lance.getUsuario().getNome();
		}
	}

	public List<Lance> tresMaioresLances(Leilao leilao ){
		Collections.sort(leilao.getLances(), new Comparator<Lance>() {

			@Override
			public int compare(Lance o1, Lance o2) {
				return Double.valueOf(o2.getValor()).compareTo(Double.valueOf(o1.getValor()));
			}
		});
		List<Lance> maiores=  new ArrayList<>();
		int conta =1;
		for(Lance lance: leilao.getLances()){
			
			if(conta <= 3){
				maiores.add(lance);
			}
			conta++;
		}
		return maiores;
	}
	
	public double getMaiorLance() {
		return maiorLance;
	}

	public double getMenorLance() {
		return menorLance;
	}
}
