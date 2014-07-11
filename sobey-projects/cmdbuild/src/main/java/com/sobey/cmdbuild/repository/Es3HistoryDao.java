package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Es3History;

public interface Es3HistoryDao extends PagingAndSortingRepository<Es3History, Integer>,
		JpaSpecificationExecutor<Es3History> {

}