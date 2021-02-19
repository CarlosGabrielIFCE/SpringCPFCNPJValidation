package com.carlosgabriel.cnpjcpfservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitationRepository extends JpaRepository<Solicitation, Long> {
	List<Solicitation> findByCustomerId(Long customerId);
}
