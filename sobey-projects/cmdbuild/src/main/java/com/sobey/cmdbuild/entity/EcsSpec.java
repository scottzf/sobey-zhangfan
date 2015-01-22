package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.BasicEntity;

/**
 * EcsSpec generated by hbm2java
 */
@Entity
@Table(name = "ecs_spec", schema = "public")
public class EcsSpec extends BasicEntity {

	private Set<EcsSpecHistory> ecsSpecHistories = new HashSet<EcsSpecHistory>(0);
	private Integer ecsType;
	private Integer idc;
	private String imageName;
	private Integer osType;
	private String remark;

	public EcsSpec() {
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ecsSpec")
	public Set<EcsSpecHistory> getEcsSpecHistories() {
		return ecsSpecHistories;
	}

	@Column(name = "ecs_type")
	public Integer getEcsType() {
		return ecsType;
	}

	@Column(name = "idc")
	public Integer getIdc() {
		return idc;
	}

	@Column(name = "image_name", length = 200)
	public String getImageName() {
		return imageName;
	}

	@Column(name = "os_type")
	public Integer getOsType() {
		return osType;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setEcsSpecHistories(Set<EcsSpecHistory> ecsSpecHistories) {
		this.ecsSpecHistories = ecsSpecHistories;
	}

	public void setEcsType(Integer ecsType) {
		this.ecsType = ecsType;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
