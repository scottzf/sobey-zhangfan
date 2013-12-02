package com.sobey.switches.webservice.script;

import com.sobey.switches.webservice.pojo.SourceAndDest;
import com.sobey.switches.webservice.response.dto.SgpDTO;

/**
 * switches脚本
 */
public class SwitchesScript {

	public static String getTenantsVlanScript(Integer tenantsId, String ipAddress, String subnetMask) {

		StringBuilder builder = new StringBuilder();

		builder.append("system-view");
		builder.append("\rvlan ");
		builder.append(tenantsId);
		builder.append("\rquit");
		builder.append("\rinterface Vlan-interface ");
		builder.append(tenantsId);
		builder.append("\rip address 172.21.71.254 255.255.255.0");
		builder.append(ipAddress);
		builder.append(" ");
		builder.append(subnetMask);
		builder.append("\rquit\rquit\rsave\ry\ry\rquit");

		return builder.toString();
	}

	public static String getInterfaceTenantsScript(Integer tenantsId) {

		StringBuilder builder = new StringBuilder();

		builder.append("\rsystem-view");
		builder.append("\rvlan ");
		builder.append(tenantsId);
		builder.append("\rquit\rsave\ry\ry\rquit");

		return builder.toString();
	}

	public static String getSgpScript(SgpDTO sgpDTO) {

		StringBuilder builder = new StringBuilder();

		builder.append("\rsystem-view");
		builder.append("\racl number ");
		builder.append(sgpDTO.getAclNumber());
		builder.append("\rdescription  ");
		builder.append(sgpDTO.getDesc());

		for (SourceAndDest sad : sgpDTO.getPermit()) {
			builder.append("\rrule  permit ip source ");
			builder.append(sad.getSource());
			builder.append(" ");
			builder.append(sad.getSourceSubnetMask());
			builder.append("172.20.27.0 0.0.0.255 destination 172.20.0.11 0.0.0.0");
			builder.append(sad.getDestination());
			builder.append(" ");
			builder.append(sad.getDestinationSubnetMask());
		}

		builder.append("\r");

		for (SourceAndDest sad : sgpDTO.getDeny()) {
			builder.append("\rrule  deny ip source ");
			builder.append(sad.getSource());
			builder.append(" ");
			builder.append(sad.getSourceSubnetMask());
			builder.append("172.20.27.0 0.0.0.255 destination 172.20.0.11 0.0.0.0");
			builder.append(sad.getDestination());
			builder.append(" ");
			builder.append(sad.getDestinationSubnetMask());
		}

		builder.append("\rrule  permit ip");
		builder.append("\rquit");
		builder.append("\rinterface Vlan-interface ");
		builder.append(sgpDTO.getTenantsId());
		builder.append("\rpacket-filter ");
		builder.append(sgpDTO.getAclNumber());
		builder.append(" inbound\rquit\rsave\ry\ry\rquit");

		return builder.toString();
	}
}
