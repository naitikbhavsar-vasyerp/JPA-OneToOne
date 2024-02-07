package com.springtut.servicecenter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springtut.servicecenter.dto.JobCardDto;
import com.springtut.servicecenter.dto.VehicalDto;
import com.springtut.servicecenter.model.JobCard;
import com.springtut.servicecenter.model.Vehical;
import com.springtut.servicecenter.service.VehicalService;

@RestController
@RequestMapping("/vehical")
public class VehicalController {

	@Autowired
	VehicalService service;
	
	@GetMapping("/get")
	public List<VehicalDto> getVehicals(){
		List<VehicalDto> vehicalDtos =  service.getAllVehicals();
		return vehicalDtos;
	}
	
	@GetMapping("/get/{chasisnumber}")
	public VehicalDto getVehical(@PathVariable("chasisnumber") String chasisNumber){
		VehicalDto vehicalDto =  service.getVehicalDto(chasisNumber);
		return vehicalDto;
	}
	
	@PostMapping("/create")
	public VehicalDto saveVehical(@RequestBody Vehical vehical) {
		VehicalDto vehicalDto = service.addVehical(vehical);
		return vehicalDto;
	}
	
	@PutMapping("/update")
	public VehicalDto updateVehical(@RequestBody Vehical vehical) {	
		VehicalDto vehicalDto = service.addVehical(vehical);
		System.out.println(vehicalDto);
		return vehicalDto;
	}
	
	@DeleteMapping("/delete/{chasisNumber}")
	public String deleteVehical(@PathVariable("chasisNumber") String chasisNumber) {
		if(service.deleteVehical(chasisNumber)) {
			return "Vehical deleted successfully";
		}
		else {
			return "Vehical deletion unsuccessfull";
		}
	}
	
}
