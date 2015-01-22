package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sobey.cmdbuild.entity.Produced;

public interface ProducedDao extends PagingAndSortingRepository<Produced, Integer>, JpaSpecificationExecutor<Produced> {

}