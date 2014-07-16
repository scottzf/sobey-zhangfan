package com.sobey.cmdbuild.service.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sobey.cmdbuild.repository.custom.CustomDao;
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
	public int selectMaxAclNumber() {
		return (int) MathsUtil.add(customDao.selectMaxAclNumber(), 1);
	}

	/**
	 * 获得数据库中PolicyId的最大值,并加上1.
	 * 
	 * @return
	 */
	public int selectMaxPolicyId() {
		return (int) MathsUtil.add(customDao.selectMaxPolicyId(), 1);
	}

}