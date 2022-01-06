/**
 * 
 */
package com.igorcm.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igorcm.minhasfinancas.model.entity.Lancamento;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
