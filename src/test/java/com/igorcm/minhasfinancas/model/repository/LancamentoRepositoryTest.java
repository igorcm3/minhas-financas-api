/**
 * 
 */
package com.igorcm.minhasfinancas.model.repository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.igorcm.minhasfinancas.model.entity.Lancamento;
import com.igorcm.minhasfinancas.model.enums.StatusLancamento;
import com.igorcm.minhasfinancas.model.enums.TipoLancamento;

/**
 * Classe de testes para o repositório de lançamentos
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LancamentoRepositoryTest {

	@Autowired 
	LancamentoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveSalvarUmLancamento() {
		// cenário - ação
		Lancamento lancamento = criarEPersistirLancamento();
		//verificação
		Assertions.assertThat(lancamento.getId()).isNotNull();
	}
	
	@Test
	public void deveDeletarUmLancamento() {
		// cenário
		Lancamento lancamento = criarEPersistirLancamento();
		
		//ação
		 lancamento = entityManager.find(Lancamento.class, lancamento.getId());		 
		 repository.delete(lancamento);
		
		// Verificação
		 Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
		 Assertions.assertThat(lancamentoInexistente).isNull();
	}
	
	
	@Test
	public void deveAtualizarUmLancamento() {
		// cenário
		Lancamento lancamento = criarEPersistirLancamento();
		
		lancamento.setAno(2018);
		lancamento.setDescricao("Teste Atualizado");
		lancamento.setStatusLancamento(StatusLancamento.CANCELADO);		
		repository.save(lancamento);
		
		// ação
		Lancamento lancAtt =  entityManager.find(Lancamento.class, lancamento.getId());
		
		//  verificação
		Assertions.assertThat(lancAtt.getAno()).isEqualTo(2018);
		Assertions.assertThat(lancAtt.getDescricao()).isEqualTo("Teste Atualizado");
		Assertions.assertThat(lancAtt.getStatusLancamento()).isEqualTo(StatusLancamento.CANCELADO);
	}
	
	@Test
	public void deveBuscarUmLancamentoPorId() {
		// cenário
		Lancamento lancamento = criarEPersistirLancamento();
		
		Optional<Lancamento> lancamentoEncontrado = repository.findById(lancamento.getId());
		
		Assertions.assertThat(lancamentoEncontrado.isPresent()).isTrue();
		
	}
	

	private Lancamento criarEPersistirLancamento() {
		// cenário
		Lancamento lancamento = criarLancamento();
		lancamento = entityManager.persist(lancamento);
		return lancamento;
	}
	


	private Lancamento criarLancamento() {
		return Lancamento.builder()
				.ano(2019)
				.mes(1)
				.descricao("Lançamento qualquer")
				.valor(BigDecimal.valueOf(10))
				.tipoLancamento(TipoLancamento.RECEITA)
				.statusLancamento(StatusLancamento.PENDENTE)
				.dataCadastro(LocalDate.now())
				.build();
	}
	

	
}
