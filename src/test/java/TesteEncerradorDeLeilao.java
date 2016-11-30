import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;

import controller.EncerradorDeLeilao;
import controller.LanceValorInvalido;
import dao.LeilaoDAO;
import exception.UsuarioNulloException;
import modelo.Leilao;
import teste.LeilaoBuilder;

public class TesteEncerradorDeLeilao {

	
	@Test
	public void naoDeveEncerrarLeiloesCorrentes() throws UsuarioNulloException, LanceValorInvalido{
		LeilaoDAO leilaoDAO = mock(LeilaoDAO.class);
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(leilaoDAO);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2016,11,1);
		Date data1 = calendar.getTime();
		
		Leilao leilao = new LeilaoBuilder()
				.comLance("Maria", 1000.00)//
				.comNome("Fusca")//
				.comData(data1).build();
		
		Date data2 = new Date();
		
		//corrente
		Leilao leilao2 = new LeilaoBuilder()
				.comLance("Maria", 1000.00)//
				.comNome("Fusca")//
				.comData(data2).build();
		
		Leilao leilao3 = new LeilaoBuilder()
				.comLance("Maria", 1000.00)//
				.comNome("Fusca")//
				.comData(data1).build();
		
		
			
		List<Leilao> listaCorrentes = Arrays.asList(leilao2);
		
		when(leilaoDAO.getCorrentes()).thenReturn(listaCorrentes);
		
		encerrador.encerrar();	
		
		
		Assert.assertEquals(1, encerrador.getCorrentes().size());
		
	}
	
	@Test
	public void deveVerificarRepeticao(){
		LeilaoDAO leilaoDAO = mock(LeilaoDAO.class);
		for (int i = 5; i < 15; i++) {
			leilaoDAO.enviarEmail(i, i+1);
			leilaoDAO.enviarEmail(i+2, i);
		}
		verify(leilaoDAO,times(20)).enviarEmail(any(int.class), any(int.class));
		//verify(leilaoDAO,atLeast(3)).enviarEmail(any(int.class),3);
		//verify(leilaoDAO,atMost(2)).enviarEmail(1,any(int.class));
	}
	
	@Test
	public void naoDeveEncerrarLeiloesCasoNaoHajaNenhum(){
		LeilaoDAO leilaoDAO = mock(LeilaoDAO.class);
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(leilaoDAO);
		
		List<Leilao> listaLeilaoAntigo = new ArrayList<>();
		
		
		when(leilaoDAO.getEncerrados()).thenReturn(listaLeilaoAntigo);
		
		encerrador.encerrar();
		
		Assert.assertEquals(0, encerrador.getEncerrados().size());
	}
	
	@Test(expected=UsuarioNulloException.class)
	public void deveVerificarExcecao() throws UsuarioNulloException, LanceValorInvalido{
		LeilaoDAO leilaoDao = mock(LeilaoDAO.class);
		
		when(leilaoDao.gerarExcecao()).thenThrow(UsuarioNulloException.class);
		leilaoDao.gerarExcecao();
		
	}
	
	@Test
	public void deveEncerrarLeiloesInicioUmaSemana() throws UsuarioNulloException, LanceValorInvalido{
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2016,11,1);
		Date data1 = calendar.getTime();
		
		Leilao leilao = new LeilaoBuilder()
				.comLance("Maria", 1000.00)//
				.comNome("Fusca")//
				.comData(data1).build();
		
		Date data2 = new Date();
		
		
		Leilao leilao2 = new LeilaoBuilder()
				.comLance("Maria", 1000.00)//
				.comNome("Fusca")//
				.comData(data2).build();
		
		Leilao leilao3 = new LeilaoBuilder()
				.comLance("Maria", 1000.00)//
				.comNome("Fusca")//
				.comData(data1).build();
		
		
		LeilaoDAO leilaoDAO = mock(LeilaoDAO.class);
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(leilaoDAO);
			
		List<Leilao> listaLeilaoAntigo = Arrays.asList(leilao,leilao3);
		
		when(leilaoDAO.naoConcorrentes()).thenReturn(listaLeilaoAntigo);
		when(leilaoDAO.getEncerrados()).thenReturn(listaLeilaoAntigo);
		
	
		
		encerrador.encerrar();
		
		verify(leilaoDAO).update(leilao);
		verify(leilaoDAO).update(leilao3);
	
		int x = (leilaoDAO.enviarEmail(2,3));
		int y = (leilaoDAO.enviarEmail(2,3));
		int z = (leilaoDAO.enviarEmail(2,3));
		
		//verify(leilaoDAO,times(1)).calculaPotencia(2, 3);
		verify(leilaoDAO,atLeastOnce()).enviarEmail(2, 3);
		verify(leilaoDAO,atLeast(2)).enviarEmail(2, 3);
		//verify(leilaoDAO,atMost(2)).calculaPotencia(2, 3);
		//verify(leilaoDAO,never()).calculaPotencia(2, 3);
		
		InOrder inOrder = inOrder(leilaoDAO);
		inOrder.verify(leilaoDAO).update(leilao);
		inOrder.verify(leilaoDAO).enviarEmail(2, 3);
		
		
		/*InOrder inOrder2 = inOrder(leilaoDAO);
		inOrder2.verify(leilaoDAO, times(1)).update(leilao);
		inOrder2.verify(leilaoDAO).enviarEmail(2, 2);*/
		
		Assert.assertTrue(leilao.isEncerrado());
		Assert.assertFalse(leilao2.isEncerrado());
		Assert.assertEquals(2, encerrador.getEncerrados().size());
	}
}
