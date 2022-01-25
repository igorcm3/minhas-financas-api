/**
 * 
 */
package com.igorcm.minhasfinancas.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Objeto para transferencia de informações do Usuario entre front e back end
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
@Getter
@Setter
@Builder
public class UsuarioDTO {
	
	private String email;
	private String nome;
	private String senha;

}
