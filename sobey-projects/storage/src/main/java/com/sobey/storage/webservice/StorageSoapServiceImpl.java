package com.sobey.storage.webservice;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.storage.constans.WsConstants;
import com.sobey.storage.service.NetAppService;
import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.ModifytEs3RuleParameter;
import com.sobey.storage.webservice.response.dto.NetAppParameter;
import com.sobey.storage.webservice.response.dto.VolumeInfoDTO;
import com.sobey.storage.webservice.response.result.DTOListResult;
import com.sobey.storage.webservice.response.result.DTOResult;
import com.sobey.storage.webservice.response.result.WSResult;

@WebService(serviceName = "StorageSoapService", endpointInterface = "com.sobey.storage.webservice.StorageSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class StorageSoapServiceImpl implements StorageSoapService {

	@Autowired
	private NetAppService netAppService;

	@Override
	public WSResult createEs3ByStorage(CreateEs3Parameter createEs3Parameter) {
		return netAppService.createEs3(createEs3Parameter);
	}

	@Override
	public WSResult deleteEs3ByStorage(DeleteEs3Parameter deleteEs3Parameter) {
		return netAppService.deleteEs3(deleteEs3Parameter);
	}

	@Override
	public WSResult modifytEs3RuleParameterByStorage(ModifytEs3RuleParameter modifytEs3RuleParameter) {
		return netAppService.modifyEs3Rule(modifytEs3RuleParameter);
	}

	@Override
	public DTOListResult<VolumeInfoDTO> getVolumeInfoDTO(NetAppParameter netAppParameter) {
		return netAppService.getVolumeInfoDTO(netAppParameter);
	}

	@Override
	public DTOResult<VolumeInfoDTO> findVolumeInfoDTO(NetAppParameter netAppParameter) {
		return netAppService.findVolumeInfoDTO(netAppParameter);
	}

}
