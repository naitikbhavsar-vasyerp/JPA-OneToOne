package com.springtut.servicecenter.service;

import java.util.List;
import java.util.Map;

import com.springtut.servicecenter.dto.JobCardDto;
import com.springtut.servicecenter.model.JobCard;

public interface JobCardService {
	public List<JobCardDto> getAllJobCards();
	public JobCardDto getJobCard(Long jobCardId);
	public JobCardDto saveJobCard(JobCard jobCard);
	public JobCardDto assignCreated(Long jobCardID, String chasisNumber);
	public boolean deleteJobCard(Long jobCardId);
	public JobCardDto updateJobCard(JobCard jobCard);

}
