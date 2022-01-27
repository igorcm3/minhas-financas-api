/**
 * 
 */
package com.igorcm.minhasfinancas.api.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igorcm.minhasfinancas.api.dto.UsuarioDTO;
import com.igorcm.minhasfinancas.model.entity.Usuario;
import com.igorcm.minhasfinancas.service.LancamentoService;
import com.igorcm.minhasfinancas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;
	private final LancamentoService lancamentoService;

	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
		try {
			Usuario usuarioAutenticado = usuarioService.autenticar(dto.getEmail(), dto.getSenha());
			return  ResponseEntity.ok(usuarioAutenticado);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
	
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();

		try {
			Usuario usuarioSalvo = usuarioService.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("{id}/saldo")
	public ResponseEntity getSaldo(@PathVariable("id") Long idUsuario) {
		Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);
		
		if(!usuario.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		BigDecimal saldo = lancamentoService.getSaldoByUsuario(idUsuario);
		return ResponseEntity.ok(saldo);
	}

}
