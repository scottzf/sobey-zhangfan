package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface Cs2HistoryDao extends PagingAndSortingRepository<Cs2History, Integer>,
		JpaSpecificationExecutor<Cs2History> {

}