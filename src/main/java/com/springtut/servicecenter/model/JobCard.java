package com.springtut.servicecenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class JobCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "jobcard_id")
	private long jobCardId;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_address")
	private String customerAddress;
	
	@Column(name = "customer_phone_number", unique = true)
	private String phoneNumber;
	
	@OneToOne
	private Vehical vehical;
}
