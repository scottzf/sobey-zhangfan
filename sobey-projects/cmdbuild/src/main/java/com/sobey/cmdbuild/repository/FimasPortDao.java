package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FimasPortDao extends PagingAndSortingRepository<FimasPort, Integer>,
		JpaSpecificationExecutor<FimasPort> {

}