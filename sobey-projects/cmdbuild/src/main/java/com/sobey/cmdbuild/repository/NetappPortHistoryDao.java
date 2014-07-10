package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.StoragePortHistory;

public interface NetappPortHistoryDao extends PagingAndSortingRepository<StoragePortHistory, Integer>,
		JpaSpecificationExecutor<StoragePortHistory> {

}