/**
 * 
 */
package com.igorcm.minhasfinancas.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.igorcm.minhasfinancas.model.entity.Usuario;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//cenário
		Usuario user = criarUsuario();
		entityManager.persist(user);
		
		//ação / execução
		boolean result = usuarioRepository.existsByEmail("usuario@email.com");
		
		//verificação
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail() {
		//cenário
		
		//ação / execução
		boolean result = usuarioRepository.existsByEmail("usuario@email.com");
		
		//verificação
		Assertions.assertThat(result).isFalse();		
	}
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		
		// cenário
		Usuario user = criarUsuario() ;
		
		//ação
		usuarioRepository.save(user);
		
		Assertions.assertThat(user.getId()).isNotNull();
			
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		// cenário
		Usuario user = criarUsuario();
		entityManager.persist(user);
		
		//ação
		Optional<Usuario> userSalvo  = usuarioRepository.findByEmail("usuario@email.com");
		
		//verificação
		Assertions.assertThat(userSalvo.isPresent()).isTrue();
		
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		// cenário
		
		//ação
		Optional<Usuario> userSalvo  = usuarioRepository.findByEmail("usuario@email.com");
		
		//verificação
		Assertions.assertThat(userSalvo.isPresent()).isFalse();
		
	}	
	
	public static Usuario criarUsuario() {
		return Usuario
				.builder()
				.nome("usuario")
				.email("usuario@email.com")
				.senha("senha")
				.build();
	}
	
	
	
}
