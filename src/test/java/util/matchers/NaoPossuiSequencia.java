package util.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import modelo.Lance;
import modelo.Leilao;

public class NaoPossuiSequencia extends TypeSafeMatcher<Leilao> {
	private Lance lance;

	public NaoPossuiSequencia(Lance lance) {
		this.lance = lance;
	}

	public boolean matchesSafely(Leilao leilao) {
		String ultimoUsuario = leilao.getLances().get(leilao.getLances().size()-1).getUsuario().getNome();
		return ultimoUsuario.equals(lance.getUsuario().getNome());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("lance em sequencia para usuario ").appendValue(lance.getUsuario().getNome());
	}
	
	@Factory
	public static Matcher<Leilao> naoPossuiSequencia( Lance lance ) {
	    return new NaoPossuiSequencia(lance);
	}

}