package br.com.incidisfy.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.incidisfy.persistence.model.Reclamacao;

public interface ReclamacaoRepository extends JpaRepository<Reclamacao, String> {
	
	
	List<Reclamacao> findByCodigoCliente(long codigoCliente);
}
