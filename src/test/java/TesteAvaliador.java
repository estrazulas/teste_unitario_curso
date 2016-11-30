
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Avaliador;
import controller.DoisLancesSeq;
import controller.LanceValorInvalido;
import controller.LeilaoSemLancesException;
import exception.UsuarioNulloException;
import modelo.Lance;
import modelo.Leilao;
import modelo.Usuario;
import teste.LeilaoBuilder;
import util.matchers.NaoPossuiSequencia;

public class TesteAvaliador extends BaseTeste {
	double maiorValorEsperado = 3000.00d;
	double menorValorEsperado = 1000.00d;
	private List<Lance> lances;
	private Leilao leilao;

	@After
	public void limpaDados() {
		logMethodName();
		leilao = null;
		lances = null;
	}

	@Before
	public void instanciaDados() {
		logMethodName();
		leilao = new Leilao();
		lances = new ArrayList<>();

	}

	@Test
	public void devePermitirLeilaoUnicoLance()
			throws LeilaoSemLancesException, UsuarioNulloException, LanceValorInvalido, DoisLancesSeq {
		logMethodName();
		lances.add(new Lance(new Usuario("João"), 1000.00));

		leilao.setData(new Date());
		leilao.setLances(lances);
		leilao.setNome("Leilão do fusca");
		Avaliador avaliador = new Avaliador();
		avaliador.avalia(leilao);
		assertEquals(lances.size(), 1);
	}

	@Test(expected = LeilaoSemLancesException.class)
	public void naoDevePermitirLeilaoSemLances()
			throws LeilaoSemLancesException, UsuarioNulloException, LanceValorInvalido, DoisLancesSeq {
		logMethodName();
		leilao.setNome("Leilão do fusca");
		leilao.setData(new Date());

		leilao.setLances(lances);

		Avaliador avaliador = new Avaliador();
		avaliador.avalia(leilao);
	}

	@Test
	public void deveEntenderOrdemCrescente()
			throws LeilaoSemLancesException, UsuarioNulloException, LanceValorInvalido, DoisLancesSeq {
		logMethodName();
		lances.add(new Lance(new Usuario("João"), 1000.00));
		lances.add(new Lance(new Usuario("José"), 2000.00));
		lances.add(new Lance(new Usuario("Maria"), 3000.00));

		leilao.setData(new Date());
		leilao.setNome("Leilão do fusca");
		leilao.setLances(lances);

		Avaliador avaliador = new Avaliador();
		avaliador.avalia(leilao);

		assertThat(avaliador.getMenorLance(), is(equalTo(menorValorEsperado)));
		assertThat(avaliador.getMaiorLance(), is(equalTo(maiorValorEsperado)));
	}

	@Test
	public void deveEntenderOrdemDecrescente()
			throws LeilaoSemLancesException, UsuarioNulloException, LanceValorInvalido, DoisLancesSeq {
		logMethodName();
		lances.add(new Lance(new Usuario("João"), 3000.00));
		lances.add(new Lance(new Usuario("José"), 2000.00));
		lances.add(new Lance(new Usuario("Maria"), 1000.00));

		leilao.setData(new Date());
		leilao.setNome("Leilão do fusca");
		leilao.setLances(lances);

		Avaliador avaliador = new Avaliador();
		avaliador.avalia(leilao);

		assertEquals(menorValorEsperado, avaliador.getMenorLance(), 0.00000001);
		assertEquals(maiorValorEsperado, avaliador.getMaiorLance(), 0.00000001);
	}

	@Test
	public void deveEntenderOrdemAlearoria()
			throws LeilaoSemLancesException, UsuarioNulloException, LanceValorInvalido, DoisLancesSeq {
		logMethodName();
		lances.add(new Lance(new Usuario("José"), 2000.00));
		lances.add(new Lance(new Usuario("João"), 1000.00));
		lances.add(new Lance(new Usuario("Maria"), 3000.00));

		leilao.setData(new Date());
		leilao.setNome("Leilão do fusca");

		leilao.setLances(lances);

		Avaliador avaliador = new Avaliador();
		avaliador.avalia(leilao);

		assertEquals(menorValorEsperado, avaliador.getMenorLance(), 0.00000001);
		assertEquals(maiorValorEsperado, avaliador.getMaiorLance(), 0.00000001);
	}

	@Test
	public void deveEncontrarTresMaiores() throws UsuarioNulloException, LanceValorInvalido {
		logMethodName();
		lances.add(new Lance(new Usuario("José"), 2000.00));
		lances.add(new Lance(new Usuario("João"), 1000.00));
		lances.add(new Lance(new Usuario("Maria"), 3000.00));
		lances.add(new Lance(new Usuario("Vitor"), 4000.00));
		lances.add(new Lance(new Usuario("Daniel"), 1000.00));

		leilao.setData(new Date());
		leilao.setNome("Leilão do fusca");

		leilao.setLances(lances);

		Avaliador avaliador = new Avaliador();
		List<Lance> tresMaiores = avaliador.tresMaioresLances(leilao);

		/*
		 * List<Lance> tresMaioresAceitos = new ArrayList<>();
		 * tresMaioresAceitos.add(new Lance(new Usuario("Vitor"), 4000.00));
		 * tresMaioresAceitos.add(new Lance(new Usuario("Maria"), 3000.00));
		 * tresMaioresAceitos.add(new Lance(new Usuario("José"), 2000.00));
		 * 
		 * for (Iterator<Lance> iterator = tresMaioresAceitos.iterator();
		 * iterator.hasNext();) { Lance lance = (Lance) iterator.next();
		 * assertEquals(tresMaiores.get(tresMaioresAceitos.indexOf(lance)).
		 * getValor(), lance.getValor(), 00000.1d); }
		 */

		assertThat(tresMaiores, hasItems(new Lance(new Usuario("Vitor"), 4000.00),
				new Lance(new Usuario("Maria"), 3000.00), new Lance(new Usuario("José"), 2000.00)));

	}

	@Test
	public void deveEncontrarDoisMaiores() throws UsuarioNulloException, LanceValorInvalido {
		logMethodName();

		lances.add(new Lance(new Usuario("José"), 1000.00));
		lances.add(new Lance(new Usuario("João"), 5000.00));

		leilao.setData(new Date());
		leilao.setNome("Leilão do fusca");

		leilao.setLances(lances);
		Avaliador avaliador = new Avaliador();
		List<Lance> tresMaiores = avaliador.tresMaioresLances(leilao);

		assertThat(tresMaiores,
				hasItems(new Lance(new Usuario("João"), 5000.00), new Lance(new Usuario("José"), 1000.00)));

	}

	@Test
	public void deveEncontrarDoisMaioresBuilder() throws UsuarioNulloException, LanceValorInvalido {
		logMethodName();
		Leilao leilao = getLeilaoBuilder().comLance("José", 1000.00).comLance("João", 5000.00).build();

		Avaliador avaliador = new Avaliador();

		List<Lance> tresMaiores = avaliador.tresMaioresLances(leilao);

		List<Lance> doisMaioresAceitos = new ArrayList<>();
		doisMaioresAceitos.add(new Lance(new Usuario("João"), 5000.00));
		doisMaioresAceitos.add(new Lance(new Usuario("José"), 1000.00));

		for (Iterator<Lance> iterator = doisMaioresAceitos.iterator(); iterator.hasNext();) {
			Lance lance = (Lance) iterator.next();
			assertEquals(tresMaiores.get(doisMaioresAceitos.indexOf(lance)).getValor(), lance.getValor(), 00000.1d);
		}
	}

	private LeilaoBuilder getLeilaoBuilder() {
		return new LeilaoBuilder().comData(new Date()).comNome("Leilao do fusca");
	}

	@Test()
	public void deveGerarExcecaoLancesEmSequencia() throws UsuarioNulloException, LanceValorInvalido {
		logMethodName();

		lances.add(new Lance(new Usuario("José"), 1000.00));
		lances.add(new Lance(new Usuario("João"), 5000.00));
		this.leilao.setLances(lances);
		
		assertThat(leilao,NaoPossuiSequencia.naoPossuiSequencia(new Lance(new Usuario("João"), 5000.00)));
	}



}
