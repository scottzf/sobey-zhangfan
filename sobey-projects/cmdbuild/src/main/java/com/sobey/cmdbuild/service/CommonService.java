package com.sobey.cmdbuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.cmdbuild.service.iaas.ConfigFirewallAddressService;
import com.sobey.cmdbuild.service.iaas.ConfigFirewallPolicyService;
import com.sobey.cmdbuild.service.iaas.ConfigRouterStaticService;
import com.sobey.cmdbuild.service.iaas.ConfigSystemInterfaceService;
import com.sobey.cmdbuild.service.iaas.DnsPolicyService;
import com.sobey.cmdbuild.service.iaas.DnsService;
import com.sobey.cmdbuild.service.iaas.EcsService;
import com.sobey.cmdbuild.service.iaas.EipPolicyService;
import com.sobey.cmdbuild.service.iaas.EipService;
import com.sobey.cmdbuild.service.iaas.ElbPolicyService;
import com.sobey.cmdbuild.service.iaas.ElbService;
import com.sobey.cmdbuild.service.iaas.Es3Service;
import com.sobey.cmdbuild.service.iaas.FirewallServiceService;
import com.sobey.cmdbuild.service.iaas.LogService;
import com.sobey.cmdbuild.service.iaas.MapEcsEipService;
import com.sobey.cmdbuild.service.iaas.MapEcsElbService;
import com.sobey.cmdbuild.service.iaas.MapEcsEs3Service;
import com.sobey.cmdbuild.service.iaas.MapEipDnsService;
import com.sobey.cmdbuild.service.iaas.MapEipElbService;
import com.sobey.cmdbuild.service.iaas.MapRouterFirewallServiceService;
import com.sobey.cmdbuild.service.iaas.MapTagServiceService;
import com.sobey.cmdbuild.service.iaas.RouterService;
import com.sobey.cmdbuild.service.iaas.ServiceService;
import com.sobey.cmdbuild.service.iaas.VpnService;
import com.sobey.cmdbuild.service.infrastructure.ConfigFirewallServiceCategoryService;
import com.sobey.cmdbuild.service.infrastructure.CustomService;
import com.sobey.cmdbuild.service.infrastructure.FirewallPortService;
import com.sobey.cmdbuild.service.infrastructure.FirewallService;
import com.sobey.cmdbuild.service.infrastructure.HardDiskService;
import com.sobey.cmdbuild.service.infrastructure.IpaddressService;
import com.sobey.cmdbuild.service.infrastructure.LoadBalancerPortService;
import com.sobey.cmdbuild.service.infrastructure.LoadBalancerService;
import com.sobey.cmdbuild.service.infrastructure.MemoryService;
import com.sobey.cmdbuild.service.infrastructure.NicPortService;
import com.sobey.cmdbuild.service.infrastructure.NicService;
import com.sobey.cmdbuild.service.infrastructure.ServerPortService;
import com.sobey.cmdbuild.service.infrastructure.ServerService;
import com.sobey.cmdbuild.service.infrastructure.StorageBoxService;
import com.sobey.cmdbuild.service.infrastructure.StoragePortService;
import com.sobey.cmdbuild.service.infrastructure.StorageService;
import com.sobey.cmdbuild.service.infrastructure.SubnetService;
import com.sobey.cmdbuild.service.infrastructure.SwitchPortService;
import com.sobey.cmdbuild.service.infrastructure.SwitchesService;
import com.sobey.cmdbuild.service.infrastructure.VlanService;
import com.sobey.cmdbuild.service.organisation.IdcService;
import com.sobey.cmdbuild.service.organisation.LookUpService;
import com.sobey.cmdbuild.service.organisation.RackService;
import com.sobey.cmdbuild.service.organisation.TagService;
import com.sobey.cmdbuild.service.organisation.TenantsService;
import com.sobey.cmdbuild.service.specification.DeviceSpecService;
import com.sobey.cmdbuild.service.specification.EcsSpecService;

/**
 * Service引用公共类,将所有业务的service方法统一在此类中注入.
 * 
 * @author Administrator
 * 
 */
@Service
public class CommonService {

	@Autowired
	public ConfigFirewallAddressService configFirewallAddressService;

	@Autowired
	public ConfigFirewallPolicyService configFirewallPolicyService;

	@Autowired
	public ConfigFirewallServiceCategoryService configFirewallServiceCategoryService;

	@Autowired
	public ConfigRouterStaticService configRouterStaticService;

	@Autowired
	public ConfigSystemInterfaceService configSystemInterfaceService;

	@Autowired
	public CustomService customService;

	@Autowired
	public DeviceSpecService deviceSpecService;

	@Autowired
	public DnsPolicyService dnsPolicyService;

	@Autowired
	public DnsService dnsService;

	@Autowired
	public EcsService ecsService;

	@Autowired
	public EcsSpecService ecsSpecService;

	@Autowired
	public EipPolicyService eipPolicyService;

	@Autowired
	public EipService eipService;

	@Autowired
	public ElbPolicyService elbPolicyService;

	@Autowired
	public ElbService elbService;

	@Autowired
	public Es3Service es3Service;

	@Autowired
	public FirewallPortService firewallPortService;

	@Autowired
	public FirewallService firewallService;

	@Autowired
	public FirewallServiceService firewallServiceService;

	@Autowired
	public HardDiskService hardDiskService;

	@Autowired
	public IdcService idcService;

	@Autowired
	public IpaddressService ipaddressService;

	@Autowired
	public LoadBalancerPortService loadBalancerPortService;

	@Autowired
	public LoadBalancerService loadBalancerService;

	@Autowired
	public LogService logService;

	@Autowired
	public LookUpService lookUpService;

	@Autowired
	public MapEcsEipService mapEcsEipService;

	@Autowired
	public MapEcsElbService mapEcsElbService;

	@Autowired
	public MapEcsEs3Service mapEcsEs3Service;

	@Autowired
	public RouterService mapEcsEsgService;

	@Autowired
	public MapEipDnsService mapEipDnsService;

	@Autowired
	public MapEipElbService mapEipElbService;

	@Autowired
	public MapRouterFirewallServiceService mapRouterFirewallServiceService;

	@Autowired
	public MapTagServiceService mapTagServiceService;

	@Autowired
	public MemoryService memoryService;

	@Autowired
	public NicPortService nicPortService;

	@Autowired
	public NicService nicService;

	@Autowired
	public RackService rackService;

	@Autowired
	public RouterService routerService;

	@Autowired
	public ServerPortService serverPortService;

	@Autowired
	public ServerService serverService;

	@Autowired
	public ServiceService serviceService;

	@Autowired
	public StorageBoxService storageBoxService;

	@Autowired
	public StoragePortService storagePortService;

	@Autowired
	public StorageService storageService;

	@Autowired
	public SubnetService subnetService;

	@Autowired
	public SwitchesService switchesService;

	@Autowired
	public SwitchPortService switchPortService;

	@Autowired
	public TagService tagService;

	@Autowired
	public TenantsService tenantsService;

	@Autowired
	public VlanService vlanService;

	@Autowired
	public VpnService vpnService;

}
