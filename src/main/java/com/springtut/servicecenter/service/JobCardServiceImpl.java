package com.springtut.servicecenter.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springtut.servicecenter.dto.JobCardDto;
import com.springtut.servicecenter.model.JobCard;
import com.springtut.servicecenter.model.Vehical;
import com.springtut.servicecenter.repository.JobCardRepository;

@Service
public class JobCardServiceImpl implements JobCardService{

	@Autowired
	JobCardRepository jobCardRepository;
	JobCardDto jobCardDto;
	@Autowired
	VehicalService vehicalService;
	final String UPLOAD_DIR = "D:\\Java\\files";
	
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


	@Override
	public String uploadFile(MultipartFile file) {
		
		File file2 = new File(UPLOAD_DIR);
		
		if(!file2.exists())
			file2.mkdirs();
			
		String fileName=file.getOriginalFilename();
		Path path = Paths.get(UPLOAD_DIR).resolve(fileName);
		
		try {
			Files.write(path, file.getBytes(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		file2 = path.toFile();
		String downloadUri="";
		if(appendData(file2))
			downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/jobcard/download/").path(fileName).toUriString();
		
		return downloadUri;
	}


	@Override
	public ResponseEntity<Object> getFile(String fileName) {
		ResponseEntity<Object> entity =null;
	    try {
	        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
	        File file = filePath.toFile();

	        if (file.exists()){

	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	            headers.setContentDispositionFormData("attachment",fileName);

	            entity = ResponseEntity.ok().headers(headers).contentLength(file.length()).body(new FileSystemResource(file));
	        } else {
	            entity = ResponseEntity.ok("File not found");
	        }

	    } catch (Exception e) {
	        System.out.println(e);
	    }
	    return entity;
	 }


	@Override
	public boolean appendData(File file) {
		
		List<JobCardDto> jobCardDtos = getAllJobCards();

        try {
        	FileWriter writer = new FileWriter(file, true); // true parameter for append mode

        	for(JobCardDto data: jobCardDtos) {
        		Vehical vehical = data.getVehical();
        		String info = "Name: "+data.getCustomerName()+" Address: "+data.getCustomerAddress()+" Phone number: "+data.getPhoneNumber()+" Vehical Name: "+vehical.getVehicalName()+" Vehical color: "+vehical.getVehicalColor()+" Registration number: "+vehical.getRegistrationNumber();
        		writer.write(info);
        	}
            writer.close();
            System.out.println("Data appended to the file successfully.");
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
		
        return true;
	}
}
