package com.sobey.api.service.data;

import com.sobey.generate.cmdbuild.SubnetDTO;

public class ConstansData {

	public static final Integer idcId = 116;

	public static final String firewall_username = "admin";
	public static final String firewall_password = "mcloud@sobey.com";

	public static final String vRouter_default_ipaddress = "10.2.253.253";

	public static SubnetDTO defaultSubnetDTO(Integer tenantsId) {

		String gateway = "192.168.100.254";
		String netmask = "255.255.255.0";
		String segment = "192.168.100.0/24";

		SubnetDTO subnetDTO = new SubnetDTO();
		subnetDTO.setIdc(idcId);
		subnetDTO.setGateway(gateway);
		subnetDTO.setNetMask(netmask);
		subnetDTO.setTenants(tenantsId);
		subnetDTO.setSegment(segment);
		subnetDTO.setDescription("默认子网");

		return subnetDTO;
	}

}
