/**
 * 
 */
package com.igorcm.minhasfinancas.service;

import java.util.List;

import com.igorcm.minhasfinancas.model.entity.Lancamento;
import com.igorcm.minhasfinancas.model.enums.StatusLancamento;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento); 
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento>buscar(Lancamento lancamentoFiltro);
	
	void atualizarStatus(Lancamento lancamento, StatusLancamento status);
	
	void validar(Lancamento lancamento);
}
