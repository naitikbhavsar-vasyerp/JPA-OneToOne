package com.springtut.servicecenter.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springtut.servicecenter.dto.JobCardDto;
import com.springtut.servicecenter.dto.RequestPayloadDto;
import com.springtut.servicecenter.model.JobCard;
import com.springtut.servicecenter.service.JobCardService;


@RestController
@RequestMapping("/jobcard")
public class JobCardController {

	
	@Autowired
	JobCardService jobCardService;
	
	@GetMapping("/get")
	public List<JobCardDto> getJobCards(){
		List<JobCardDto> jobCardDtos =  jobCardService.getAllJobCards();
		return jobCardDtos;
	}
	
	@GetMapping("/get/{jobCardId}")
	public JobCardDto getJobCard(@PathVariable("jobCardId") Long jobCardId){
		JobCardDto jobCardDto =  jobCardService.getJobCard(jobCardId);
		return jobCardDto;
	}
	
	@PostMapping("/create")
	public JobCardDto saveJobCard(@RequestBody JobCard jobCard) {
		JobCardDto jobCardDto = jobCardService.saveJobCard(jobCard);
		return jobCardDto;
	}
	
	@PostMapping("/assign-created")
	public JobCardDto assignvehical(@RequestBody RequestPayloadDto requestPayload) {
		JobCardDto jobCardDto = jobCardService.assignCreated(requestPayload.jobCardId, requestPayload.chasisNumber);
		return jobCardDto;
	}
	
	@PutMapping("/update")
	public JobCardDto updateJobCard(@RequestBody JobCard jobCard) {	
		JobCardDto jobCardDto = jobCardService.updateJobCard(jobCard);
		System.out.println(jobCardDto);
		return jobCardDto;
	}
	
	@DeleteMapping("/delete/{jobCardId}")
	public String deleteJobCard(@PathVariable("jobCardId") Long jobCardId) {
		if(jobCardService.deleteJobCard(jobCardId)) {
			return "JobCard deleted successfully";
		}
		else {
			return "JobCard not deleted";
		}
	}
	                  
	@PostMapping("/upload")
	public String getFile(@RequestParam("file") MultipartFile file) {
		String downloadUri = jobCardService.uploadFile(file);
		return "File uploaded. Download by clicking "+downloadUri;
	}
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Object> getFile(@PathVariable String name){
		ResponseEntity<Object> entity = jobCardService.getFile(name);
		return entity;
	}
	
}
