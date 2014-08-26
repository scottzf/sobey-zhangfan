package com.sobey.cmdbuild.repository.custom;

import java.util.List;

public interface CustomDao {

	public Integer selectMaxAclNumber();

	public Integer selectMaxPolicyId();

	public List<Object[]> getTagRelation(Integer serviceId);

}
