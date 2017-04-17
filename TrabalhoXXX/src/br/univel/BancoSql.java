package br.univel;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;





public class BancoSql implements Banco{
	
	private Connection con;
	
	public BancoSql(){
		
		
	 conectar();     
     Carro carro = new Carro();
     
     
     
     List<Carro> lista = carregarTodos();
    
     try {

		
			PreparedStatement ps = getPreparedStatementForInset(con, carro);

			ps.executeUpdate();

			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}

	
     carregarTodos();
     
     
	
}
	
	
public void conectar() {
 	String url = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String pass = "sa";
    
    try {
        con = DriverManager.getConnection(url, user, pass);
    } catch (SQLException ex) {
        Logger.getLogger(BancoSql.class.getName()).log(Level.SEVERE, null, ex);
    }
    
	
}


private PreparedStatement getPreparedStatementForInset(Connection con, Object obj) {

	Class<? extends Object> cl = obj.getClass();

	StringBuilder sb = new StringBuilder();

	// Declaração da tabela.
	{
		String nomeTabela;
		if (cl.isAnnotationPresent(Tabela.class)) {

			Tabela anotacaoTabela = cl.getAnnotation(Tabela.class);
			nomeTabela = anotacaoTabela.value();

		} else {
			nomeTabela = cl.getSimpleName().toUpperCase();

		}
		sb.append("INSERT INTO ").append(nomeTabela).append(" (");
	}

	Field[] atributos = cl.getDeclaredFields();


	{
		for (int i = 0; i < atributos.length; i++) {

			Field field = atributos[i];

			String nomeColuna;

			if (field.isAnnotationPresent(Coluna.class)) {
				Coluna anotacaoColuna = field.getAnnotation(Coluna.class);

				if (anotacaoColuna.nome().isEmpty()) {
					nomeColuna = field.getName().toUpperCase();
				} else {
					nomeColuna = anotacaoColuna.nome();
				}

			} else {
				nomeColuna = field.getName().toUpperCase();
			}

			if (i > 0) {
				sb.append(", ");
			}

			sb.append(nomeColuna);
		}
	}

	sb.append(") VALUES (");
	Carro c =new Carro();
	
	Class<?> clazz = c.getClass();

	Field[] vetorFields = clazz.getDeclaredFields();
	for (int i = 0; i < atributos.length; i++) {
		if (i > 0) {
			sb.append(", ");
		}
		for (Field f : clazz.getDeclaredFields()) {

		try {

			
			f.setAccessible(true);

			String nome = f.getName();
		//	Object valor = f.get(obj);
			sb.append(f.get(c));
			
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	}
	sb.append(')');

	String strSql = sb.toString();
	System.out.println("SQL GERADO: " + strSql);
	PreparedStatement ps = null;
	try {
		ps = con.prepareStatement(strSql);

		for (int i = 0; i < atributos.length; i++) {
			Field field = atributos[i];

		
			field.setAccessible(true);

			if (field.getType().equals(int.class)) {
				ps.setInt(i + 1, field.getInt(obj));

			} else if (field.getType().equals(String.class)) {
				ps.setString(i + 1, String.valueOf(field.get(obj)));

			} else {
				throw new RuntimeException("Tipo não suportado, falta implementar.");

			}
		}

	} catch (SQLException e) {
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	}

	return ps;
	
	
}

@Override
public List<Carro> carregarTodos() {
	List<Carro> lista = new ArrayList<>();
    String sql = "select * from aluno";
    PreparedStatement ps;
    try {
        
        ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Carro c = new Carro();
            c.setId(rs.getInt(1));
            c.setNome(rs.getString(2));
            lista.add(c);
        }
        
    } catch (SQLException ex) {
        Logger.getLogger(BancoSql.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lista;

}

}
