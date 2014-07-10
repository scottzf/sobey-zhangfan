package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FimasBoxHistoryDao extends PagingAndSortingRepository<FimasBoxHistory, Integer>,
		JpaSpecificationExecutor<FimasBoxHistory> {

}