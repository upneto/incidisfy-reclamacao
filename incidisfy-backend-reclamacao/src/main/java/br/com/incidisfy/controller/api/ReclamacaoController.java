package br.com.incidisfy.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.incidisfy.controller.payload.ReclamacaoPayload;
import br.com.incidisfy.resources.exception.DaoException;
import br.com.incidisfy.resources.exception.MessageQueueException;
import br.com.incidisfy.service.MessageSenderService;
import br.com.incidisfy.service.ReclamacaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/reclamacao")
public class ReclamacaoController {

	@Autowired
	private ReclamacaoService service;
	
	@Autowired
	private MessageSenderService messageSender;
	
	@Value("${env}")
	private String env;
	
	/**
	 * 
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "Pesquisa todas as reclamações")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna lista de Reclamaçãos"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
	@GetMapping(produces="application/json")
	public ResponseEntity<List<ReclamacaoPayload>> findAll() throws DaoException {
		return new ResponseEntity<>(this.service.findAll(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "Pesquisa Reclamação por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna Reclamação"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
	@GetMapping(value = "/{idCliente}", produces="application/json", consumes="application/json")
	public ResponseEntity<ReclamacaoPayload> findBy(@PathVariable String id) throws DaoException {
		return new ResponseEntity<>(this.service.find(id), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param codigoCliente
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "Pesquisa Reclamação por codigo do cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna lista de Reclamações"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
	@GetMapping(value = "/cliente/{codigoCliente}", produces="application/json", consumes="application/json")
	public ResponseEntity<List<ReclamacaoPayload>> findByCodigoCliente(@PathVariable long codigoCliente) throws DaoException {
		return new ResponseEntity<>(this.service.findByCodigoCliente(codigoCliente), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param reclamacao
	 * @return
	 * @throws DaoException
	 */
	@ApiOperation(value = "Altera descrição da Reclamação")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Alterou Reclamação com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
	@PutMapping(produces="application/json", consumes="application/json")
	public ResponseEntity<?> update(@RequestBody ReclamacaoPayload reclamacao) throws DaoException {
		this.service.update(reclamacao);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param reclamacao
	 * @return
	 * @throws DaoException
	 * @throws MessageQueueException
	 */
	@ApiOperation(value = "Inclui Reclamação")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Alterou Reclamação com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
	@PostMapping(produces="application/json", consumes="application/json")
	public ResponseEntity<?> insert(@RequestBody ReclamacaoPayload reclamacao) throws DaoException, MessageQueueException {
//		if(this.env.equalsIgnoreCase("P")) {
//			this.messageSender.sendCustomerComplaint(reclamacao);
//		}
//		else {
//			this.service.insert(reclamacao);
//		}
		this.service.insert(reclamacao);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
