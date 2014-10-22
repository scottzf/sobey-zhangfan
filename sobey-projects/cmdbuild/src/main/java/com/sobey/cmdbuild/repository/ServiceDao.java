package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Service;

public interface ServiceDao extends PagingAndSortingRepository<Service, Integer>, JpaSpecificationExecutor<Service> {

}