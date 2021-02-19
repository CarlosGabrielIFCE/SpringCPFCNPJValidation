package com.carlosgabriel.cnpjcpfservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository da Entidade Customer
 * @author Carlos Gabriel
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{
	List<Customer> findByEmail(String value);
}
