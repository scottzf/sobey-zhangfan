package com.sobey.cmdbuild.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service引用公共类,将所有业务的service方法统一在此类中注入.
 * 
 * @author Administrator
 * 
 */
@Service
public class CommonService {

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
	public FirewallPolicyService firewallPolicyService;

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
