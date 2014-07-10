package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.StoragePort;

public interface NetappPortDao extends PagingAndSortingRepository<StoragePort, Integer>,
		JpaSpecificationExecutor<StoragePort> {

}