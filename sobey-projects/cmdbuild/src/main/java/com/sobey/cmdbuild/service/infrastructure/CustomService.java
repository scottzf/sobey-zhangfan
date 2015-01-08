package com.sobey.cmdbuild.service.infrastructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sobey.cmdbuild.repository.custom.CustomDao;
import com.sobey.cmdbuild.webservice.response.dto.TagRelation;
import com.sobey.core.utils.MathsUtil;

/**
 * 自定义查询的service类.
 */
@Service
@Transactional
public class CustomService {

	@Autowired
	private CustomDao customDao;

	/**
	 * 获得数据库中AclNumber的最大值,并加上1.
	 * 
	 * @return
	 */
	public Integer selectMaxAclNumber() {

		Integer aclNumber = customDao.selectMaxAclNumber();
		return (int) MathsUtil.add(aclNumber == null ? 3000 : aclNumber, 1);
	}

	/**
	 * 获得tenants中PolicyId的最大值,并加上1.
	 * 
	 * @return
	 */
	public Integer selectMaxPolicyId(Integer tenantsId) {
		Integer policyId = customDao.selectMaxPolicyId(tenantsId);
		return (int) MathsUtil.add(policyId == null ? 1 : policyId, 1);
	}

	/**
	 * 获得Tenants所有Subnet中PolicyId的最大值,并加上1.
	 * 
	 * @return
	 */
	public Integer selectMaxPolicyIdBySubnet(Integer tenantsId) {
		Integer policyId = customDao.selectMaxPolicyIdBySubnet(tenantsId);
		return (int) MathsUtil.add(policyId == null ? 3 : policyId, 1);
	}

	/**
	 * 获得tenants中RouterId的最大值,并加上1.
	 * 
	 * @return
	 */
	public Integer selectMaxRouterId(Integer tenantsId) {
		Integer routerId = customDao.selectMaxRouterId(tenantsId);
		return (int) MathsUtil.add(routerId == null ? 1 : routerId, 1);
	}

	/**
	 * 根据nicId和subnet获得数据库中Vlan的最大值,并加上1.
	 * 
	 * @param nicId
	 * @param subnetId
	 * @return
	 */
	public Integer selectMaxVlanId(Integer nicId, Integer subnetId) {
		Integer vlanId = customDao.selectMaxVlanId(nicId, subnetId);
		return (int) MathsUtil.add(vlanId == null ? 4 : vlanId, 1);
	}

	public List<TagRelation> getTagRelation(Integer serviceId) {

		List<TagRelation> relations = Lists.newArrayList();

		List<Object[]> list = customDao.getTagRelation(serviceId);

		for (Object[] obj : list) {

			TagRelation tagRelation = new TagRelation();

			tagRelation.setServiceCode(obj[0].toString());
			tagRelation.setServiceName(obj[1].toString());
			tagRelation.setTagName(obj[2].toString());
			tagRelation.setTagTypeName(obj[3].toString());
			if (obj[4] != null) {
				tagRelation.setParentTagName(obj[4].toString());
			}
			relations.add(tagRelation);

		}

		return relations;
	}

}