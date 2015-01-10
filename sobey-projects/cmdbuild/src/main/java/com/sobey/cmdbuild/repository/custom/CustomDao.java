package com.sobey.cmdbuild.repository.custom;

import java.util.List;

public interface CustomDao {

	public Integer selectMaxAclNumber();

	public Integer selectMaxPolicyId(Integer tenantsId);

	public Integer selectMaxRouterId(Integer tenantsId);

	public List<Object[]> getTagRelation(Integer serviceId);

	public Integer selectMaxVlanId(Integer nicId, Integer subnetId);

}
