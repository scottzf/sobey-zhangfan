package com.sobey.cmdbuild.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.service.infrastructure.IpaddressService;
import com.sobey.cmdbuild.service.organisation.CompanyService;
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


		List<Ipaddress> list =new ArrayList<Ipaddress>();
		
		for (int i = 0; i < 3; i++) {
			Ipaddress ipaddress = TestData.randomIpaddress();
			//ipaddress.setId(null);
			//list.add(ipaddress);
			System.out.println(ipaddress);
			service.saveOrUpdate(ipaddress);
		}
//		Iterable<Ipaddress> iterable = service.saveAll(list);
//		
//		for (Ipaddress ip : iterable) {
//			assertNotNull(ip.getCode());
//			
		//}
		

	}

}
