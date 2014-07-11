package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.StoragePort;

public interface StoragePortDao extends PagingAndSortingRepository<StoragePort, Integer>,
		JpaSpecificationExecutor<StoragePort> {

}