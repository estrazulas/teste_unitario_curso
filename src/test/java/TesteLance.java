import org.junit.Test;

import controller.LanceValorInvalido;
import exception.UsuarioNulloException;
import modelo.Lance;
import modelo.Usuario;

public class TesteLance extends BaseTeste {

	@Test(expected = UsuarioNulloException.class)
	public void deveGerarExcecaoComLanceSemUsuario()
			throws  UsuarioNulloException, LanceValorInvalido {
		logMethodName();
		new Lance(null, 2000.00);
	}
	@Test(expected = LanceValorInvalido.class)
	public void deveGerarExecaoComValorInvalido()
			throws LanceValorInvalido, UsuarioNulloException {
		logMethodName();
		new Lance(new Usuario("teste"), -1.00);
	}
	
	
}
