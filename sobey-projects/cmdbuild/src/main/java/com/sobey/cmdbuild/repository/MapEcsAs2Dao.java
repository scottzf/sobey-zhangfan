package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Log;

public interface MapEcsAs2Dao extends PagingAndSortingRepository<Log, Integer>, JpaSpecificationExecutor<Log> {

}