package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.MapTagService;

public interface MapTagServiceDao extends PagingAndSortingRepository<MapTagService, Integer>,
		JpaSpecificationExecutor<MapTagService> {

}