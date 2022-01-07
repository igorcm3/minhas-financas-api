/**
 * 
 */
package com.igorcm.minhasfinancas.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igorcm.minhasfinancas.exception.ErroAutenticacao;
import com.igorcm.minhasfinancas.exception.RegraNegocioException;
import com.igorcm.minhasfinancas.model.entity.Usuario;
import com.igorcm.minhasfinancas.model.repository.UsuarioRepository;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */

@Service
public class UsuarioServiceImp implements UsuarioService{

	
	private UsuarioRepository usuarioRepository; 
	
	public UsuarioServiceImp(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
		}
		
		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida.");
		}
		
		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return usuarioRepository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		if(usuarioRepository.existsByEmail(email)) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		}
		
	}

	
}
