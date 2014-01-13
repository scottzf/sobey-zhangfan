package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.MapEcsElb;

public interface MapEcsElbDao extends PagingAndSortingRepository<MapEcsElb, Integer>,
		JpaSpecificationExecutor<MapEcsElb> {

}