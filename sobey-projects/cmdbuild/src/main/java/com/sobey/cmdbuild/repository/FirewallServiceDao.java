package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.FirewallService;

public interface FirewallServiceDao extends PagingAndSortingRepository<FirewallService, Integer>,
		JpaSpecificationExecutor<FirewallService> {

}