package com.sobey.cmdbuild.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sobey.cmdbuild.constants.DeviceTypeEnum;
import com.sobey.cmdbuild.entity.As2;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.entity.Consumptions;
import com.sobey.cmdbuild.entity.Cs2;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.Dns;
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.entity.EipSpec;
import com.sobey.cmdbuild.entity.Elb;
import com.sobey.cmdbuild.entity.Es3Spec;
import com.sobey.cmdbuild.entity.Esg;
import com.sobey.cmdbuild.entity.Fimas;
import com.sobey.cmdbuild.entity.FimasBox;
import com.sobey.cmdbuild.entity.FimasPort;
import com.sobey.cmdbuild.entity.Firewall;
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.entity.GroupPolicy;
import com.sobey.cmdbuild.entity.HardDisk;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.entity.LoadBalancerPort;
import com.sobey.cmdbuild.entity.Memory;
import com.sobey.cmdbuild.entity.NetappBox;
import com.sobey.cmdbuild.entity.NetappController;
import com.sobey.cmdbuild.entity.NetappPort;
import com.sobey.cmdbuild.entity.Nic;
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.entity.Vpn;
import com.sobey.cmdbuild.webservice.response.dto.InfrastructureDTO;
import com.sobey.cmdbuild.webservice.response.dto.InfrastructurePortDTO;
import com.sobey.test.data.RandomData;

public class TestData {

	private static Date startDate = new Date(System.currentTimeMillis());
	private static Date endDate = new Date(System.currentTimeMillis() + (60 * 60 * 24 * 7 * 1000));

	public static Company randomCompany() {
		Company company = new Company();
		company.setId(0);
		company.setCode(RandomData.randomName("code"));
		company.setDescription(RandomData.randomName("description"));

		company.setPhone(RandomData.randomName("phone"));
		company.setAddress(RandomData.randomName("address"));
		company.setZip(RandomData.randomName("zip"));
		company.setRemark(RandomData.randomName("remark"));
		return company;
	}

	public static Tenants randomTenants() {
		Tenants tenants = new Tenants();
		tenants.setId(0);
		tenants.setCode(RandomData.randomName("code"));
		tenants.setDescription(RandomData.randomName("description"));

		tenants.setPhone(RandomData.randomName("phone"));
		tenants.setRemark(RandomData.randomName("remark"));
		tenants.setAccontBalance(RandomData.randomDouble());
		tenants.setCompany(86);
		tenants.setPassword(RandomData.randomName("password"));
		tenants.setEmail(RandomData.randomName("email"));
		return tenants;
	}

	public static Tag randomTag() {
		Tag tag = new Tag();
		tag.setId(0);
		tag.setCode(RandomData.randomName("code"));
		tag.setDescription(RandomData.randomName("description"));

		tag.setRemark(RandomData.randomName("remark"));
		tag.setTenants(217);
		return tag;
	}

	public static Idc randomIdc() {

		Idc idc = new Idc();

		idc.setId(0);
		idc.setCode(RandomData.randomName("code"));
		idc.setDescription(RandomData.randomName("description"));
		// idc.setBeginDate(startDate);

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
		rack.setCode(RandomData.randomName("code"));
		rack.setDescription(RandomData.randomName("description"));

		rack.setRemark(RandomData.randomName("remark"));
		rack.setIdc(90);

		return rack;
	}

	public static Consumptions randomConsumptions() {
		Consumptions con = new Consumptions();

		con.setId(0);
		con.setNotes(RandomData.randomName("note"));
		con.setCode(RandomData.randomName("code10"));
		con.setConsumptionsStatus(69);// 执行中

		con.setServiceStart(startDate);
		con.setServiceEnd(endDate);
		con.setIdentifier(RandomData.randomName("fuwuid"));
		con.setTenants(87);

		con.setDescription(RandomData.randomName("description"));
		con.setRemark(RandomData.randomName("remark"));
		con.setSpending(RandomData.randomDouble());

		return con;
	}

	public static DeviceSpec randomDeviceSpec() {
		DeviceSpec dev = new DeviceSpec();

		dev.setId(0);
		dev.setDeviceType(26);
		dev.setBeginDate(startDate);
		dev.setCode(RandomData.randomName("code10"));
		dev.setDescription(RandomData.randomName("description"));

		dev.setBrand(0);
		dev.setHeight(0);
		dev.setMaintenance(0);
		dev.setCpuModel(RandomData.randomName("i"));
		dev.setHdNumber(RandomData.randomInt());
		dev.setRamNumber(RandomData.randomInt());
		dev.setPower(RandomData.randomInt());
		dev.setPrice(RandomData.randomDouble());
		dev.setNicNumber(RandomData.randomInt());
		dev.setRemark(RandomData.randomName("remark"));

		return dev;
	}

	public static EcsSpec randomEcsSpec() {
		EcsSpec esc = new EcsSpec();

		esc.setId(0);
		esc.setNotes(RandomData.randomName("note"));
		esc.setCode(RandomData.randomName("code10"));
		esc.setDescription(RandomData.randomName("description"));
		esc.setBeginDate(startDate);
		esc.setCpuNumber(RandomData.randomInt());
		esc.setDiskSize(RandomData.randomInt());
		esc.setMemory(RandomData.randomInt());
		esc.setPrice(RandomData.randomDouble());

		esc.setRemark(RandomData.randomName("remark"));
		esc.setBrand(0);

		return esc;
	}

	public static EipSpec randomEipSpec() {
		EipSpec eip = new EipSpec();

		eip.setId(0);
		eip.setBeginDate(startDate);
		eip.setCode(RandomData.randomName("code10"));
		eip.setDescription(RandomData.randomName("description"));
		eip.setPrice(RandomData.randomDouble());

		eip.setRemark(RandomData.randomName("remark"));
		eip.setBrand(0);

		return eip;
	}

	public static Es3Spec randomEs3Spec() {
		Es3Spec es3 = new Es3Spec();

		es3.setId(0);
		es3.setBrand(0);
		es3.setCode(RandomData.randomName("code10"));
		es3.setDescription(RandomData.randomName("description"));

		es3.setMaxSpace(RandomData.randomInt());
		es3.setPrice(RandomData.randomDouble());
		es3.setRemark(RandomData.randomName("remark"));
		es3.setBrand(0);
		es3.setBeginDate(startDate);

		return es3;
	}

	public static Fimas randomFimas() {

		Fimas fimas = new Fimas();

		fimas.setId(0);
		fimas.setCode(RandomData.randomName("code"));
		fimas.setDescription(RandomData.randomName("description"));

		return fimas;
	}

	public static FimasBox randomFimasBox() {

		FimasBox fimasBox = new FimasBox();

		fimasBox.setId(0);
		fimasBox.setCode(RandomData.randomName("code"));
		fimasBox.setDescription(RandomData.randomName("description"));
		fimasBox.setBeginDate(startDate);
		fimasBox.setIdc(90);
		fimasBox.setRack(218);
		fimasBox.setDeviceSpec(226);
		fimasBox.setDiskType(53);// SATA
		fimasBox.setDiskNumber(RandomData.randomInt());
		fimasBox.setSite(0);

		// 非必须参数
		fimasBox.setIpaddress(94);
		// fimasBox.setNotes(RandomData.randomName("nodes"));
		fimasBox.setSn(RandomData.randomName("sn"));
		fimasBox.setGdzcSn(RandomData.randomName("gdzcSn"));
		fimasBox.setRemark(RandomData.randomName("remark"));

		return fimasBox;
	}

	public static FimasPort randomFimasPort() {
		FimasPort fimasPort = new FimasPort();
		fimasPort.setId(0);
		fimasPort.setCode(RandomData.randomName("code"));
		fimasPort.setDescription(RandomData.randomName("description"));
		return fimasPort;
	}

	public static Firewall randomFirewall() {
		Firewall firewall = new Firewall();
		firewall.setId(0);
		firewall.setCode(RandomData.randomName("code"));
		firewall.setDescription(RandomData.randomName("description"));
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
		hardDisk.setCode(RandomData.randomName("code"));
		hardDisk.setDescription(RandomData.randomName("description"));
		hardDisk.setBeginDate(startDate);
		hardDisk.setHardDiskSize(1024);
		hardDisk.setIdc(85);

		// 非必须参数
		// hardDisk.setServer(0);

		return hardDisk;
	}

	public static Ipaddress randomIpaddress() {

		Ipaddress ipaddress = new Ipaddress();

		ipaddress.setId(0);
		ipaddress.setCode(RandomData.randomName("code"));
		ipaddress.setDescription(RandomData.randomName("description"));
		ipaddress.setIpaddressPool(46);// ip协议
		ipaddress.setIpaddressStatus(49);
		ipaddress.setVlan(91);
		ipaddress.setBeginDate(startDate);

		ipaddress.setNetMask(RandomData.randomName("netMask"));
		ipaddress.setIsp(44);
		ipaddress.setGateway(RandomData.randomName("gateway"));

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
		loadBalancer.setCode(RandomData.randomName("code"));
		loadBalancer.setDescription(RandomData.randomName("description"));
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
		memory.setCode(RandomData.randomName("code"));
		memory.setDescription(RandomData.randomName("description"));
		memory.setBeginDate(startDate);
		memory.setIdc(0);
		memory.setRam(4);

		// 非必须参数
		memory.setBrand(0);
		memory.setFimas(0);
		memory.setFrequency(0);
		memory.setNotes(RandomData.randomName("notes"));
		memory.setServer(0);

		return memory;
	}

	public static NetappBox randomNetappBox() {

		NetappBox netappBox = new NetappBox();

		netappBox.setId(0);
		netappBox.setCode(RandomData.randomName("code"));
		netappBox.setDescription(RandomData.randomName("description"));
		netappBox.setBeginDate(startDate);
		netappBox.setIdc(90);
		netappBox.setRack(218);
		netappBox.setDeviceSpec(226);
		netappBox.setDiskNumber(RandomData.randomInt());
		netappBox.setDiskType(53);// SATA

		// 非必须参数
		netappBox.setGdzcSn(RandomData.randomName("gdzcSn"));
		netappBox.setRemark(RandomData.randomName("remark"));
		netappBox.setSn(RandomData.randomName("sn"));
		netappBox.setIpaddress(94);

		return netappBox;
	}

	public static NetappController randomNetappController() {
		NetappController netappController = new NetappController();
		netappController.setId(0);
		netappController.setCode(RandomData.randomName("code"));
		netappController.setDescription(RandomData.randomName("description"));
		return netappController;
	}

	public static NetappPort randomNetappPort() {

		NetappPort netappPort = new NetappPort();

		netappPort.setId(0);
		netappPort.setCode(RandomData.randomName("code"));
		netappPort.setDescription(RandomData.randomName("description"));
		netappPort.setBeginDate(startDate);

		return netappPort;
	}

	public static Nic randomNic() {

		Nic nic = new Nic();

		nic.setId(0);
		nic.setCode(RandomData.randomName("code"));
		nic.setDescription(RandomData.randomName("description"));
		nic.setBeginDate(startDate);
		nic.setPortNumber(3);
		nic.setIdc(0);

		nic.setBrand(0);

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
		server.setCode(RandomData.randomName("code"));
		server.setDescription(RandomData.randomName("description"));

		server.setGdzcSn(RandomData.randomName("gdzcSn"));
		server.setIdc(129);
		server.setRack(224);
		server.setSn(RandomData.randomName("sn"));

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
		switches.setCode(RandomData.randomName("code"));
		switches.setDescription(RandomData.randomName("description"));
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
		vlan.setCode(RandomData.randomName("code"));
		vlan.setDescription(RandomData.randomName("description"));
		vlan.setBeginDate(startDate);
		vlan.setTenants(87);
		vlan.setIdc(90);

		vlan.setRemark(RandomData.randomName("remark"));
		vlan.setSegment(RandomData.randomName("segment"));
		vlan.setNetMask(RandomData.randomName("netMask"));

		return vlan;
	}

	public static Cs2 randomCs2() {
		Cs2 cs2 = new Cs2();
		cs2.setDiskSize(1024);
		cs2.setEs3Spec(0);
		cs2.setFimas(0);
		cs2.setIpaddress(0);
		cs2.setRemark(RandomData.randomName("remark"));
		cs2.setTag(0);
		cs2.setTenants(116);
		return cs2;
	}

	public static As2 randomAs2() {
		As2 as2 = new As2();
		as2.setDiskSize(1024);
		as2.setEs3Spec(0);
		as2.setIpaddress(0);
		as2.setNetAppController(0);
		as2.setRemark(RandomData.randomName("remark"));
		as2.setTag(0);
		as2.setTenants(116);
		as2.setVolumePath(RandomData.randomName("volumePath"));
		as2.setVolumeType(0);
		return as2;
	}

	public static Eip randomEip() {
		Eip eip = new Eip();
		eip.setBandwidth(1024);
		eip.setEipSpec(0);
		eip.setEipStatus(0);
		eip.setIpaddress(0);
		eip.setRemark(RandomData.randomName("remark"));
		eip.setTag(0);
		eip.setTenants(116);
		return eip;
	}

	public static Elb randomElb() {
		Elb elb = new Elb();
		elb.setIpaddress(0);
		elb.setIsSession(true);
		elb.setRemark(RandomData.randomName("remark"));
		elb.setTag(0);
		elb.setTenants(116);
		return elb;
	}

	public static Dns randomDns() {
		Dns dns = new Dns();
		dns.setCnameDomain(RandomData.randomName("cnameDomain"));
		dns.setDomainName(RandomData.randomName("domainName"));
		dns.setDomainType(0);
		dns.setRemark(RandomData.randomName("remark"));
		dns.setTag(0);
		dns.setTenants(116);
		return dns;
	}

	public static Esg randomEsg() {
		Esg esg = new Esg();
		esg.setAclNumber(0);
		esg.setIsPublic(true);
		esg.setRemark(RandomData.randomName("remark"));
		esg.setTag(0);
		esg.setTenants(116);
		return esg;
	}

	public static Vpn randomVpn() {
		Vpn vpn = new Vpn();
		vpn.setRemark(RandomData.randomName("remark"));
		vpn.setTag(0);
		vpn.setTenants(116);
		vpn.setVpnName(RandomData.randomName("vpnName"));
		vpn.setVpnPassword(RandomData.randomName("vpnPassword"));
		return vpn;
	}

	public static GroupPolicy randomGroupPolicy() {
		GroupPolicy groupPolicy = new GroupPolicy();
		groupPolicy.setRemark(RandomData.randomName("remark"));
		groupPolicy.setTenants(116);
		return groupPolicy;
	}

	public static Ecs randomEcs() {
		return null;
	}

	public static InfrastructureDTO randomInfrastructureDTO() {

		InfrastructureDTO infrastructureDTO = new InfrastructureDTO();

		infrastructureDTO.setId(0);
		infrastructureDTO.setBeginDate(startDate);
		infrastructureDTO.setCode(RandomData.randomName("code"));
		infrastructureDTO.setDescription(RandomData.randomName("desc"));
		infrastructureDTO.setDeviceSpec(226);
		infrastructureDTO.setDeviceStatus(0);// 参数定义未知
		infrastructureDTO.setRack(218);
		infrastructureDTO.setIdc(90);
		infrastructureDTO.setSite(RandomData.randomName("site"));

		infrastructureDTO.setGdzcSn(RandomData.randomName("gdzcSn"));
		infrastructureDTO.setRemark(RandomData.randomName("remark"));
		infrastructureDTO.setSn(RandomData.randomName("sn"));
		infrastructureDTO.setIpAddress(94);

		return infrastructureDTO;
	}

	public static InfrastructurePortDTO randomInfrastructurePortDTO() {

		InfrastructurePortDTO infrastructurePortDTO = new InfrastructurePortDTO();

		infrastructurePortDTO.setId(0);
		infrastructurePortDTO.setBeginDate(startDate);
		infrastructurePortDTO.setCode(RandomData.randomName("code"));
		infrastructurePortDTO.setDescription(RandomData.randomName("desc"));
		infrastructurePortDTO.setSite(RandomData.randomName("site"));

		infrastructurePortDTO.setRemark(RandomData.randomName("remark"));
		infrastructurePortDTO.setIpAddress(94);
		//infrastructurePortDTO.setConnectedTo(0);//连接的 switch 端口 ID
		infrastructurePortDTO.setMacAddress(RandomData.randomName("mac"));

		return infrastructurePortDTO;
	}

}
