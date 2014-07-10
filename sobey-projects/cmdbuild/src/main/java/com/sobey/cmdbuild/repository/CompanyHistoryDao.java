package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyHistoryDao extends PagingAndSortingRepository<CompanyHistory, Integer>,
		JpaSpecificationExecutor<CompanyHistory> {

}