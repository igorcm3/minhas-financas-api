/**
 * 
 */
package com.igorcm.minhasfinancas.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.igorcm.minhasfinancas.exception.RegraNegocioException;
import com.igorcm.minhasfinancas.model.entity.Usuario;
import com.igorcm.minhasfinancas.model.repository.UsuarioRepository;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	UsuarioRepository usuarioRepository;

	@BeforeEach
	public void setUp() {
		usuarioRepository = Mockito.mock(UsuarioRepository.class);
		usuarioService = new UsuarioServiceImp(usuarioRepository);
	}
	
	@Test
	public void deveValidarEmail() {
		// cenário
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

		// ação - verificação
		Assertions.assertDoesNotThrow(() -> usuarioService.validarEmail("email@email.com"));	

	}

	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		// cenário
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

		// ação
		RegraNegocioException thrown = Assertions.assertThrows(RegraNegocioException.class, () -> {
			usuarioService.validarEmail("email@email.com");
		}, "RegraNegocioException error was expected");

		// Verificação
		Assertions.assertEquals("Já existe um usuário cadastrado com este email.", thrown.getMessage());

	}

}
