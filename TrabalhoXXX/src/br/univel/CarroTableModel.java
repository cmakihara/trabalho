package br.univel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CarroTableModel extends AbstractTableModel{
	
	private List<Carro> itens = new ArrayList<>();
	
	//
	private List<Carro> lista;
	public CarroTableModel(List<Carro> lista) {
		this.lista = lista;
	}
	//
	
	
	Carro c =new Carro();
	
	

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
