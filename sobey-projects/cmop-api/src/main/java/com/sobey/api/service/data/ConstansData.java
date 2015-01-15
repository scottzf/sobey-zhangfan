package com.sobey.api.service.data;

import com.sobey.generate.cmdbuild.SubnetDTO;

public class ConstansData {

	public static final Integer idcId = 116;

	public static final String firewall_username = "admin";
	public static final String firewall_password = "mcloud@sobey.com";

	public static final String vRouter_default_ipaddress = "10.2.253.253";

	/**
	 * 电信默认连接端口 : port8
	 */
	public static final String CTC_DEFAULT_PORT = "port8";

	/**
	 * 电信默认连接端口组 :ISP_CTC_VLAN1000
	 */
	public static final String CTC_DEFAULT_PORTGROUPNAME = "ISP_CTC_VLAN1000";

	/**
	 * 电信默认连接端口序号 :8
	 */
	public static final int CTC_DEFAULT_PORTNO = 8;

	/**
	 * 电信默认连接端口序号 :221.237.156.153
	 */
	public static final String CTC_DEFAULT_IP = "221.237.156.153";

	/**
	 * 电信默认eip映射组名
	 */
	public static final String CTC_MAPPING_GROUP_NAME = "CTC_ALL_Server";

	public static SubnetDTO defaultSubnetDTO(Integer tenantsId) {

		String gateway = "192.168.100.1";
		String netmask = "255.255.255.0";
		String segment = "192.168.100.0";

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
