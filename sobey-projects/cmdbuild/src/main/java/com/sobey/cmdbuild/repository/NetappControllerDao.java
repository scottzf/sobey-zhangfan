package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Storage;

public interface NetappControllerDao extends PagingAndSortingRepository<Storage, Integer>,
		JpaSpecificationExecutor<Storage> {

}