package com.springtut.servicecenter.dto;

import com.springtut.servicecenter.model.Vehical;

import lombok.Data;

@Data
public class JobCardDto {
	private String customerName;

	private String customerAddress;
	
	private String phoneNumber;
	
	private Vehical vehical;
}
