package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.StorageBoxHistory;

public interface StorageBoxHistoryDao extends PagingAndSortingRepository<StorageBoxHistory, Integer>,
		JpaSpecificationExecutor<StorageBoxHistory> {

}