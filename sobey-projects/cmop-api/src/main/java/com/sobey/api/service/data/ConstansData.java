package com.sobey.api.service.data;

import com.sobey.generate.cmdbuild.SubnetDTO;

public class ConstansData {

	public static final Integer idcId = 1;

	public static SubnetDTO defaultSubnetDTO(Integer tenantsId) {

		String gateway = null;
		String netmask = null;
		String segment = null;

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
