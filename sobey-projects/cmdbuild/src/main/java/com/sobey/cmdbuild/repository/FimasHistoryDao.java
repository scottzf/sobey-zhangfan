package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FimasHistoryDao extends PagingAndSortingRepository<FimasHistory, Integer>,
		JpaSpecificationExecutor<FimasHistory> {

}