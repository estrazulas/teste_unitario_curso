import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class BaseTeste {
	protected static Logger LOGGER;
	
	@BeforeClass
	public static void iniciaLog() {
		System.out.println("-----------------------------");
		System.out.println("-------INICIANDO LOGS--------");
		System.out.println("-----------------------------");

		LOGGER = Logger.getLogger(TesteAvaliador.class.getSimpleName());

	}

	@AfterClass
	public static void finalizaLog() {
		System.out.println("-----------------------------");
		System.out.println("-------FINALIZANDO-LOGS------");
		System.out.println("-----------------------------");

		LOGGER = null;
	}
	
	public void logMethodName() {
		LOGGER.info(Thread.currentThread().getStackTrace()[2].getMethodName());
	}
}
