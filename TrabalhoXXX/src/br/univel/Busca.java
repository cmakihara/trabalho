package br.univel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Busca implements Banco {
	
	private Connection con;
	
	public Busca(){
		
		 conectar(); 
			
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
		    carregarTodos();
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
		                //c.setAno(rs.getString(3));
		               // c.setValor(rs.getString(4));
		                lista.add(c);
		            }
		            
		        } catch (SQLException ex) {
		            Logger.getLogger(Busca.class.getName()).log(Level.SEVERE, null, ex);
		        }
		        return lista;
		    }


		
		
	}
