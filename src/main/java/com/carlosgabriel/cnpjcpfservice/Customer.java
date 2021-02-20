package com.carlosgabriel.cnpjcpfservice;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * Entidade referente ao Cliente que faz a requisição
 * de validação de CPF/CNPJ
 * @author Carlos Gabriel
 *
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "payment")
	private Double payment = 0.0;
	
	@OneToMany(mappedBy="customer")
	private Set<Solicitation> solicitations;

	public Customer() {}
	
	public Customer(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Set<Solicitation> getSolicitations() {
		return solicitations;
	}

	public void setSolicitations(Set<Solicitation> solicitations) {
		this.solicitations = solicitations;
	}
}
