package com.sobey.api.data;

import com.sobey.generate.cmdbuild.CompanyDTO;
import com.sobey.test.data.RandomData;

public class CMDBuildTestData {

	public static CompanyDTO randomCompany() {
		CompanyDTO dto = new CompanyDTO();

		dto.setAddress(RandomData.randomName("address"));
		dto.setCode(RandomData.randomName("code"));
		dto.setDescription("测试");
		dto.setZip(RandomData.randomName("zip"));
		dto.setPhone(RandomData.randomName("phone"));
		dto.setRemark(RandomData.randomName("remark"));

		return dto;
	}

}
