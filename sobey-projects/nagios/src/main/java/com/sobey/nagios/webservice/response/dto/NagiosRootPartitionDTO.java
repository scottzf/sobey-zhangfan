package com.sobey.nagios.webservice.response.dto;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.nagios.constans.WsConstants;
import com.sobey.nagios.entity.NagiosRootPartition;

@XmlRootElement(name = "NagiosRootPartitionDTO")
@XmlType(name = "NagiosRootPartitionDTO", namespace = WsConstants.NS)
public class NagiosRootPartitionDTO {

	private ArrayList<NagiosRootPartition> nagiosRootPartitions;

	public ArrayList<NagiosRootPartition> getNagiosRootPartitions() {
		return nagiosRootPartitions;
	}

	public void setNagiosRootPartitions(ArrayList<NagiosRootPartition> nagiosRootPartitions) {
		this.nagiosRootPartitions = nagiosRootPartitions;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
