package com.sobey.cmdbuild.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.LookUpTypeEnum;
import com.sobey.cmdbuild.entity.LookUp;
import com.sobey.cmdbuild.service.organisation.LookUpService;
import com.sobey.test.spring.SpringTransactionalTestCase;

/**
 * LookUpService 的测试用例,测试sevice层的业务逻辑
 * 
 * @author Administrator
 * 
 */
@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public class LookUpServiceTest extends SpringTransactionalTestCase {

	@Autowired
	private LookUpService service;

	@Test
	public void getLookUp() {

		Map<String, Object> searchParamsMap = Maps.newHashMap();

		searchParamsMap.put("EQ_type", LookUpTypeEnum.ISP.name());
		searchParamsMap.put("EQ_description", "中国联通");

		List<LookUp> list = service.getLookUpList(searchParamsMap);

		System.out.println("列表数据数量:" + list.size());

		for (LookUp lookUp : list) {
			System.out.println(lookUp.getDescription());
			System.out.println(lookUp.getNotes());
		}

		assertNotNull(list);
	}

}
