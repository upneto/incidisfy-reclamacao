/**
 * 
 */
package br.com.fiap.fmba.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.incidisfy.controller.payload.ReclamacaoPayload;
import br.com.incidisfy.persistence.dao.ReclamacaoRepository;
import br.com.incidisfy.persistence.model.Reclamacao;
import br.com.incidisfy.resources.exception.DaoException;
import br.com.incidisfy.service.ReclamacaoService;

/**
 * @author Ulisses Neto
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TestReclamacaoService {
	
	@Mock
	private ReclamacaoRepository mockRepository;

	@InjectMocks
	private ReclamacaoService service;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
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
	 * Test method for {@link br.com.incidisfy.service.ReclamacaoService#find(long)}.
	 * @throws DaoException 
	 */
	@Test
	public void testFind() throws DaoException {
		
		Mockito.when(mockRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getMock()));
		
		ReclamacaoPayload find = service.find("TESTE_123456");

		Assert.assertNotNull(find);
		Assert.assertEquals(getMock().getCodigo(), find.getCodigo());
	}

	/**
	 * Test method for {@link br.com.incidisfy.service.ReclamacaoService#find(long)}.
	 * @throws DaoException 
	 */
	@Test
	public void testFindError() throws DaoException {
		
		Mockito.when(mockRepository.findById(Mockito.anyString())).thenThrow(new RuntimeException());
		
		try {
			service.find("TESTE_123456");			
		} catch (Exception e) {
			Assert.assertTrue(e instanceof DaoException);
		}
	}

	
	/**
	 * Test method for {@link br.com.incidisfy.service.ReclamacaoService#findAll()}.
	 * @throws DaoException 
	 */
	@Test
	public void testFindAll() throws DaoException {
		List<Reclamacao> lista = new ArrayList<Reclamacao>();
		lista.add(getMock());
		
		Mockito.when(mockRepository.findAll()).thenReturn(lista);
		
		List<ReclamacaoPayload> findAll = service.findAll();

		Assert.assertNotNull(findAll);
		Assert.assertEquals(lista.size(), findAll.size());
		Assert.assertEquals(lista.get(0).getCodigo(), findAll.get(0).getCodigo());
	}
	
	/**
	 * Test method for {@link br.com.incidisfy.service.ReclamacaoService#findAll()}.
	 * @throws DaoException 
	 */
	@Test
	public void testFindAllError() {
		List<Reclamacao> lista = new ArrayList<Reclamacao>();
		lista.add(getMock());
		
		Mockito.when(mockRepository.findAll()).thenThrow(new RuntimeException());
		
		try {
			service.findAll();
		} catch (Exception e) {
			Assert.assertTrue(e instanceof DaoException);
		}		
	}

	/**
	 * Test method for {@link br.com.incidisfy.service.ReclamacaoService#update(br.com.incidisfy.persistence.model.Reclamacao)}.
	 * @throws DaoException 
	 */
	@Test
	public void testUpdate() throws DaoException {
		
		service.update(getMockPayload());
		
		verify(mockRepository, times(1)).save(Mockito.any(Reclamacao.class));
	}

}
