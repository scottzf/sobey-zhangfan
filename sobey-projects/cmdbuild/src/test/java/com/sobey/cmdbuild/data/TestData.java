package com.sobey.cmdbuild.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sobey.cmdbuild.constants.LookUpConstants;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.Dns;
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.entity.Elb;
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

	private static Date startDate = new Date(System.currentTimeMillis());

	private static Integer idcId = 99;
	private static Integer rackId = 101;
	private static Integer tenantsId = 105;
	private static Integer parentTagId = 107;
	private static Integer brandId = 56;
	private static Integer deviceTypeId = 23;
	private static Integer heightId = 69;
	private static Integer powerId = 77;
	private static Integer maintenanceId = 77;
	private static Integer deviceSpecId = 121;
	private static Integer osTypeId = 21;
	private static Integer ecsSpecId = 123;
	private static Integer vlanId = 125;
	private static Integer ipaddressId = 143;
	private static Integer serverId = 344;
	private static Integer switchId = 386;
	private static Integer diskTypeId = 24;

	// private static Date endDate = new Date(System.currentTimeMillis() + (60 * 60 * 24 * 7 * 1000));

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

	public static Tag randomTag() {
		Tag tag = new Tag();
		tag.setId(0);
		tag.setDescription(RandomData.randomName("description"));
		tag.setTagType(LookUpConstants.TagType.上级标签.getValue());
		tag.setRemark(RandomData.randomName("remark"));
		tag.setTenants(tenantsId);
		return tag;
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
		firewallPort.setCode(RandomData.randomName("code"));
		firewallPort.setDescription(RandomData.randomName("description"));
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
		ipaddress.setIsp(29);
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
		loadBalancerPort.setCode(RandomData.randomName("code"));
		loadBalancerPort.setDescription(RandomData.randomName("description"));
		return loadBalancerPort;
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

	public static StoragePort randomNetappPort() {

		StoragePort netappPort = new StoragePort();

		netappPort.setId(0);
		netappPort.setCode(RandomData.randomName("code"));
		netappPort.setDescription(RandomData.randomName("description"));
		netappPort.setBeginDate(startDate);

		return netappPort;
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
		nicPort.setCode(RandomData.randomName("code"));
		nicPort.setDescription(RandomData.randomName("description"));
		nicPort.setSite(RandomData.randomName("位置"));
		nicPort.setBeginDate(startDate);
		nicPort.setNic(0);

		nicPort.setMacAddress(RandomData.randomName("MAC地址"));

		return nicPort;
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
		serverPort.setCode(RandomData.randomName("code"));
		serverPort.setDescription(RandomData.randomName("description"));
		return serverPort;
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
		switchPort.setCode(RandomData.randomName("code"));
		switchPort.setDescription(RandomData.randomName("description"));
		return switchPort;
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

	public static Es3 randomAs2() {
		Es3 as2 = new Es3();
		as2.setDiskSize(1024);
		as2.setEs3Spec(0);
		as2.setIpaddress(0);
		as2.setNetAppController(0);
		as2.setRemark(RandomData.randomName("remark"));
		as2.setTag(117132);
		as2.setTenants(117129);
		as2.setVolumePath(RandomData.randomName("volumePath"));
		as2.setVolumeType(0);
		return as2;
	}

	public static Eip randomEip() {
		Eip eip = new Eip();
		eip.setBandwidth(1024);
		eip.setEipSpec(117135);
		eip.setEipStatus(58);
		eip.setIpaddress(563);
		eip.setRemark(RandomData.randomName("remark"));
		eip.setCode(RandomData.randomName("code"));
		eip.setDescription(RandomData.randomName("description"));
		eip.setTag(117132);
		eip.setTenants(117129);

		return eip;
	}

	public static Elb randomElb() {
		Elb elb = new Elb();
		elb.setIpaddress(0);
		elb.setIsSession(true);
		elb.setRemark(RandomData.randomName("remark"));
		elb.setTag(117132);
		elb.setTenants(117129);
		return elb;
	}

	public static Dns randomDns() {
		Dns dns = new Dns();
		dns.setCnameDomain(RandomData.randomName("cnameDomain"));
		dns.setDomainName(RandomData.randomName("domainName"));
		dns.setDomainType(62);
		dns.setRemark(RandomData.randomName("remark"));
		dns.setTag(117132);
		dns.setTenants(117129);
		dns.setCode(RandomData.randomName("code"));
		dns.setDescription(RandomData.randomName("desc"));

		return dns;
	}

	public static Esg randomEsg() {
		Esg esg = new Esg();
		esg.setId(0);
		esg.setCode(RandomData.randomName("code"));
		esg.setDescription(RandomData.randomName("description"));
		esg.setRemark(RandomData.randomName("remark"));
		esg.setAclNumber(2000);
		esg.setIsPublic(true);
		esg.setTag(92);
		esg.setTenants(117129);
		return esg;
	}

	public static EsgPolicy randomEsgPolicy() {
		EsgPolicy policy = new EsgPolicy();
		policy.setId(0);
		policy.setCode(RandomData.randomName("code"));
		policy.setDescription(RandomData.randomName("description"));
		policy.setEsg(95);
		policy.setSourceIp("10.10.0.0");
		policy.setTargetIp("10.10.8.8");
		policy.setPort(80);
		policy.setEsgProtocol(68);
		return policy;
	}

	public static Vpn randomVpn() {
		Vpn vpn = new Vpn();
		vpn.setRemark(RandomData.randomName("remark"));
		vpn.setTag(117132);
		vpn.setTenants(117129);
		vpn.setVpnName(RandomData.randomName("vpnName"));
		vpn.setVpnPassword(RandomData.randomName("vpnPassword"));
		return vpn;
	}

	public static Ecs randomEcs() {
		Ecs ecs = new Ecs();
		ecs.setId(0);
		ecs.setDescription(RandomData.randomName("description"));
		ecs.setRemark(RandomData.randomName("remark"));
		ecs.setTenants(108);
		ecs.setAgentType(47);
		ecs.setIdc(110);
		ecs.setIpaddress(180);
		ecs.setEcsSpec(164);
		ecs.setEcsStatus(34);
		ecs.setServer(328);
		;
		return ecs;
	}

}
