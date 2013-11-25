package com.sobey.cmdbuild.webservice.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.BaseFunctionalTestCase;
import com.sobey.cmdbuild.constants.DevicePortTypeEnum;
import com.sobey.cmdbuild.data.TestData;
import com.sobey.cmdbuild.webservice.response.dto.InfrastructurePortDTO;
import com.sobey.cmdbuild.webservice.response.result.IdResult;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-soap-client.xml" })
public class InfrastructurePortSoapTest extends BaseFunctionalTestCase {

	private Integer id = 0;

	private String code = "";

	@Test
	public void testFindInfrastructurePort() {

		Map<String, Object> searchParams = Maps.newHashMap();

		// InfrastructurePortService.findInfrastructurePort(InfrastructurePortDTO);

	}

	@Test
	public void testFindInfrastructurePortByParams() {

	}

	@Test
	public void testCreateInfrastructurePort() {

		InfrastructurePortDTO infrastructurePortDTO = TestData.randomInfrastructurePortDTO();

		infrastructurePortDTO.setDevicePortType(DevicePortTypeEnum.FIREWALLPORT);
		infrastructurePortDTO.setInfrastructure(227);// 端口所属基础设施 Id

		// infrastructurePortDTO.setDevicePortType(DevicePortTypeEnum.LOADBALANCEPORT);
		// infrastructurePortDTO.setInfrastructure(260);

		// infrastructurePortDTO.setDevicePortType(DevicePortTypeEnum.SERVERPORT);
		// infrastructurePortDTO.setInfrastructure(249);

		// infrastructurePortDTO.setDevicePortType(DevicePortTypeEnum.SWITCHPORT);
		// infrastructurePortDTO.setInfrastructure(278);

		// infrastructurePortDTO.setDevicePortType(DevicePortTypeEnum.FIMASPORT);
		// infrastructurePortDTO.setInfrastructure(249);

		// infrastructurePortDTO.setDevicePortType(DevicePortTypeEnum.NETAPPCONTROLLERPORT);
		// infrastructurePortDTO.setInfrastructure(298);

		IdResult result = infrastructureService.createInfrastructurePort(infrastructurePortDTO);

		assertNotNull(result);
		assertEquals("0", result.getCode());
	}

	@Test
	public void testUpdateInfrastructurePort() {

	}

	@Test
	public void testDeleteInfrastructurePort() {

	}

	@Test
	public void testGetInfrastructurePortList() {

	}

	@Test
	public void testGetInfrastructurePortPagination() {

	}

	@Test
	public void testReportInfrastructurePort() {

	}

}
