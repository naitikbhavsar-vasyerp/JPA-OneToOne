package com.springtut.servicecenter.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.springtut.servicecenter.dto.JobCardDto;
import com.springtut.servicecenter.model.JobCard;

public interface JobCardService {
	public List<JobCardDto> getAllJobCards();
	public JobCardDto getJobCard(Long jobCardId);
	public JobCardDto saveJobCard(JobCard jobCard);
	public JobCardDto assignCreated(Long jobCardID, String chasisNumber);
	public boolean deleteJobCard(Long jobCardId);
	public JobCardDto updateJobCard(JobCard jobCard);
	public String uploadFile(MultipartFile file);
	public ResponseEntity<Object> getFile(String fileName);
	public boolean appendData(File file);

}
