package com.sobey.cmdbuild.webservice.infrastructure;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.constants.DeviceTypeEnum;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.dto.InfrastructureDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class InfrastructureSoapTest extends BaseFunctionalTestCase {

	private Integer id = 0;

	private String code = "";

	@Test
	public void testFindInfrastructure() {

		InfrastructureDTO infrastructureDTO = new InfrastructureDTO();

		infrastructureDTO.setId(227);

		infrastructureDTO.setDeviceType(DeviceTypeEnum.FIREWALL);

		// infrastructureDTO.setDeviceType(DeviceTypeEnum.LOADBALANCE);
		// infrastructureDTO.setDeviceType(DeviceTypeEnum.SERVER);
		// infrastructureDTO.setDeviceType(DeviceTypeEnum.SWITCH);
		// infrastructureDTO.setDeviceType(DeviceTypeEnum.FIMAS);
		// infrastructureDTO.setDeviceType(DeviceTypeEnum.NETAPPCONTROLLER);

		DTOResult<Object> result = infrastructureService.findInfrastructure(infrastructureDTO);

		assertEquals("0", result.getCode());

	}

	@Test
	public void testFindInfrastructureByParams() {

	}

	@Test
	public void testCreateInfrastructure() {

		InfrastructureDTO infrastructureDTO = TestData.randomInfrastructureDTO();

		infrastructureDTO.setDeviceType(DeviceTypeEnum.FIREWALL);
		// infrastructureDTO.setDeviceType(DeviceTypeEnum.LOADBALANCE);
		// infrastructureDTO.setDeviceType(DeviceTypeEnum.SERVER);
		// infrastructureDTO.setDeviceType(DeviceTypeEnum.SWITCH);

		// infrastructureDTO.setDeviceType(DeviceTypeEnum.FIMAS);
		// infrastructureDTO.setFimasBox(240);

		// infrastructureDTO.setDeviceType(DeviceTypeEnum.NETAPPCONTROLLER);
		// infrastructureDTO.setNetAppBox(289);

		IdResult result = infrastructureService.createInfrastructure(infrastructureDTO);

		assertNotNull(result);
		assertEquals("0", result.getCode());

	}

	@Test
	public void testUpdateInfrastructure() {

	}

	@Test
	public void testDeleteInfrastructure() {

	}

	@Test
	public void testGetInfrastructureList() {

	}

	@Test
	public void testGetInfrastructurePagination() {

	}

	@Test
	public void testReportInfrastructure() {

	}

}
