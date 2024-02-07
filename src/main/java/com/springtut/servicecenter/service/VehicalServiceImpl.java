package com.springtut.servicecenter.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springtut.servicecenter.dto.JobCardDto;
import com.springtut.servicecenter.dto.VehicalDto;
import com.springtut.servicecenter.model.JobCard;
import com.springtut.servicecenter.model.Vehical;
import com.springtut.servicecenter.repository.VehicalRepository;

@Service
public class VehicalServiceImpl implements VehicalService{

	@Autowired
	private VehicalRepository vehicalRepository;
	

	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public Vehical getVehical(String chasisNumber) {
		Vehical vehical = vehicalRepository.findById(chasisNumber).orElseThrow(()-> new RuntimeException("Vehical not found"));
		return vehical;
	}

	@Override
	public VehicalDto getVehicalDto(String chasisNumber) {
		Vehical vehical = vehicalRepository.findById(chasisNumber).orElseThrow(()-> new RuntimeException("Vehical not found"));
		VehicalDto vehicalDto = modelMapper.map(vehical, VehicalDto.class);
		return vehicalDto;
	}

	@Override
	public List<VehicalDto> getAllVehicals() {
		List<Vehical> vehicals = new ArrayList<>();
		List<VehicalDto> vehicalDtos = new ArrayList<>();
		
		vehicals = vehicalRepository.findAll();
		
		for(Vehical vehical : vehicals) {
			VehicalDto vehicalDto = modelMapper.map(vehical, VehicalDto.class);
			vehicalDtos.add(vehicalDto);
		}
		
		return vehicalDtos;
	}
 
	@Override
	public VehicalDto addVehical(Vehical vehical) {
		vehicalRepository.save(vehical);
		VehicalDto vehicalDto = modelMapper.map(vehical, VehicalDto.class);
		return vehicalDto;
	}

	@Override
	public boolean deleteVehical(String chasisNumber) {
		try {
			vehicalRepository.deleteById(chasisNumber);
			return true;
			}
			catch(Exception e) {
				System.out.println(e);
			}
			return false;
	}

}
