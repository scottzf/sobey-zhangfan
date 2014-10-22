package com.sobey.api.entity;

import java.util.List;

public class EipEntity {

	private String identifier;
	private String eipName;
	private String remark;
	private String bandwidth;
	private String isp;
	private List<EcsEntity> ecsEntities;
	private List<ElbEntity> elbEntities;

	public EipEntity(String identifier, String eipName, String remark, String bandwidth, String isp,
			List<EcsEntity> ecsEntities, List<ElbEntity> elbEntities) {
		super();
		this.identifier = identifier;
		this.eipName = eipName;
		this.remark = remark;
		this.bandwidth = bandwidth;
		this.isp = isp;
		this.ecsEntities = ecsEntities;
		this.elbEntities = elbEntities;
	}

	public EipEntity(String identifier, String eipName, String remark, String bandwidth, String isp) {
		super();
		this.identifier = identifier;
		this.eipName = eipName;
		this.remark = remark;
		this.bandwidth = bandwidth;
		this.isp = isp;
	}

	public List<EcsEntity> getEcsEntities() {
		return ecsEntities;
	}

	public void setEcsEntities(List<EcsEntity> ecsEntities) {
		this.ecsEntities = ecsEntities;
	}

	public List<ElbEntity> getElbEntities() {
		return elbEntities;
	}

	public void setElbEntities(List<ElbEntity> elbEntities) {
		this.elbEntities = elbEntities;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getEipName() {
		return eipName;
	}

	public void setEipName(String eipName) {
		this.eipName = eipName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

}
