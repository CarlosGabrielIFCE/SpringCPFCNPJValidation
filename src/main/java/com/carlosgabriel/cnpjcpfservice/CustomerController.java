package com.carlosgabriel.cnpjcpfservice;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Customers Controller
 * Contains all REST Functions from Customers
 * @author Carlos Gabriel
 *
 */
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private SolicitationRepository solicitationRepository;
	
	/**
	 * Return all customers from the database
	 * @param mail
	 * @return
	 */
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String mail) {
		try {
			List<Customer> customers = new ArrayList<Customer>();

			if (mail == null)
				customerRepository.findAll().forEach(customers::add);
			else
				customerRepository.findByEmail(mail).forEach(customers::add);

			if (customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(customers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Return a Customer from the database
	 * @param id
	 * @return
	 */
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") long id) {
		Optional<Customer> customerData = customerRepository.findById(id);

		if (customerData.isPresent()) {
			return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Return a Customer's total payment value
	 * @param id
	 * @return
	 */
	@GetMapping("/customers/{id}/payment")
	public CustomerPaymentJson getCustomerPaymentById(@PathVariable("id") long id) {
		Optional<Customer> customerData = customerRepository.findById(id);
		final CustomerPaymentJson returnValue = new CustomerPaymentJson();
		if (customerData.isPresent()) {
			returnValue.setPaymentValue(customerData.get().getPayment());
			returnValue.setStatus("Success returned value.");
		} else {
			returnValue.setPaymentValue(0.0);
			returnValue.setStatus("Customer is not present in our database...");
		}
		return returnValue;
	}
	
	/**
	 * Return a Customer's month payment value
	 * @param id
	 * @param month
	 * @return
	 */
	@GetMapping("/customers/{id}/payment/{month}")
	public CustomerPaymentJson getCustomerPaymentByMonth(@PathVariable("id") long id, @PathVariable String month) {
		Optional<Customer> customerData = customerRepository.findById(id);
		final CustomerPaymentJson returnValue = new CustomerPaymentJson();
		Double paymentMonthValue = 0.0;
		Integer monthValue = Integer.parseInt(month);
		if (customerData.isPresent()) {
			List<Solicitation> solicitations = solicitationRepository.findByCustomerId(id);
			for (Solicitation sol: solicitations) {
				if (sol.getSolicitationDate() == monthValue) {
					paymentMonthValue += 0.1;
				}
			}
			returnValue.setPaymentValue(paymentMonthValue);
			returnValue.setStatus("Success returned value.");
		} else {
			returnValue.setPaymentValue(0.0);
			returnValue.setStatus("Customer is not present in our database...");
		}
		return returnValue;
	}

	/**
	 * Add a Customer
	 * @param customer
	 * @return
	 */
	@PostMapping("/customers")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		try {
			Customer _customer = customerRepository.save(new Customer(customer.getEmail(), customer.getPassword()));
			return new ResponseEntity<>(_customer, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update a Customer
	 * @param id
	 * @param customer
	 * @return
	 */
	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
		Optional<Customer> customerData = customerRepository.findById(id);

		if (customerData.isPresent()) {
			Customer _customer = customerData.get();
			_customer.setEmail(customer.getEmail());
			_customer.setPassword(customer.getPassword());
			return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Delete a Customer by Id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
		try {
			customerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Delete all Customers from the base
	 * @return
	 */
	@DeleteMapping("/customers")
	public ResponseEntity<HttpStatus> deleteAllCustomers() {
		try {
			customerRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
