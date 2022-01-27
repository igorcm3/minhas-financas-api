/**
 * 
 */
package com.igorcm.minhasfinancas.service;

import java.math.BigDecimal;
import java.util.Optional;

import com.igorcm.minhasfinancas.model.entity.Usuario;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
	Optional<Usuario> findById(Long id);
	
}
