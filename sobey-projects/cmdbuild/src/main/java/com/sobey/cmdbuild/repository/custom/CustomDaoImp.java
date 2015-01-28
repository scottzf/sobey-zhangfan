package com.sobey.cmdbuild.repository.custom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CustomDaoImp implements CustomDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Integer selectMaxVlanId(Integer nicId) {
		String sqlString = "SELECT MAX(vlan_id) from vlan where \"Status\"= 'A' and nic = " + nicId;
		return (Integer) em.createNativeQuery(sqlString).getSingleResult();
	}

	@Override
	public Integer selectMaxPortIndex(Integer tenantsId) {
		String sqlString = "SELECT MAX(port_index) from Subnet where \"Status\"= 'A' and tenants = " + tenantsId;
		return (Integer) em.createNativeQuery(sqlString).getSingleResult();
	}

}
