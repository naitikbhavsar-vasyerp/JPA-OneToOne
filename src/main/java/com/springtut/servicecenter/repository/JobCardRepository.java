package com.springtut.servicecenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springtut.servicecenter.model.JobCard;

@Repository
public interface JobCardRepository extends JpaRepository<JobCard, Long>{

}
