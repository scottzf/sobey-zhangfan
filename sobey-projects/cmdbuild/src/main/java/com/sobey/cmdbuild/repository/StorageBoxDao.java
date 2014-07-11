package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.StorageBox;

public interface StorageBoxDao extends PagingAndSortingRepository<StorageBox, Integer>,
		JpaSpecificationExecutor<StorageBox> {

}