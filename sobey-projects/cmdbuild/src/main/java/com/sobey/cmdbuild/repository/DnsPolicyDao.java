package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.DnsPolicy;

public interface DnsPolicyDao extends PagingAndSortingRepository<DnsPolicy, Integer>,
		JpaSpecificationExecutor<DnsPolicy> {

}