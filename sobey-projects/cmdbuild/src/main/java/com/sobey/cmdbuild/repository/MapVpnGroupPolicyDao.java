package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.MapVpnGroupPolicy;

public interface MapVpnGroupPolicyDao extends PagingAndSortingRepository<MapVpnGroupPolicy, Integer>,
		JpaSpecificationExecutor<MapVpnGroupPolicy> {

}