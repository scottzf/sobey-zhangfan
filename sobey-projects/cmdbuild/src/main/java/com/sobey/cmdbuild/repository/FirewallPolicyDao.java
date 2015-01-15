package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FirewallPolicy;

public interface FirewallPolicyDao extends PagingAndSortingRepository<FirewallPolicy, Integer>,
		JpaSpecificationExecutor<FirewallPolicy> {

}