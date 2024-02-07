package com.springtut.servicecenter.service;

import java.util.List;

import com.springtut.servicecenter.dto.VehicalDto;
import com.springtut.servicecenter.model.Vehical;

public interface VehicalService {
	public List<VehicalDto> getAllVehicals();
	public Vehical getVehical(String chasisNumber);
	public VehicalDto getVehicalDto(String chasisNumber);
	public VehicalDto addVehical(Vehical vehical);
	public boolean deleteVehical(String chasisNumber);
}
