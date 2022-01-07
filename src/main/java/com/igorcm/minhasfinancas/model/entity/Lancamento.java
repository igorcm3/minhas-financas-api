/**
 * 
 */
package com.igorcm.minhasfinancas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.igorcm.minhasfinancas.model.enums.StatusLancamento;
import com.igorcm.minhasfinancas.model.enums.TipoLancamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */

@Entity
@Table(name = "lancamento", schema = "financas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "mes")
	private Integer mes;

	@Column(name = "ano")
	private Integer ano;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "dataCadastro")
	private LocalDate dataCadastro;

	@Column(name = "tipo")
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipoLancamento;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusLancamento statusLancamento;

	@Column(name = "descricao")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

}
