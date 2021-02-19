package com.carlosgabriel.cnpjcpfservice;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsável por lidar com as operações
 * referentes às Solicitações
 * @author Carlos Gabriel
 *
 */
@RestController
@RequestMapping("/api")
public class SolicitationController {
	
	@Autowired
	private SolicitationRepository solicitationRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@GetMapping("/customers/{customerId}/solicitations")
	public ResponseEntity<List<Solicitation>> getSolicitationsByCustomerId(@PathVariable Long customerId) {
		try {
			List<Solicitation> solicitations = new ArrayList<Solicitation>();
			
			if (!customerRepository.existsById(customerId)) {
				throw new NotFoundException("Student not found!");
			}

			if (customerId == null)
				solicitationRepository.findAll().forEach(solicitations::add);
			else
				solicitationRepository.findByCustomerId(customerId).forEach(solicitations::add);

			if (solicitations.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(solicitations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    @PostMapping("/customers/{customerId}/solicitations")
    public ResponseEntity<Solicitation> addSolicitation(@PathVariable Long customerId,
                            @RequestBody Solicitation solicitation) {
    	boolean validado;
    	if (solicitation.getValue().length() == 11) {
    		validado = isCPF(solicitation.getValue());
    	}else {
    		validado = isCNPJ(solicitation.getValue());
    	}
    	Optional<Customer> customerData = customerRepository.findById(customerId);

		if (customerData.isPresent()) {
			solicitation.setCustomer(customerData.get());
			solicitation.setValid(validado);
			Solicitation _solicitation = solicitationRepository.save(solicitation);
			return new ResponseEntity<>(_solicitation, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

	@PutMapping("customers/{customerId}/solicitation/{id}")
	public Solicitation updateSolicitation(@PathVariable Long customerId, @PathVariable long id, @RequestBody Solicitation solicitationUpdated) {
    	if(!customerRepository.existsById(customerId)) {
    		throw new NotFoundException("Customer not found!");
    	}
    	
        return solicitationRepository.findById(id)
                .map(solicitation -> {
                	solicitation.setValue(solicitationUpdated.getValue());
                	solicitation.setDescription(solicitationUpdated.getDescription());
                	solicitation.setValid(solicitationUpdated.isValid());
                    return solicitationRepository.save(solicitation);
                }).orElseThrow(() -> new NotFoundException("Solicitation not found!"));
	}

	@DeleteMapping("customers/{customerId}/solicitation/{id}")
	public String deleteSolicitation(@PathVariable long customerId, @PathVariable("id") long id) {
		if(!customerRepository.existsById(customerId)) {
    		throw new NotFoundException("Customer not found!");
    	}
    	
        return solicitationRepository.findById(id)
                .map(solicitation -> {
                    solicitationRepository.delete(solicitation);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Solicitation not found!"));
	}


	
	/**
	 * Função que verifica se o CPF é valido
	 * 
	 * @param CPF
	 * @return
	 */
	public static boolean isCPF(String CPF) {
		// Verificações simples
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// Tratamento de Exceções
		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}
	
	public static boolean isCNPJ(String CNPJ) {
		// Verificações simples
		if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") || CNPJ.equals("22222222222222")
				|| CNPJ.equals("33333333333333") || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
				|| CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") || CNPJ.equals("88888888888888")
				|| CNPJ.equals("99999999999999") || (CNPJ.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		try {

			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

}
