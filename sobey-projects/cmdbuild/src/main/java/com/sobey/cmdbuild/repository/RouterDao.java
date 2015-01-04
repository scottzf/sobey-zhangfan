package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Router;

public interface RouterDao extends PagingAndSortingRepository<Router, Integer>, JpaSpecificationExecutor<Router> {

}