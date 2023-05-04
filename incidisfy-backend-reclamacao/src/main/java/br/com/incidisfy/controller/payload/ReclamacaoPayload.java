package br.com.incidisfy.controller.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReclamacaoPayload {

	private String codigo;
	
	private long codigoCliente;
	private int codigoCategoria;
	private long codigoProduto;
	private String descricao;
	
	private boolean statusAberto;
	private boolean reincidente;
	
}
