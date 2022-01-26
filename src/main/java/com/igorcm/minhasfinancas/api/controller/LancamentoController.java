/**
 * 
 */
package com.igorcm.minhasfinancas.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igorcm.minhasfinancas.api.dto.LancamentoDTO;
import com.igorcm.minhasfinancas.exception.RegraNegocioException;
import com.igorcm.minhasfinancas.model.entity.Lancamento;
import com.igorcm.minhasfinancas.model.entity.Usuario;
import com.igorcm.minhasfinancas.model.enums.StatusLancamento;
import com.igorcm.minhasfinancas.model.enums.TipoLancamento;
import com.igorcm.minhasfinancas.service.LancamentoService;
import com.igorcm.minhasfinancas.service.UsuarioService;

import lombok.RequiredArgsConstructor;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

	private final LancamentoService lancamentoService;
	private final UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
		try {
			Lancamento entidade = this.converter(dto);
			entidade = this.lancamentoService.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
		return lancamentoService.findById(id).map( entity -> {
			try {
				Lancamento lancamento = converter(dto);
				lancamento.setId(id);
				this.lancamentoService.atualizar(lancamento);
				return ResponseEntity.ok(lancamento);				
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}

		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return lancamentoService.findById(id).map( entity -> {
			try {
				this.lancamentoService.deletar(entity);
				return new ResponseEntity(HttpStatus.NO_CONTENT);				
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}

		}).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	@GetMapping
	public ResponseEntity buscar(@RequestParam(value= "descricao", required = false) String descricao, 
			@RequestParam(value = "mes", required = false) Integer mes, 
			@RequestParam(value = "ano", required = false) Integer ano, 
			@RequestParam("usuario") Long idUsuario) {
		Lancamento lancamentoFiltro = new Lancamento();
		lancamentoFiltro.setDescricao(descricao);
		lancamentoFiltro.setMes(mes);
		lancamentoFiltro.setAno(ano);
		
		Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);
		
		if(!usuario.isPresent()) {
			 return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o Id informado.");
		}
		
		lancamentoFiltro.setUsuario(usuario.get());
		List<Lancamento> lancamentos = this.lancamentoService.buscar(lancamentoFiltro);
		
		return ResponseEntity.ok(lancamentos);
				
	}

	private Lancamento converter(final LancamentoDTO dto) {
		Lancamento lancamento = new Lancamento();
		lancamento.setDescricao(dto.getDescricao());
		lancamento.setAno(dto.getAno());
		lancamento.setMes(dto.getMes());
		lancamento.setValor(dto.getValor());
		Usuario usuario = this.usuarioService.findById(dto.getUsuario())
				.orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o Id informado."));

		lancamento.setUsuario(usuario);
		if(dto.getTipo() != null) {
			lancamento.setTipoLancamento(TipoLancamento.valueOf(dto.getTipo()));
		}
		
		if(dto.getStatus() != null) {
			lancamento.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus()));
		}
		return lancamento;
	}

}
