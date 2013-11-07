package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.LoadBalancerHistory;

public interface LoadBalancerHistoryDao extends PagingAndSortingRepository<LoadBalancerHistory, Integer>,
		JpaSpecificationExecutor<LoadBalancerHistory> {

}