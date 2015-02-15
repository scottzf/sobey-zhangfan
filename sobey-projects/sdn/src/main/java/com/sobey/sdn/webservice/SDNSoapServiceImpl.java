package com.sobey.sdn.webservice;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.sdn.bean.Firewall;
import com.sobey.sdn.bean.Router;
import com.sobey.sdn.bean.Subnet;
import com.sobey.sdn.constans.WsConstants;
import com.sobey.sdn.service.SDNService;
import com.sobey.sdn.test.testParameter.CreateECSParameter;
import com.sobey.sdn.test.testParameter.CreateRouterParameter;

@WebService(serviceName = "SDNSoapService", endpointInterface = "com.sobey.sdn.webservice.SDNSoapService", targetNamespace = WsConstants.NS)
public class SDNSoapServiceImpl implements SDNSoapService {

	@Autowired
	private SDNService sdnService;

	@Override
	public void createECS(CreateECSParameter createECSParameter) throws Exception {

		sdnService.createECS(createECSParameter);
	}

	@Override
	public void createRouter(CreateRouterParameter createRouterParameter) throws Exception {

		sdnService.createRouter(createRouterParameter);

	}

	@Override
	public void bindingRouter(Router router, List<Subnet> subnets) throws Exception {

	}

	@Override
	public void createFirewall(Firewall firewall) throws Exception {
		sdnService.createFirewall(firewall);
	}

	@Override
	public void bindingFirewall(Router router, Firewall firewall) throws Exception {
		// sdnService.bindingFirewall(router,firewall);
	}
}
