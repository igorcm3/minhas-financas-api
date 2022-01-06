/**
 * 
 */
package com.igorcm.minhasfinancas.model.enums;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
public enum TipoLancamento {

	RECEITA("Receita"),
	DESPESA("Despesa");
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	private TipoLancamento(String nome) {
		this.nome = nome;
	}

	private String nome;
}
