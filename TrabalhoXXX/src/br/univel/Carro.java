package br.univel;

import java.math.BigDecimal;


@Tabela("CARRO")
public class Carro {
	
	@Coluna(pk=true)
	private int id;
	
	@Coluna(nome="NOME")
	private String nome;
	@Coluna(nome="ANO")
	private int ano;
	@Coluna(nome="VALOR")
	private BigDecimal valor;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	@Override
    public String toString() {
        return "Carro{" + "id=" + id + ", nome=" + nome +", ano=" + ano + ", valor=" + valor +'}';
    }

}
