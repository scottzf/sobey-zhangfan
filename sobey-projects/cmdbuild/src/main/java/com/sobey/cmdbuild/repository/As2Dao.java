package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Es3;

public interface As2Dao extends PagingAndSortingRepository<Es3, Integer>, JpaSpecificationExecutor<Es3> {

}