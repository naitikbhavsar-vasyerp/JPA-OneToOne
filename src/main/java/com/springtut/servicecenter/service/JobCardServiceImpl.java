package com.springtut.servicecenter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springtut.servicecenter.dto.JobCardDto;
import com.springtut.servicecenter.model.JobCard;
import com.springtut.servicecenter.model.Vehical;
import com.springtut.servicecenter.repository.JobCardRepository;
import com.springtut.servicecenter.repository.VehicalRepository;

@Service
public class JobCardServiceImpl implements JobCardService{

	@Autowired
	JobCardRepository jobCardRepository;
	JobCardDto jobCardDto;
	@Autowired
	VehicalService vehicalService;
	
	
	ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<JobCardDto> getAllJobCards() {
		List<JobCard> jobCards = new ArrayList<>();
		List<JobCardDto> jobCardDtos = new ArrayList<>();
		
		jobCards=jobCardRepository.findAll();
		
		for(JobCard jobCard : jobCards) {
			JobCardDto jobCardDto = modelMapper.map(jobCard, JobCardDto.class);
			jobCardDtos.add(jobCardDto);
		}
		
		return jobCardDtos;
	}


	@Override
	public JobCardDto saveJobCard(JobCard jobCard) {
		jobCardRepository.save(jobCard);
		JobCardDto jobCardDto = modelMapper.map(jobCard, JobCardDto.class);
		return jobCardDto;
	}

	@Override
	public JobCardDto assignCreated(Long jobCardID, String chasisNumber) {
		Vehical vehical = vehicalService.getVehical(chasisNumber);
		
		JobCard jobCard = jobCardRepository.findById(jobCardID).orElseThrow(()-> new RuntimeException("Jobcard not found"));
		jobCard.setVehical(vehical);
		jobCardRepository.save(jobCard);
		JobCardDto jobCardDto = modelMapper.map(jobCard, JobCardDto.class);
		return jobCardDto;
	}


	@Override
	public boolean deleteJobCard(Long jobCardId) {
		try {
		jobCardRepository.deleteById(jobCardId);
		return true;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return false;
	}


	@Override
	public JobCardDto updateJobCard(JobCard jobCard) {
		//System.out.println(jobCard);
		JobCardDto jobCardDto;
		if(jobCard.getVehical()==null) {
		  	JobCard card = jobCardRepository.findById(jobCard.getJobCardId()).orElseThrow(()-> new RuntimeException("Jobcard not found"));
			card.setJobCardId(jobCard.getJobCardId());
			card.setCustomerName(jobCard.getCustomerName());
			card.setCustomerAddress(jobCard.getCustomerAddress());
			card.setPhoneNumber(jobCard.getPhoneNumber());
			jobCardRepository.save(card);
			jobCardDto = modelMapper.map(card, JobCardDto.class);
		}
		else {
			jobCardRepository.save(jobCard);
			vehicalService.addVehical(jobCard.getVehical());
			jobCardDto = modelMapper.map(jobCard, JobCardDto.class);
		}
		return jobCardDto;
	}


	@Override
	public JobCardDto getJobCard(Long jobCardId) {
		JobCard jobCard = jobCardRepository.findById(jobCardId).orElseThrow(()-> new RuntimeException("JObcard not found"));
		JobCardDto jobCardDto = modelMapper.map(jobCard, JobCardDto.class);
		return jobCardDto;
	}
	
	
	
}
