/**
 * 
 */
package br.com.fiap.fmba.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import br.com.incidisfy.controller.api.ReclamacaoController;
import br.com.incidisfy.controller.payload.ReclamacaoPayload;
import br.com.incidisfy.persistence.model.Reclamacao;
import br.com.incidisfy.resources.exception.DaoException;
import br.com.incidisfy.service.ReclamacaoService;

/**
 * @author Ulisses Neto
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestReclamacaoController {

	@Mock
	private ReclamacaoService mockService;

	@InjectMocks
	private ReclamacaoController controller;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUpBefore() throws Exception {
		MockitoAnnotations.openMocks(this);
	}
	
	public Reclamacao getMock() {
		return Reclamacao.builder()
				.codigo("TESTE_123456")
				.descricao("TESTE DESCRICAO")				
				.build();
	}
	
	public ReclamacaoPayload getMockPayload() {
		return ReclamacaoPayload.builder()
				.codigo("TESTE_123456")
				.descricao("TESTE DESCRICAO")
				.build();
	}

	/**
	 * Test method for
	 * {@link br.com.incidisfy.controller.api.ReclamacaoController#findAll()}.
	 * @throws DaoException 
	 */
	@Test
	public void testFindAll() throws DaoException {
		
		List<ReclamacaoPayload> lista = new ArrayList<ReclamacaoPayload>();
		lista.add(getMockPayload());
		
		Mockito.when(mockService.findAll()).thenReturn(lista);
		
		ResponseEntity<List<ReclamacaoPayload>> findAll = controller.findAll();

		Assert.assertNotNull(findAll);
        Assert.assertEquals(200, findAll.getStatusCode().value());
        Assert.assertEquals(lista.get(0).getCodigo(), findAll.getBody().get(0).getCodigo());
	}

	/**
	 * Test method for
	 * {@link br.com.incidisfy.controller.api.ReclamacaoController#findBy(long)}.
	 * @throws DaoException 
	 */
	@Test
	public void testFindBy() throws DaoException {
		
		Mockito.when(mockService.find(Mockito.anyString())).thenReturn(getMockPayload());
		
		ResponseEntity<ReclamacaoPayload> findBy = controller.findBy("TESTE_123456");

		Assert.assertNotNull(findBy);
        Assert.assertEquals(200, findBy.getStatusCode().value());
        Assert.assertEquals(getMock().getCodigo(), findBy.getBody().getCodigo());
	}

	/**
	 * Test method for
	 * {@link br.com.incidisfy.controller.api.ReclamacaoController#update(br.com.incidisfy.persistence.model.Reclamacao)}.
	 * @throws DaoException 
	 */
	@Test
	public void testUpdate() throws DaoException {
		ResponseEntity<?> update = controller.update(getMockPayload());

		Assert.assertNotNull(update);
        Assert.assertEquals(200, update.getStatusCode().value());
	}
}
