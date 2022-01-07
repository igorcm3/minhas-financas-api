/**
 * 
 */
package com.igorcm.minhasfinancas.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igorcm.minhasfinancas.model.entity.Usuario;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	boolean existsByEmail(String email);
	
	Optional<Usuario> findByEmail(String email);

	
}
