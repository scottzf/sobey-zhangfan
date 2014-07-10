package com.sobey.cmdbuild.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface Cs2Dao extends PagingAndSortingRepository<Cs2, Integer>, JpaSpecificationExecutor<Cs2> {

}