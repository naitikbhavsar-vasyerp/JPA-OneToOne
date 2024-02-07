package com.springtut.servicecenter.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Vehical {
	
	@Id
	private String chasisNumber;
	
	@Column(name = "vehical_name")
	private String vehicalName;
	
	@Column(name = "vehical_color")
	private String vehicalColor;
	
	@Column(name = "vehical_registration_number", unique = true)
	private String registrationNumber;
	
}
