package com.sobey.api.data;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.service.data.ConstansData;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.SubnetDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

public class TestData {

	private static final Integer tenantsId = 174;

	public static TenantsDTO randomTenantsDTO() {
		TenantsDTO dto = new TenantsDTO();
		dto.setCompany("sobey");
		dto.setDescription("liukai");
		dto.setEmail("liukai@sobey.com");
		dto.setPhone("130000000");
		return dto;
	}

	public static SubnetDTO randomSubnetDTO() {

		String gateway = "192.168.220.1";
		String netmask = "255.255.255.0";
		String segment = "192.168.220.0";

		SubnetDTO dto = new SubnetDTO();

		dto.setIdc(ConstansData.idcId);
		dto.setGateway(gateway);
		dto.setNetMask(netmask);
		dto.setTenants(tenantsId);
		dto.setSegment(segment);
		dto.setDescription("220子网");
		return dto;
	}

	public static EcsDTO randomEcsDTO() {
		EcsDTO dto = new EcsDTO();
		dto.setDescription("测试专用主机A");
		dto.setServer(151);
		dto.setSubnet(1447);// 175:默认子网 1447 : 200子网
		dto.setEcsType(109); // 109 instance 110 firewall
		dto.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		dto.setIdc(ConstansData.idcId);
		dto.setTenants(tenantsId);
		dto.setEcsSpec(118);// centos
		return dto;
	}

	public static EcsDTO randomRouterDTO() {
		EcsDTO dto = new EcsDTO();
		dto.setDescription("刘凯的vRouter2");
		dto.setServer(151);
		dto.setSubnet(175);
		dto.setEcsType(110); // 109 instance 110 firewall
		dto.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		dto.setIdc(ConstansData.idcId);
		dto.setTenants(tenantsId);
		dto.setEcsSpec(120);
		return dto;
	}

}
