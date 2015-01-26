package com.sobey.api.data;

import java.util.ArrayList;
import java.util.List;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.service.data.ConstansData;
import com.sobey.core.utils.Identities;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.FirewallPolicyDTO;
import com.sobey.generate.cmdbuild.FirewallServiceDTO;
import com.sobey.generate.cmdbuild.ProducedDTO;
import com.sobey.generate.cmdbuild.RouterDTO;
import com.sobey.generate.cmdbuild.SubnetDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

public class TestData {

	private static final Integer tenantsId = 287;

	public static TenantsDTO randomTenantsDTO() {
		TenantsDTO dto = new TenantsDTO();
		dto.setCompany("sobey");
		dto.setDescription("liukai");
		dto.setEmail("liukai@sobey.com");
		dto.setPhone("130000000");
		return dto;
	}

	public static SubnetDTO randomSubnetDTO() {

		String gateway = "192.168.200.1";
		String netmask = "255.255.255.0";
		String segment = "192.168.200.0";

		SubnetDTO dto = new SubnetDTO();

		dto.setIdc(ConstansData.idcId);
		dto.setGateway(gateway);
		dto.setNetMask(netmask);
		dto.setDefaultSubnet(LookUpConstants.DefaultSubnet.No.getValue());
		dto.setTenants(tenantsId);
		dto.setSegment(segment);
		dto.setDescription("200子网");
		return dto;
	}

	public static EcsDTO randomEcsDTO() {
		EcsDTO dto = new EcsDTO();
		dto.setDescription("测试专用主机A");
		dto.setSubnet(288);// 165:默认子网 1466 : 200子网
		dto.setEcsType(110); // 109 instance 110 firewall
		dto.setEcsStatus(LookUpConstants.ECSStatus.运行.getValue());
		dto.setIdc(ConstansData.idcId);
		dto.setTenants(tenantsId);
		dto.setEcsSpec(120);// centos
		dto.setCpuNumber("2");
		dto.setMemorySize("2048");
		return dto;
	}

	public static RouterDTO randomRouterDTO() {
		RouterDTO dto = new RouterDTO();
		dto.setDescription("路由2");
		dto.setIdc(ConstansData.idcId);
		dto.setTenants(tenantsId);
		dto.setEcsSpec(122);
		dto.setCpuNumber("1");
		dto.setMemorySize("1024");
		return dto;
	}

	public static FirewallServiceDTO randomFirewallServiceDTO() {

		FirewallServiceDTO firewallServiceDTO = new FirewallServiceDTO();
		firewallServiceDTO.setTenants(tenantsId);
		firewallServiceDTO.setIdc(ConstansData.idcId);
		firewallServiceDTO.setDescription("自定义的防火墙");
		return firewallServiceDTO;
	}

	public static List<FirewallPolicyDTO> randomFirewallPolicyDTOs() {

		List<FirewallPolicyDTO> firewallPolicyDTOs = new ArrayList<FirewallPolicyDTO>();

		FirewallPolicyDTO firewallPolicyDTO = new FirewallPolicyDTO();
		firewallPolicyDTO.setAction(99); // 99 Allow ; 100 Deny
		firewallPolicyDTO.setAddress("192.168.2.1");
		firewallPolicyDTO.setDescription("TCP");
		firewallPolicyDTO.setDirection(113); // 113 下行 ; 112 上行
		firewallPolicyDTO.setStartPort(80);
		firewallPolicyDTO.setEndPort(80);
		firewallPolicyDTO.setProtocol(115); // 115 TCP ; 116 UDP

		FirewallPolicyDTO firewallPolicyDTO2 = new FirewallPolicyDTO();

		firewallPolicyDTO2.setAction(100); // 99 Allow ; 100 Deny
		firewallPolicyDTO2.setAddress("192.168.2.3");
		firewallPolicyDTO2.setDescription("TCP");
		firewallPolicyDTO2.setDirection(112); // 113 下行 ; 112 上行
		firewallPolicyDTO2.setStartPort(80);
		firewallPolicyDTO2.setEndPort(100);
		firewallPolicyDTO2.setProtocol(116); // 115 TCP ; 116 UDP

		firewallPolicyDTOs.add(firewallPolicyDTO);
		firewallPolicyDTOs.add(firewallPolicyDTO2);
		return firewallPolicyDTOs;
	}

	public static DnsDTO randomDnsDTO() {
		DnsDTO dnsDTO = new DnsDTO();
		dnsDTO.setDomainName("www.sobey.com");
		dnsDTO.setIdc(ConstansData.idcId);
		dnsDTO.setTenants(tenantsId);
		dnsDTO.setDescription("www.sobey.com");
		return dnsDTO;
	}

	public static List<DnsPolicyDTO> randomDnsPolicyDTOs() {
		List<DnsPolicyDTO> dnsPolicyDTOs = new ArrayList<DnsPolicyDTO>();
		DnsPolicyDTO dnsPolicyDTO = new DnsPolicyDTO();
		dnsPolicyDTO.setDnsProtocol(39); // 39 HTTP ; 59 HTTPS
		dnsPolicyDTO.setPort("80");
		dnsPolicyDTO.setIpaddress("125.71.203.22");// EIP的IP
		dnsPolicyDTOs.add(dnsPolicyDTO);
		return dnsPolicyDTOs;
	}

	public static Es3DTO randomEs3DTO() {

		Es3DTO es3DTO = new Es3DTO();
		es3DTO.setAgentType(LookUpConstants.AgentType.VMware.getValue());
		es3DTO.setDescription("测试的卷2");
		es3DTO.setTotalSize("100");
		es3DTO.setEs3Type(44);
		es3DTO.setIdc(ConstansData.idcId);
		es3DTO.setVolumeName(Identities.randomBase62(8));
		es3DTO.setTenants(tenantsId);
		es3DTO.setRemark("无所谓");

		return es3DTO;
	}

	public static EipDTO randomEipDTO() {

		EipDTO eipDTO = new EipDTO();
		eipDTO.setEipStatus(LookUpConstants.EIPStatus.未使用.getValue());
		eipDTO.setTenants(tenantsId);
		eipDTO.setIsp(29);
		eipDTO.setIpaddress(149);
		eipDTO.setIdc(ConstansData.idcId);
		eipDTO.setDescription("125.71.203.22");
		eipDTO.setAgentType(LookUpConstants.AgentType.Fortigate.getValue());
		eipDTO.setBandwidth(1);

		return eipDTO;
	}

	public static ProducedDTO randomProducedDTO() {
		ProducedDTO dto = new ProducedDTO();
		dto.setEcsSpec(130); // 120 122 130
		dto.setIdc(ConstansData.idcId);

		return dto;
	}
}
