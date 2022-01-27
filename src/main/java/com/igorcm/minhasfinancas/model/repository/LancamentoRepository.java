/**
 * 
 */
package com.igorcm.minhasfinancas.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.igorcm.minhasfinancas.model.entity.Lancamento;
import com.igorcm.minhasfinancas.model.enums.TipoLancamento;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

	@Query(value = 
			" select sum(lanc.valor) from Lancamento lanc  "
			+ " inner join lanc.usuario user "
			+ " where user.id = :idUsuario "
			+ " and lanc.tipoLancamento = :tipo "
			+ " group by user ")
	BigDecimal getSaldoByTipoLancamentoAndUsuario(@Param("idUsuario") Long idUsuario, @Param("tipo") TipoLancamento tipo);
}
