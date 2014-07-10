package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MapGroupPolicyIpaddressDao extends PagingAndSortingRepository<MapGroupPolicyIpaddress, Integer>,
		JpaSpecificationExecutor<MapGroupPolicyIpaddress> {

}