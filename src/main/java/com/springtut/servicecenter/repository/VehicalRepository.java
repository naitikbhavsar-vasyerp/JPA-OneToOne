package com.springtut.servicecenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springtut.servicecenter.model.Vehical;

@Repository
public interface VehicalRepository extends JpaRepository<Vehical, String>{

}
