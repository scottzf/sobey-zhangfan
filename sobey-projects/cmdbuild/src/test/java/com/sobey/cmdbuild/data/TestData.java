package com.sobey.cmdbuild.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sobey.cmdbuild.constants.LookUpConstants;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.Dns;
import com.sobey.cmdbuild.entity.DnsPolicy;
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.entity.EipPolicy;
import com.sobey.cmdbuild.entity.Elb;
import com.sobey.cmdbuild.entity.ElbPolicy;
import com.sobey.cmdbuild.entity.Es3;
import com.sobey.cmdbuild.entity.Esg;
import com.sobey.cmdbuild.entity.EsgPolicy;
import com.sobey.cmdbuild.entity.Firewall;
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.entity.HardDisk;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.entity.LoadBalancerPort;
import com.sobey.cmdbuild.entity.Log;
import com.sobey.cmdbuild.entity.Memory;
import com.sobey.cmdbuild.entity.Nic;
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.entity.Storage;
import com.sobey.cmdbuild.entity.StorageBox;
import com.sobey.cmdbuild.entity.StoragePort;
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.entity.Vpn;
import com.sobey.test.data.RandomData;

public class TestData {

	private static Integer agentTypeId = 80;
	private static Integer brandId = 56;
	private static Integer deviceSpecId = 121;
	private static Integer deviceTypeId = 23;
	private static Integer diskTypeId = 24;
	private static Integer dnsId = 712;
	private static Integer ecsSpecId = 123;
	private static Integer eipId = 697;
	private static Integer elbId = 680;
	private static Integer esgId = 725;
	private static Integer heightId = 69;
	private static Integer idcId = 99;
	private static Integer ipaddressId = 143;
	private static Integer ispId = 29;
	private static Integer maintenanceId = 77;
	private static Integer osTypeId = 21;
	private static Integer parentTagId = 107;
	private static Integer powerId = 77;
	private static Integer rackId = 101;
	private static Integer serverId = 344;
	private static Integer serviceTypeId = 26;
	private static Integer storageId = 365;
	private static Integer switchPortId = 472;
	private static Integer tenantsId = 105;
	private static Integer vlanId = 125;

	private static Date startDate = new Date(System.currentTimeMillis());

	// private static Date endDate = new Date(System.currentTimeMillis() + (60 * 60 * 24 * 7 * 1000));

	public static DeviceSpec randomDeviceSpec() {

		DeviceSpec dev = new DeviceSpec();
		dev.setId(0);
		dev.setBeginDate(startDate);
		dev.setDescription(RandomData.randomName("description"));
		dev.setDeviceType(deviceTypeId);
		dev.setBrand(brandId);
		dev.setHeight(heightId);
		dev.setPower(powerId);
		dev.setMaintenance(maintenanceId);
		dev.setCpuModel(RandomData.randomName("i"));
		dev.setCpuNumber(2);
		dev.setHdNumber(RandomData.randomInt());
		dev.setRamNumber(RandomData.randomInt());
		dev.setNicNumber(RandomData.randomInt());
		dev.setRemark(RandomData.randomName("remark"));

		return dev;
	}

	public static Dns randomDns() {

		Dns dns = new Dns();

		dns.setId(0);
		dns.setDescription(RandomData.randomName("description"));
		dns.setIdc(idcId);
		dns.setRemark(RandomData.randomName("remark"));
		dns.setTenants(tenantsId);
		dns.setAgentType(agentTypeId);
		dns.setDomainName(RandomData.randomName("domainName"));
		dns.setCnameDomain(RandomData.randomName("cnameDomain"));
		dns.setDomainType(25);

		return dns;
	}

	public static DnsPolicy randomDnsPolicy() {

		DnsPolicy policy = new DnsPolicy();

		policy.setId(0);
		policy.setDescription(RandomData.randomName("description"));
		policy.setDns(dnsId);
		policy.setDnsProtocol(39);
		policy.setPort(RandomData.randomInt());

		return policy;
	}

	public static Ecs randomEcs() {

		Ecs ecs = new Ecs();

		ecs.setId(0);
		ecs.setDescription(RandomData.randomName("description"));
		ecs.setIdc(idcId);
		ecs.setIpaddress(ipaddressId);
		ecs.setRemark(RandomData.randomName("remark"));
		ecs.setServer(serverId);
		ecs.setTenants(tenantsId);
		ecs.setAgentType(agentTypeId);
		ecs.setEcsSpec(ecsSpecId);
		ecs.setEcsStatus(34);

		return ecs;
	}

	public static EcsSpec randomEcsSpec() {

		EcsSpec esc = new EcsSpec();

		esc.setId(0);
		esc.setNotes(RandomData.randomName("note"));
		esc.setDescription(RandomData.randomName("description"));
		esc.setBeginDate(startDate);
		esc.setCpuNumber(RandomData.randomInt());
		esc.setDiskSize(RandomData.randomInt());
		esc.setMemory(RandomData.randomInt());
		esc.setRemark(RandomData.randomName("remark"));
		esc.setOsType(osTypeId);

		return esc;
	}

	public static Eip randomEip() {

		Eip eip = new Eip();

		eip.setId(0);
		eip.setDescription(RandomData.randomName("description"));
		eip.setIdc(idcId);
		eip.setIpaddress(ipaddressId);
		eip.setRemark(RandomData.randomName("remark"));
		eip.setTenants(tenantsId);
		eip.setAgentType(agentTypeId);
		eip.setIsp(ispId);
		eip.setEipStatus(35);
		eip.setBandwidth(10);

		return eip;
	}

	public static EipPolicy randomEipPolicy() {

		EipPolicy policy = new EipPolicy();

		policy.setId(0);
		policy.setDescription(RandomData.randomName("description"));
		policy.setEip(eipId);
		policy.setEipProtocol(38);
		policy.setSourcePort(RandomData.randomInt());
		policy.setTargetPort(RandomData.randomInt());

		return policy;
	}

	public static Elb randomElb() {

		Elb elb = new Elb();

		elb.setId(0);
		elb.setDescription(RandomData.randomName("description"));
		elb.setIdc(idcId);
		elb.setIpaddress(ipaddressId);
		elb.setRemark(RandomData.randomName("remark"));
		elb.setTenants(tenantsId);
		elb.setAgentType(agentTypeId);

		return elb;
	}

	public static ElbPolicy randomElbPolicy() {

		ElbPolicy policy = new ElbPolicy();

		policy.setId(0);
		policy.setDescription(RandomData.randomName("description"));
		policy.setElb(elbId);
		policy.setElbProtocol(97);
		policy.setSourcePort(RandomData.randomInt());
		policy.setTargetPort(RandomData.randomInt());
		return policy;
	}

	public static Es3 randomEs3() {

		Es3 es3 = new Es3();

		es3.setId(0);
		es3.setDescription(RandomData.randomName("description"));
		es3.setIdc(idcId);
		es3.setRemark(RandomData.randomName("remark"));
		es3.setTenants(tenantsId);
		es3.setAgentType(agentTypeId);
		es3.setVolumeName(RandomData.randomName("volumeName"));
		es3.setStorage(storageId);
		es3.setEs3Type(93);
		es3.setDiskSize(1000);

		return es3;
	}

	public static Esg randomEsg() {

		Esg esg = new Esg();

		esg.setId(0);
		esg.setDescription(RandomData.randomName("description"));
		esg.setIdc(idcId);
		esg.setRemark(RandomData.randomName("remark"));
		esg.setTenants(tenantsId);
		esg.setAgentType(agentTypeId);
		esg.setAclNumber(RandomData.randomInt());
		esg.setIsDefault(true);
		return esg;
	}

	public static EsgPolicy randomEsgPolicy() {

		EsgPolicy policy = new EsgPolicy();

		policy.setId(0);
		policy.setDescription(RandomData.randomName("description"));
		policy.setEsg(esgId);
		policy.setSourceIp(RandomData.randomName("SourceIp"));
		policy.setTargetIp(RandomData.randomName("TargetIp"));
		policy.setPort(RandomData.randomInt());
		policy.setPolicyType(98);

		return policy;
	}

	public static Firewall randomFirewall() {
		Firewall firewall = new Firewall();
		firewall.setId(0);
		firewall.setIdc(idcId);
		firewall.setRack(rackId);
		firewall.setDeviceSpec(deviceSpecId);
		firewall.setIpaddress(ipaddressId);
		firewall.setDescription(RandomData.randomName("description"));
		firewall.setSn(RandomData.randomName("sn"));
		firewall.setGdzcSn(RandomData.randomName("gdzcSn"));
		firewall.setSite("1");
		firewall.setRemark(RandomData.randomName("remark"));
		return firewall;
	}

	public static FirewallPort randomFirewallPort() {

		FirewallPort firewallPort = new FirewallPort();

		firewallPort.setId(0);
		firewallPort.setDescription(RandomData.randomName("description"));
		firewallPort.setIdc(idcId);
		firewallPort.setIpaddress(ipaddressId);
		firewallPort.setRemark(RandomData.randomName("remark"));
		firewallPort.setMacAddress(RandomData.randomName("macAddress"));
		firewallPort.setSite(RandomData.randomName("site"));
		firewallPort.setConnectedTo(switchPortId);
		firewallPort.setFirewall(324);

		return firewallPort;
	}

	public static HardDisk randomHardDisk() {

		HardDisk hardDisk = new HardDisk();

		hardDisk.setId(0);
		hardDisk.setIdc(idcId);
		hardDisk.setRack(rackId);
		hardDisk.setBrand(brandId);
		hardDisk.setSite("1");
		hardDisk.setRemark(RandomData.randomName("remark"));
		hardDisk.setSn(RandomData.randomName("sn"));
		hardDisk.setGdzcSn(RandomData.randomName("gdzcSn"));
		hardDisk.setDescription(RandomData.randomName("description"));
		hardDisk.setServer(serverId);
		hardDisk.setRotationalSpeed(62);
		hardDisk.setHdSize(100);
		hardDisk.setDiskType(diskTypeId);

		return hardDisk;
	}

	public static Idc randomIdc() {

		Idc idc = new Idc();

		idc.setId(0);
		idc.setDescription(RandomData.randomName("description"));
		idc.setRemark(RandomData.randomName("remark"));
		idc.setCity(RandomData.randomName("city"));
		idc.setZip(RandomData.randomName("zip"));
		idc.setAddress(RandomData.randomName("address"));
		idc.setPhone(RandomData.randomName("phone"));

		return idc;
	}

	public static Ipaddress randomIpaddress() {

		Ipaddress ipaddress = new Ipaddress();

		ipaddress.setId(0);
		ipaddress.setDescription(RandomData.randomName("description"));
		ipaddress.setIpaddressPool(66);
		ipaddress.setIpaddressStatus(LookUpConstants.IPAddressStatus.未使用.getValue());
		ipaddress.setVlan(vlanId);
		ipaddress.setBeginDate(startDate);
		ipaddress.setIdc(idcId);
		ipaddress.setNetMask(RandomData.randomName("netMask"));
		ipaddress.setIsp(ispId);
		ipaddress.setGateway(RandomData.randomName("gateway"));
		ipaddress.setRemark(RandomData.randomName("remark"));

		return ipaddress;
	}

	public static List<Ipaddress> randomIpaddressList(int loopNum) {
		List<Ipaddress> list = new ArrayList<Ipaddress>();
		for (int i = 0; i < loopNum; i++) {
			list.add(randomIpaddress());
		}
		return list;
	}

	public static LoadBalancer randomLoadBalancer() {

		LoadBalancer loadBalancer = new LoadBalancer();
		loadBalancer.setId(0);
		loadBalancer.setIdc(idcId);
		loadBalancer.setRack(rackId);
		loadBalancer.setDeviceSpec(deviceSpecId);
		loadBalancer.setIpaddress(143);
		loadBalancer.setDescription(RandomData.randomName("description"));
		loadBalancer.setSn(RandomData.randomName("sn"));
		loadBalancer.setGdzcSn(RandomData.randomName("gdzcSn"));
		loadBalancer.setSite("1");
		loadBalancer.setRemark(RandomData.randomName("remark"));

		return loadBalancer;
	}

	public static LoadBalancerPort randomLoadBalancerPort() {
		LoadBalancerPort loadBalancerPort = new LoadBalancerPort();

		loadBalancerPort.setId(0);
		loadBalancerPort.setDescription(RandomData.randomName("description"));
		loadBalancerPort.setIdc(idcId);
		loadBalancerPort.setIpaddress(ipaddressId);
		loadBalancerPort.setRemark(RandomData.randomName("remark"));
		loadBalancerPort.setMacAddress(RandomData.randomName("macAddress"));
		loadBalancerPort.setSite(RandomData.randomName("site"));
		loadBalancerPort.setConnectedTo(switchPortId);
		loadBalancerPort.setLoadBalancer(334);

		return loadBalancerPort;
	}

	public static Log randomLog() {
		Log log = new Log();

		log.setId(0);
		log.setDescription(RandomData.randomName("description"));
		log.setTenants(tenantsId);
		log.setOperateType(21);
		log.setResult(63);
		log.setServiceType(serviceTypeId);

		return log;
	}

	public static Memory randomMemory() {

		Memory memory = new Memory();

		memory.setId(0);
		memory.setIdc(idcId);
		memory.setRack(rackId);
		memory.setBrand(brandId);
		memory.setSite("1");
		memory.setRemark(RandomData.randomName("remark"));
		memory.setSn(RandomData.randomName("sn"));
		memory.setGdzcSn(RandomData.randomName("gdzcSn"));
		memory.setDescription(RandomData.randomName("description"));
		memory.setServer(serverId);
		memory.setFrequency(32);
		memory.setSize(4);

		return memory;
	}

	public static Nic randomNic() {

		Nic nic = new Nic();

		nic.setId(0);
		nic.setIdc(idcId);
		nic.setRack(rackId);
		nic.setBrand(brandId);
		nic.setSite("1");
		nic.setRemark(RandomData.randomName("remark"));
		nic.setSn(RandomData.randomName("sn"));
		nic.setGdzcSn(RandomData.randomName("gdzcSn"));
		nic.setDescription(RandomData.randomName("description"));
		nic.setServer(serverId);

		nic.setNicRate(64);
		nic.setPortNumber(4);

		return nic;
	}

	public static NicPort randomNicPort() {

		NicPort nicPort = new NicPort();

		nicPort.setId(0);
		nicPort.setDescription(RandomData.randomName("description"));
		nicPort.setIdc(idcId);
		nicPort.setIpaddress(ipaddressId);
		nicPort.setRemark(RandomData.randomName("remark"));
		nicPort.setMacAddress(RandomData.randomName("macAddress"));
		nicPort.setSite(RandomData.randomName("site"));
		nicPort.setConnectedTo(switchPortId);
		nicPort.setNic(430);

		nicPort.setMacAddress(RandomData.randomName("MAC地址"));

		return nicPort;
	}

	public static Rack randomRack() {

		Rack rack = new Rack();
		rack.setId(0);
		rack.setDescription(RandomData.randomName("description"));
		rack.setRemark(RandomData.randomName("remark"));
		rack.setIdc(idcId);
		rack.setBrand(56);
		rack.setHeight(68);
		rack.setPower(77);
		rack.setUnitNumber(2);

		return rack;
	}

	public static Server randomServer() {

		Server server = new Server();
		server.setId(0);
		server.setDescription(RandomData.randomName("description"));
		server.setGdzcSn(RandomData.randomName("gdzcsn"));
		server.setIdc(idcId);
		server.setRack(rackId);
		server.setDeviceSpec(deviceSpecId);
		server.setIpaddress(ipaddressId);
		server.setSn(RandomData.randomName("sn"));
		server.setRemark(RandomData.randomName("remark"));
		server.setSite("1");
		server.setResgroup("Resgroup-1");

		return server;
	}

	public static ServerPort randomServerPort() {

		ServerPort serverPort = new ServerPort();

		serverPort.setId(0);
		serverPort.setDescription(RandomData.randomName("description"));
		serverPort.setIdc(idcId);
		serverPort.setIpaddress(ipaddressId);
		serverPort.setRemark(RandomData.randomName("remark"));
		serverPort.setMacAddress(RandomData.randomName("macAddress"));
		serverPort.setSite(RandomData.randomName("site"));
		serverPort.setConnectedTo(switchPortId);
		serverPort.setServer(serverId);

		return serverPort;
	}

	public static Storage randomStorage() {

		Storage storage = new Storage();

		storage.setId(0);
		storage.setIdc(idcId);
		storage.setRack(rackId);
		storage.setDeviceSpec(deviceSpecId);
		storage.setIpaddress(ipaddressId);
		storage.setDescription(RandomData.randomName("description"));
		storage.setSn(RandomData.randomName("sn"));
		storage.setGdzcSn(RandomData.randomName("gdzcSn"));
		storage.setSite("1");
		storage.setRemark(RandomData.randomName("remark"));
		storage.setConfigText(RandomData.randomName("configText"));
		storage.setPassword(RandomData.randomName("password"));

		return storage;
	}

	public static StorageBox randomStorageBox() {

		StorageBox netappBox = new StorageBox();

		netappBox.setId(0);
		netappBox.setIdc(idcId);
		netappBox.setRack(rackId);
		netappBox.setBrand(brandId);
		netappBox.setSite("1");
		netappBox.setRemark(RandomData.randomName("remark"));
		netappBox.setSn(RandomData.randomName("sn"));
		netappBox.setGdzcSn(RandomData.randomName("gdzcSn"));
		netappBox.setDescription(RandomData.randomName("description"));
		netappBox.setServer(serverId);
		netappBox.setDiskType(diskTypeId);
		netappBox.setDiskNumber(4);

		return netappBox;
	}

	public static StoragePort randomStoragePort() {

		StoragePort storagePort = new StoragePort();

		storagePort.setId(0);
		storagePort.setDescription(RandomData.randomName("description"));
		storagePort.setIdc(idcId);
		storagePort.setIpaddress(ipaddressId);
		storagePort.setRemark(RandomData.randomName("remark"));
		storagePort.setMacAddress(RandomData.randomName("macAddress"));
		storagePort.setSite(RandomData.randomName("site"));
		storagePort.setConnectedTo(switchPortId);
		storagePort.setStorage(storageId);

		return storagePort;
	}

	public static Tag randomSubTag() {
		Tag tag = new Tag();
		tag.setId(0);
		tag.setDescription(RandomData.randomName("description"));
		tag.setTagType(LookUpConstants.TagType.子标签.getValue());
		tag.setParentTag(parentTagId);
		tag.setRemark(RandomData.randomName("remark"));
		tag.setTenants(tenantsId);
		return tag;
	}

	public static Switches randomSwitches() {

		Switches switches = new Switches();
		switches.setId(0);
		switches.setIdc(idcId);
		switches.setRack(rackId);
		switches.setDeviceSpec(deviceSpecId);
		switches.setIpaddress(ipaddressId);
		switches.setDescription(RandomData.randomName("description"));
		switches.setSn(RandomData.randomName("sn"));
		switches.setGdzcSn(RandomData.randomName("gdzcSn"));
		switches.setSite("1");
		switches.setRemark(RandomData.randomName("remark"));

		return switches;
	}

	public static SwitchPort randomSwitchPort() {

		SwitchPort switchPort = new SwitchPort();

		switchPort.setId(0);
		switchPort.setDescription(RandomData.randomName("description"));
		switchPort.setIdc(idcId);
		switchPort.setIpaddress(ipaddressId);
		switchPort.setRemark(RandomData.randomName("remark"));
		switchPort.setMacAddress(RandomData.randomName("macAddress"));
		switchPort.setSite(RandomData.randomName("site"));
		switchPort.setConnectedTo(switchPortId);
		switchPort.setSwitches(386);

		return switchPort;
	}

	public static Tag randomTag() {
		Tag tag = new Tag();
		tag.setId(0);
		tag.setDescription(RandomData.randomName("description"));
		tag.setTagType(LookUpConstants.TagType.上级标签.getValue());
		tag.setRemark(RandomData.randomName("remark"));
		tag.setTenants(tenantsId);
		return tag;
	}

	public static Tenants randomTenants() {
		Tenants tenants = new Tenants();
		tenants.setId(0);
		tenants.setDescription(RandomData.randomName("description"));
		tenants.setPhone(RandomData.randomName("phone"));
		tenants.setRemark(RandomData.randomName("remark"));
		tenants.setPassword(RandomData.randomName("password"));
		tenants.setEmail(RandomData.randomName("email"));
		tenants.setAccessKey(RandomData.randomName("accessKey"));
		tenants.setCompany(RandomData.randomName("company"));
		tenants.setCreateInfo(RandomData.randomName("createInfo"));
		return tenants;
	}

	public static Vlan randomVlan() {

		Vlan vlan = new Vlan();

		vlan.setId(0);
		vlan.setDescription(RandomData.randomName("description"));
		vlan.setBeginDate(startDate);
		vlan.setTenants(tenantsId);
		vlan.setIdc(idcId);

		vlan.setRemark(RandomData.randomName("remark"));
		vlan.setSegment(RandomData.randomName("segment"));
		vlan.setNetMask(RandomData.randomName("netMask"));
		vlan.setGateway(RandomData.randomName("gateway"));

		return vlan;
	}

	public static Vpn randomVpn() {

		Vpn vpn = new Vpn();

		vpn.setId(0);
		vpn.setDescription(RandomData.randomName("description"));
		vpn.setIdc(idcId);
		vpn.setRemark(RandomData.randomName("remark"));
		vpn.setTenants(tenantsId);
		vpn.setAgentType(agentTypeId);
		vpn.setPassword(RandomData.randomName("password"));
		vpn.setPolicyId(RandomData.randomInt());

		return vpn;
	}

}
