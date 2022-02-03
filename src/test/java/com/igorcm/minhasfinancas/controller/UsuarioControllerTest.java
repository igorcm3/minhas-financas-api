package com.igorcm.minhasfinancas.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igorcm.minhasfinancas.api.controller.UsuarioController;
import com.igorcm.minhasfinancas.api.dto.UsuarioDTO;
import com.igorcm.minhasfinancas.exception.ErroAutenticacao;
import com.igorcm.minhasfinancas.exception.RegraNegocioException;
import com.igorcm.minhasfinancas.model.entity.Usuario;
import com.igorcm.minhasfinancas.service.LancamentoService;
import com.igorcm.minhasfinancas.service.UsuarioService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	static final String API = "/api/usuarios";
	static final MediaType JSON =  MediaType.APPLICATION_JSON;
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	UsuarioService service;
	
	@MockBean
	LancamentoService lancamentoService;
	
	@Test
	public void deveAutenticarUmUsuario() throws Exception {
		// cenario
		String email = "usuario@email.com";
		String senha = "123";
		UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
		Usuario user = Usuario.builder().id(1l).email(email).senha(senha).build();
		
		
		Mockito.when(service.autenticar(email, senha)).thenReturn(user);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// execução e verificação
		
		// Objeto que cria uma requisição
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API.concat("/autenticar"))
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		mvc
		.perform(request)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect( MockMvcResultMatchers.jsonPath("id").value(user.getId()) )
		.andExpect( MockMvcResultMatchers.jsonPath("nome").value(user.getNome()) )
		.andExpect( MockMvcResultMatchers.jsonPath("email").value(user.getEmail()) );
		
	}
	
	@Test
	public void deveRetornarBadRequestAoObterErroDeAutenticacao() throws Exception {
		// cenario
		String email = "usuario@email.com";
		String senha = "123";
		UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
		
		Mockito.when(service.autenticar(email, senha)).thenThrow(ErroAutenticacao.class);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// execução e verificação
		
		// Objeto que cria uma requisição
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API.concat("/autenticar"))
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		mvc
		.perform(request)
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void deveCriarUmNovoUsuario() throws Exception {
		// cenario
		String email = "usuario@email.com";
		String senha = "123";
		UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
		Usuario user = Usuario.builder().id(1l).email(email).senha(senha).build();
		
		
		Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(user);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// execução e verificação
		
		// Objeto que cria uma requisição
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API)
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		mvc
		.perform(request)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect( MockMvcResultMatchers.jsonPath("id").value(user.getId()) )
		.andExpect( MockMvcResultMatchers.jsonPath("nome").value(user.getNome()) )
		.andExpect( MockMvcResultMatchers.jsonPath("email").value(user.getEmail()) );
		
	}	
	
	@Test
	public void deveRetornarBadRequestAoTentarCriarUmNovoUsuarioInvalido() throws Exception {
		// cenario
		String email = "usuario@email.com";
		String senha = "123";
		UsuarioDTO dto = UsuarioDTO.builder().email(email).senha(senha).build();
		
		Mockito.when(service.salvarUsuario(Mockito.any(Usuario.class))).thenThrow(RegraNegocioException.class);
		
		String json = new ObjectMapper().writeValueAsString(dto);
		
		// execução e verificação
		
		// Objeto que cria uma requisição
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
													.post(API)
													.accept(JSON)
													.contentType(JSON)
													.content(json);
		mvc
		.perform(request)
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}	
}
