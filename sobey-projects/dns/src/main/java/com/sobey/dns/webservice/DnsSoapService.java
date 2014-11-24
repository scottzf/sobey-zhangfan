package com.sobey.dns.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.dns.constans.WsConstants;
import com.sobey.dns.webservice.response.dto.DNSParameter;
import com.sobey.dns.webservice.response.dto.DnsSync;
import com.sobey.dns.webservice.response.result.DTOListResult;
import com.sobey.dns.webservice.response.result.WSResult;

/**
 * DNS对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "DnsSoapService", targetNamespace = WsConstants.NS)
public interface DnsSoapService {

	/**
	 * 在DNS上创建DNS
	 * 
	 * @param dnsParameter
	 *            {@link DNSParameter}
	 * @return
	 */
	WSResult createDNSByDNS(@WebParam(name = "dnsParameter") DNSParameter dnsParameter);

	/**
	 * 在DNS上删除DNS
	 * 
	 * @param dnsParameter
	 *            {@link DNSParameter}
	 * @return
	 */
	WSResult deleteDNSByDNS(@WebParam(name = "dnsParameter") DNSParameter dnsParameter);

	/**
	 * 获得物理设备上DNS相关的所有配置信息.
	 * 
	 * @return
	 */
	DTOListResult<DnsSync> getDNSConfig();
}
