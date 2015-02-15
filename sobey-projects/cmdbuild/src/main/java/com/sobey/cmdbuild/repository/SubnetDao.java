package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Subnet;

public interface SubnetDao extends PagingAndSortingRepository<Subnet, Integer>, JpaSpecificationExecutor<Subnet> {

}