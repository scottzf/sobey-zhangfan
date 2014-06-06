package com.sobey.dns.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.dns.constans.WsConstants;
import com.sobey.dns.service.DnsService;
import com.sobey.dns.webservice.response.dto.DNSParameter;
import com.sobey.dns.webservice.response.result.WSResult;

@WebService(serviceName = "DnsSoapService", endpointInterface = "com.sobey.dns.webservice.DnsSoapService", targetNamespace = WsConstants.NS)
public class DnsSoapServiceImpl implements DnsSoapService {

	@Autowired
	public DnsService service;

	@Override
	public WSResult createDNSByDNS(@WebParam(name = "dnsParameter") DNSParameter dnsParameter) {

		WSResult result = new WSResult();

		boolean flag = service.createGSLB(dnsParameter);

		if (!flag) {
			result.setError(WSResult.SYSTEM_ERROR, "DNS创建失败");
		}

		return result;
	}

	@Override
	public WSResult deleteDNSByDNS(@WebParam(name = "dnsParameter") DNSParameter dnsParameter) {

		WSResult result = new WSResult();

		boolean flag = service.deleteGSLB(dnsParameter);

		if (!flag) {
			result.setError(WSResult.SYSTEM_ERROR, "DNS删除失败");
		}

		return result;
	}

}
