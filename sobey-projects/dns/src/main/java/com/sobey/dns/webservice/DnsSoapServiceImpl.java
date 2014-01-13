package com.sobey.dns.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.dns.constans.WsConstants;
import com.sobey.dns.webservice.response.dto.DNSParameter;
import com.sobey.dns.webservice.response.result.WSResult;

@WebService(serviceName = "DnsSoapService", endpointInterface = "com.sobey.dns.webservice.DnsSoapService", targetNamespace = WsConstants.NS)
public class DnsSoapServiceImpl implements DnsSoapService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	protected static PropertiesLoader DNS_LOADER = new PropertiesLoader("classpath:/dns.properties");

	/* DNS登录 */
	protected static final String DNS_IP = DNS_LOADER.getProperty("DNS_IP");
	protected static final String DNS_USERNAME = DNS_LOADER.getProperty("DNS_USERNAME");
	protected static final String DNS_PASSWORD = DNS_LOADER.getProperty("DNS_PASSWORD");

	@Override
	public WSResult createDNSByDNS(@WebParam(name = "DNSParameter") DNSParameter parameter) {

		String command = com.sobey.dns.script.GenerateScript.generateCreateDNSScript(parameter);

		TelnetUtil.execCommand(DNS_IP, DNS_USERNAME, DNS_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

	@Override
	public WSResult deleteDNSByDNS(@WebParam(name = "DNSParameter") DNSParameter parameter) {

		String command = com.sobey.dns.script.GenerateScript.generateDeleteDNSScript(parameter);

		TelnetUtil.execCommand(DNS_IP, DNS_USERNAME, DNS_PASSWORD, command);

		// TODO 缺少针对返回字符串解析是否执行成功的判断.

		return new WSResult();
	}

}
