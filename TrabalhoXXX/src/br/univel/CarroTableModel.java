package br.univel;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

public class CarroTableModel extends AbstractTableModel{
	
	private List<Carro> itens = new ArrayList<>();
	
	private Connection con;	
	private List<Carro> lista;
	public CarroTableModel(List<Carro> lista) {
		this.lista = lista;
		conectar();
	}
	//
	
	
	Carro c =new Carro();
	public void conectar() {
	 	String url = "jdbc:postgresql://localhost:5432/postgres";
	    String user = "postgres";
	    String pass = "sa";
	    
	    try {
	        con = DriverManager.getConnection(url, user, pass);
	    } catch (SQLException ex) {
	        Logger.getLogger(BancoSql.class.getName()).log(Level.SEVERE, null, ex);
	    }
	  //  inseriTabela();
	}
	

	public String getColumnName(int column){
		
		
		
		
		switch(column){
		
		case 0 : return "ID";
		case 1 : return "Nome do carro";
		case 2 : return "Ano";
		case 3 : return "Valor";
		
		
		}
		return null;		
		
}

	@Override
	public int getColumnCount() {
		Class<?> clazz = c.getClass();
		for (Field f : clazz.getDeclaredFields()) {
				
		}
		return clazz.getDeclaredFields().length ;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return itens.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Carro c = itens.get(row);
		
			
			String sql = "INSERT INTO CARRO (ID, NOME, ANO, VALOR) VALUES ("+c.getId()+"," +"'" +c.getNome()+ "'" +      ","      +c.getAno()+ ","+c.getValor()+" )"; 
			System.out.println(sql);
		    PreparedStatement ps;
		    try {
		        
		        ps = con.prepareStatement(sql);
		        ResultSet rs = ps.executeQuery();
		       
		        
		    } catch (SQLException ex) {
		        Logger.getLogger(ExcluiTabela.class.getName()).log(Level.SEVERE, null, ex);
		    }
		    			

		
		switch(column){
		case 0:
			return c.getId();
		case 1:
			return c.getNome();
		case 2:
			return c.getAno();
		case 3 :
			return  "R$ "+ c.getValor();
		
		}
		
		return "oadkflo";
	
	}
	public void addNovoCarro(Carro d1) {
		itens.add(d1);
		
		super.fireTableDataChanged();
				
	}
	

}
