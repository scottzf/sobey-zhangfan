package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.MapEipElb;

public interface MapEipElbDao extends PagingAndSortingRepository<MapEipElb, Integer>,
		JpaSpecificationExecutor<MapEipElb> {

}