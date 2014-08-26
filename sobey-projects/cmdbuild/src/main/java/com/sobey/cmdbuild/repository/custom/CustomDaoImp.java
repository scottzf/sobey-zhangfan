package com.sobey.cmdbuild.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CustomDaoImp implements CustomDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Integer selectMaxAclNumber() {
		String sqlString = "SELECT MAX(acl_number) from tenants where \"Status\"= 'A' ";
		return (Integer) em.createNativeQuery(sqlString).getSingleResult();
	}

	@Override
	public Integer selectMaxPolicyId() {
		String sqlString = "SELECT MAX(policy_id) from vpn where \"Status\"= 'A' ";
		return (Integer) em.createNativeQuery(sqlString).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getTagRelation(Integer serviceId) {
		String sqlString = "SELECT t1.\"Code\" as serviceCode ,t1.\"Description\" as serviceName ,t3.\"Description\" as tagName,t5.\"Description\" as tagTypeName,t4.\"Description\"as parentTagName "
				+ " FROM iaas AS t1 LEFT JOIN \"Map_tag_service\" AS t2 ON t1.\"Id\" = t2.\"IdObj2\" LEFT JOIN tag AS t3 ON t2.\"IdObj1\" = t3.\"Id\" LEFT JOIN tag AS t4 ON t3.parent_tag = t4.\"Id\" LEFT JOIN \"LookUp\" AS t5 ON t5.\"Id\" = t3.tag_type"
				+ " WHERE	t1.\"Status\" = 'A' AND t2.\"Status\" = 'A' AND t3.\"Status\" = 'A' AND t1.\"Id\" = "
				+ serviceId;
		System.out.println(sqlString);
		return em.createNativeQuery(sqlString).getResultList();
	}
}
