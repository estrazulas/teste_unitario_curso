package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.UsuarioNulloException;
import modelo.Leilao;

public class LeilaoDAO {

	private Connection conexao;

	public LeilaoDAO() {
		try {
			this.conexao = DriverManager.getConnection("jdbc:mysql://localhost:3307/mocks?user=root&password=gtigti");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public Connection getConexao() {
		return conexao;
	}

	public void save(Leilao leilao) {
		PreparedStatement stmt;
		try {
			stmt = conexao.prepareStatement("insert XXXX");
			stmt.executeQuery();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<Leilao> naoConcorrentes() {
		PreparedStatement stmt;
		try {
			stmt = conexao.prepareStatement("select ***");
			ResultSet rs = stmt.executeQuery();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	public List<Leilao> getConcorrentes() {
		PreparedStatement stmt;
		try {
			stmt = conexao.prepareStatement("select ***");
			ResultSet rs = stmt.executeQuery();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<>();
	}
	public void update(Leilao leilao) {
		try {
			PreparedStatement stmt = conexao.prepareStatement("update xxxx");
			stmt.executeQuery();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	int i =0;
	int valor =0;
	public int enviarEmail(int base, int pow){
		if(i <pow){
			i++;
			valor *= base;
			return enviarEmail(base, pow);
		}
		return valor;
	}
	public int gerarExcecao() throws UsuarioNulloException{
		throw new UsuarioNulloException();
	}
	public List<Leilao> getEncerrados() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Leilao> getCorrentes() {
		// TODO Auto-generated method stub
		return null;
	}
}
