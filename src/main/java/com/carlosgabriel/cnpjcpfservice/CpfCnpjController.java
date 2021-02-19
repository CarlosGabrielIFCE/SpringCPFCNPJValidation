package com.carlosgabriel.cnpjcpfservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CpfCnpjController {

	private static final String cpfTemplate = "CPF é válido:  %s";
	private static final String cnpjTemplate = "CNPJ é válido: %s";
	private static final String requisitionValueTemplate = "Valor total de Requisições: R$ %s";
	private final AtomicLong counter = new AtomicLong();

	/**
	 * Requisição POST de Validação de CPF
	 * @param value
	 * @return
	 */
	@PostMapping(value = "/postCpf", consumes = "application/json", produces = "application/json")
    public String postCPF(@RequestBody Cpf value) {
		Message result = new Message(counter.incrementAndGet(), String.format(cpfTemplate, value.isValid() ? "Sim" : "Não"));
        return "{\"result\": {\"msg\": \"" +  result.getContent() + "\", \"isValid\": " + (value.isValid() ? "true" : "false")  + "}}";
    }
	
	/**
	 * Requisição POST de Validação de CNPJ
	 * @param value
	 * @return
	 */
	@PostMapping(value = "/postCnpj", consumes = "application/json", produces = "application/json")
    public String postCNPJ(@RequestBody Cnpj value) {
		Message result = new Message(counter.incrementAndGet(), String.format(cnpjTemplate, value.isValid() ? "Sim" : "Não"));
        return "{\"result\": {\"msg\": \"" +  result.getContent() + "\", \"isValid\": " + (value.isValid() ? "true" : "false")  + "}}";
    }
	
	/**
	 * Requisição GET para retornar o valor em reais das requisições.
	 * @param value
	 * @return
	 */
	@GetMapping(value = "/requisitions", produces = "application/json")
    public String getRequisitionsValue(@RequestParam(value = "month", defaultValue = "01") String value) {
		System.out.println(counter.get());
		double total = counter.doubleValue() * 0.10;
		Message result = new Message(counter.get(), String.format(requisitionValueTemplate, total));
        return "{\"result\": {\"msg\": \"" +  result.getContent() + "\"}}";
    }
	
	/**
	 * Requisição GET de Validação de CPF
	 * @param value
	 * @return
	 */
	@GetMapping(value = "/cpf")
	public Message cpfMessage(@RequestParam(value = "value", defaultValue = "0000000000") String value) {
		Cpf cpf = new Cpf(counter.incrementAndGet(), value);
		return new Message(counter.get(), String.format(cpfTemplate, !cpf.getValue().equals("") ? "Sim" : "Não"));
	}
	
	/**
	 * Requisição GET de Validação de CNPJ
	 * @param value
	 * @return
	 */
	@GetMapping("/cnpj")
	public Message cnpjMessage(@RequestParam(value = "value", defaultValue = "00000000000000") String value) {
		Cnpj cnpj = new Cnpj(counter.incrementAndGet(), value);
		return new Message(counter.get(), String.format(cnpjTemplate, !cnpj.getValue().equals("") ? "Sim" : "Não"));
	}
	
}
