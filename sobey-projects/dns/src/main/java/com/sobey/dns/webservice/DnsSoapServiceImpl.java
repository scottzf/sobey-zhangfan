package com.sobey.dns.webservice;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.dns.constans.WsConstants;
import com.sobey.dns.service.DnsService;
import com.sobey.dns.webservice.response.dto.DNSParameter;
import com.sobey.dns.webservice.response.dto.DnsSync;
import com.sobey.dns.webservice.response.result.DTOListResult;
import com.sobey.dns.webservice.response.result.WSResult;

@WebService(serviceName = "DnsSoapService", endpointInterface = "com.sobey.dns.webservice.DnsSoapService", targetNamespace = WsConstants.NS)
public class DnsSoapServiceImpl implements DnsSoapService {

	@Autowired
	public DnsService service;

	@Override
	public WSResult createDNSByDNS(DNSParameter dnsParameter) {

		return service.createGSLB(dnsParameter);
	}

	@Override
	public WSResult deleteDNSByDNS(DNSParameter dnsParameter) {

		return service.deleteGSLB(dnsParameter);
	}

	@Override
	public DTOListResult<DnsSync> getDNSConfig(DNSParameter dnsParameter) {
		return service.getDnsSyncList(dnsParameter);
	}

}
