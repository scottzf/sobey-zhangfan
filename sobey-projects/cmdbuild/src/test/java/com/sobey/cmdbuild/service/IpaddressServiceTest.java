package com.sobey.cmdbuild.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sobey.cmdbuild.service.infrastructure.IpaddressService;
import com.sobey.test.spring.SpringTransactionalTestCase;

/**
 * CompanyService 的测试用例,测试sevice层的业务逻辑
 * 
 * @author Administrator
 * 
 */
@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public class IpaddressServiceTest extends SpringTransactionalTestCase {

	@Autowired
	private IpaddressService service;

	@Test
	@Rollback(false)
	public void saveCompany() {

	}

}
