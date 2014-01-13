package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.MapEcsCs2;

public interface MapEcsCs2Dao extends PagingAndSortingRepository<MapEcsCs2, Integer>,
		JpaSpecificationExecutor<MapEcsCs2> {

}