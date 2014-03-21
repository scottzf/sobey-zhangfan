package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.dns.DNSParameter;
import com.sobey.generate.dns.DnsSoapService;
import com.sobey.generate.dns.WSResult;

/**
 * Instance
 * 
 * @author Administrator
 * 
 */
@Service
public class DnsService {

	@Autowired
	private DnsSoapService dnsSoapService;

	public WSResult createDNS(DNSParameter dnsParameter) {
		return dnsSoapService.createDNSByDNS(dnsParameter);
	}

	public WSResult deleteDNS(DNSParameter dnsParameter) {
		return dnsSoapService.deleteDNSByDNS(dnsParameter);
	}

}
