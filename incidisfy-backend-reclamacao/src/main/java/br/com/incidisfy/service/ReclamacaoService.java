package br.com.incidisfy.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.incidisfy.controller.payload.ReclamacaoPayload;
import br.com.incidisfy.persistence.dao.ReclamacaoRepository;
import br.com.incidisfy.persistence.model.Reclamacao;
import br.com.incidisfy.resources.exception.DaoException;

@Service
public class ReclamacaoService {

	@Autowired
	private ReclamacaoRepository repository;
	
	private static final SimpleDateFormat _SDF = new SimpleDateFormat("ddMMyyyy-HHmmss");

	/**
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public ReclamacaoPayload find(String codigo) throws DaoException {
		try {
			Reclamacao reclamacao = this.repository.findById(codigo).get();			
			return ReclamacaoPayload.builder()
					.codigo(reclamacao.getCodigo())
					.codigoCliente(reclamacao.getCodigoCliente())
					.codigoCategoria(reclamacao.getCodigoCategoria())
					.codigoProduto(reclamacao.getCodigoProduto())
					.descricao(reclamacao.getDescricao())
					.reincidente(reclamacao.isReincidente())
					.statusAberto(reclamacao.isStatusAberto())
					.build();						
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @return
	 * @throws DaoException
	 */
	public List<ReclamacaoPayload> findAll() throws DaoException {
		List<ReclamacaoPayload> reclamacaos = new ArrayList<ReclamacaoPayload>();
		try {
			List<Reclamacao> findAll = this.repository.findAll();
			findAll.stream().forEach(reclamacao -> {
				reclamacaos.add(ReclamacaoPayload.builder()
						.codigo(reclamacao.getCodigo())
						.codigoCliente(reclamacao.getCodigoCliente())
						.codigoCategoria(reclamacao.getCodigoCategoria())
						.codigoProduto(reclamacao.getCodigoProduto())
						.descricao(reclamacao.getDescricao())
						.reincidente(reclamacao.isReincidente())
						.statusAberto(reclamacao.isStatusAberto())
						.build());
			});
			return reclamacaos;
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public List<ReclamacaoPayload> findByCodigoCliente(long codigoCliente) throws DaoException {
		List<ReclamacaoPayload> reclamacaos = new ArrayList<ReclamacaoPayload>();
		try {
			List<Reclamacao> findAll = this.repository.findByCodigoCliente(codigoCliente);			
			findAll.stream().forEach(reclamacao -> {
				reclamacaos.add(ReclamacaoPayload.builder()
						.codigo(reclamacao.getCodigo())
						.codigoCliente(reclamacao.getCodigoCliente())
						.codigoCategoria(reclamacao.getCodigoCategoria())
						.codigoProduto(reclamacao.getCodigoProduto())
						.descricao(reclamacao.getDescricao())
						.reincidente(reclamacao.isReincidente())
						.statusAberto(reclamacao.isStatusAberto())
						.build());
			});
			return reclamacaos;					
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	
	/**
	 * 
	 * @param reclamacao
	 * @throws DaoException
	 */
	public void insert(ReclamacaoPayload reclamacao) throws DaoException {
		try {
			this.repository.save(Reclamacao.builder()
					.codigo(this.buildCodigoReclamacao(reclamacao))
					.codigoCliente(reclamacao.getCodigoCliente())
					.codigoCategoria(reclamacao.getCodigoCategoria())
					.codigoProduto(reclamacao.getCodigoProduto())
					.dataCriacao(new Date())
					.statusAberto(true)
					.reincidente(false)
					.descricao(reclamacao.getDescricao())
					.build());
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param reclamacao
	 * @throws DaoException
	 */
	public void update(ReclamacaoPayload reclamacao) throws DaoException {
		try {
			this.repository.save(Reclamacao.builder()
					.codigo(reclamacao.getCodigo())
					.descricao(reclamacao.getDescricao())
					.build());
		} catch (Exception e) {
			throw new DaoException(e.getMessage(), e);
		}
	}
	
	/**
	 * Monta ID da reclamacao
	 * @param reclamacao
	 * @return
	 */
	private String buildCodigoReclamacao(ReclamacaoPayload reclamacao) {
		StringBuilder sb = new StringBuilder();
		sb.append(reclamacao.getCodigoCliente());
		sb.append(reclamacao.getCodigoCategoria());
		sb.append(reclamacao.getCodigoProduto());
		return String.format("%020d", Long.parseLong(sb.toString())) + "_" + _SDF.format(new Date());
	}
}
