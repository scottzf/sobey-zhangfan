package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.ConfigRouterStatic;

public interface ConfigRouterStaticDao extends PagingAndSortingRepository<ConfigRouterStatic, Integer>,
		JpaSpecificationExecutor<ConfigRouterStatic> {

}