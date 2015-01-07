package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.ConfigFirewallAddress;

public interface ConfigFirewallAddressDao extends PagingAndSortingRepository<ConfigFirewallAddress, Integer>,
		JpaSpecificationExecutor<ConfigFirewallAddress> {

}