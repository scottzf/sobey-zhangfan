package com.sobey.api.data;

import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.test.data.RandomData;

public class CMDBuildTestData {

	public static EcsDTO randomEcsDTO() {

		EcsDTO dto = new EcsDTO();

		dto.setCode(RandomData.randomName("code"));
		dto.setDescription("Description");
		dto.setRemark(RandomData.randomName("remark"));

		dto.setIdc(99);
		dto.setIpaddress(143);
		dto.setServer(344);
		dto.setTenants(105);
		dto.setAgentType(80);
		dto.setEcsSpec(123);
		dto.setEcsStatus(34);

		return dto;
	}
}
