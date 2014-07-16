package com.sobey.cmdbuild.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CustomDaoImp implements CustomDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Integer selectMaxAclNumber() {
		String sqlString = "SELECT MAX(acl_number) from esg where \"Status\"= 'A' ";
		return (Integer) em.createNativeQuery(sqlString).getSingleResult();
	}

	@Override
	public Integer selectMaxPolicyId() {
		String sqlString = "SELECT MAX(policy_id) from vpn where \"Status\"= 'A' ";
		return (Integer) em.createNativeQuery(sqlString).getSingleResult();
	}

}
