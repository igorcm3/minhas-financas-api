/**
 * 
 */
package com.igorcm.minhasfinancas.exception;

/**
 * @author <a href="coronaigor@gmail.com">Igor Corona de Matos</a>
 *
 */
public class ErroAutenticacao extends RuntimeException{

	private static final long serialVersionUID = -7762862751133308972L;

	public ErroAutenticacao(String msg) {
		super(msg);
	}
}
