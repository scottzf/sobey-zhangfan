package com.sobey.dns.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.dns.constans.WsConstants;
import com.sobey.dns.service.NitroService;
import com.sobey.dns.webservice.response.dto.DNSParameter;
import com.sobey.dns.webservice.response.result.WSResult;

@WebService(serviceName = "DnsSoapService", endpointInterface = "com.sobey.dns.webservice.DnsSoapService", targetNamespace = WsConstants.NS)
public class DnsSoapServiceImpl implements DnsSoapService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	protected static PropertiesLoader DNS_LOADER = new PropertiesLoader("classpath:/dns.properties");

	@Autowired
	public NitroService nitroService;

	@Override
	public WSResult createDNSByDNS(@WebParam(name = "DNSParameter") DNSParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = nitroService.createDns(parameter);

		if (!falg) {
			result.setCode(WSResult.PARAMETER_ERROR);
		}

		return result;
	}

	@Override
	public WSResult deleteDNSByDNS(@WebParam(name = "DNSParameter") DNSParameter parameter) {

		WSResult result = new WSResult();

		boolean falg = nitroService.deleteDns(parameter);

		if (!falg) {
			result.setCode(WSResult.PARAMETER_ERROR);
		}

		return result;
	}

}
