package com.carlosgabriel.cnpjcpfservice;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entidade referente às solicitações
 * de CPF e CNPJ
 * @author Carlos Gabriel
 *
 */
@Entity
@Table(name = "solicitation")
public class Solicitation implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "is_valid")
	private boolean isValid;
	
	@Column(name = "solicitation_date")
	private int solicitationDate;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	@JsonIgnore
	private Customer customer;
	
	public Solicitation() {}

	public Solicitation(String value, String description, boolean isValid, int solicitationDate) {
		this.value = value;
		this.description = description;
		this.isValid = isValid;
		this.solicitationDate = solicitationDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSolicitationDate() {
		return solicitationDate;
	}

	public void setSolicitationDate(int solicitationDate) {
		this.solicitationDate = solicitationDate;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Solicitation [id=" + id + ", value=" + value + "]";
	}

}
