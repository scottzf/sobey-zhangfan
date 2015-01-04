package com.sobey.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.core.utils.Encodes;
import com.sobey.core.utils.Identities;
import com.sobey.generate.cmdbuild.CmdbuildSoapService;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.ElbDTO;
import com.sobey.generate.cmdbuild.ElbPolicyDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.dns.DnsSoapService;
import com.sobey.generate.firewall.FirewallSoapService;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.loadbalancer.LoadbalancerSoapService;
import com.sobey.generate.storage.StorageSoapService;
import com.sobey.generate.switches.SwitchesSoapService;
import com.sobey.generate.zabbix.ZabbixSoapService;

@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	private CmdbuildSoapService cmdbuildSoapService;

	@Autowired
	private FirewallSoapService firewallSoapService;

	@Autowired
	private SwitchesSoapService switchesSoapService;

	@Autowired
	private InstanceSoapService instanceSoapService;

	@Autowired
	private StorageSoapService storageSoapService;

	@Autowired
	private LoadbalancerSoapService loadbalancerSoapService;

	@Autowired
	private DnsSoapService dnsSoapService;

	@Autowired
	private ZabbixSoapService zabbixSoapService;

	@Override
	public WSResult createTenants(TenantsDTO tenantsDTO) {

		WSResult result = new WSResult();

		/**
		 * Step.1 在CMDB中创建Tenants
		 * 
		 * Step.2 在CMDB中为Tenants分配一个默认的Subnet
		 * 
		 */

		// Step.1 在CMDB中创建Tenants

		// 密钥生成:基于Base62编码的SecureRandom随机生成bytes.
		tenantsDTO.setAccessKey(Encodes.encodeBase64(Identities.randomBase62(16).getBytes()));
		cmdbuildSoapService.createTenants(tenantsDTO);

		// 获得创建的Tenants对象
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_description", tenantsDTO.getDescription());

		TenantsDTO queryTenantsDTO = (TenantsDTO) cmdbuildSoapService.findTenantsByParams(
				CMDBuildUtil.wrapperSearchParams(map)).getDto();

		// Step.2 在CMDB中为Tenants分配一个默认的Subnet
		result = createSubnet();
		if (!WSResult.SUCESS.equals(result.getCode())) {
			return result;
		}

		return result;
	}

	@Override
	public WSResult createSubnet() {

		/**
		 * Step.1 判断Subnet是否存在
		 * 
		 * Step.2 判断Subnet是否存在,不存在的话将其保存至CMDB中
		 * 
		 * Step.3 由Subnet生成Ipaddress,保存至CMDB中
		 * 
		 */

		return null;
	}

	@Override
	public WSResult deleteSubnet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createECS(EcsDTO ecsDTO) {
		// TODO Auto-generated method stub

		/**
		 * Step.1 创建ECS
		 * 
		 * Step.2 绑定端口组
		 * 
		 * Step.3 保存CMDB
		 * 
		 */
		return null;
	}

	@Override
	public WSResult destroyECS(Integer ecsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult powerOpsECS(Integer ecsId, String powerOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult reconfigECS(Integer ecsId, Integer ecsSpecId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createES3(Es3DTO es3dto, String vmName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteES3(Integer es3Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult applyEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult recoverEIP(Integer eipId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult bindingEIP(Integer eipId, Integer serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult unbindingEIP(Integer eipId, Integer serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createELB(ElbDTO elbDTO, List<ElbPolicyDTO> elbPolicyDTOs, Integer[] ecsIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteELB(Integer elbId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult bindingELB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult unbindingELB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createDNS(DnsDTO dnsDTO, List<DnsPolicyDTO> dnsPolicyDTOs, Integer[] eipIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteDNS(Integer dnsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult bindingRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult unbindingRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult createFirewall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult deleteFirewall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult bindingFirewall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WSResult unbindingFirewall() {
		// TODO Auto-generated method stub
		return null;
	}

}
