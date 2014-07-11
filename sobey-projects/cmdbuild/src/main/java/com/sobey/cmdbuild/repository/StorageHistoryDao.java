package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.StorageHistory;

public interface StorageHistoryDao extends PagingAndSortingRepository<StorageHistory, Integer>,
		JpaSpecificationExecutor<StorageHistory> {

}