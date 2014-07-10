package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.MapEcsEs3;

public interface MapEcsCs2Dao extends PagingAndSortingRepository<MapEcsEs3, Integer>,
		JpaSpecificationExecutor<MapEcsEs3> {

}