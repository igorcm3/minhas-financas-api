/**
 * 
 */
package com.igorcm.minhasfinancas.model.entity;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
public enum StatusLancamento {
	PENDENTE("Pendente"),
	CANCELADO("Cancelado"),
	EFETIVADO("Efetivado");
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	private StatusLancamento(String descricao) {
		this.descricao = descricao;
	}
}
