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

	private Integer cpuNumber;
	private Integer memory;
	private Integer osType;
	private Integer diskSize;
	private String imageName;
	private String remark;
	private Set<EcsSpecHistory> ecsSpecHistories = new HashSet<EcsSpecHistory>(0);

	public EcsSpec() {
	}

	@Column(name = "cpu_number")
	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	@Column(name = "memory")
	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	@Column(name = "os_type")
	public Integer getOsType() {
		return osType;
	}

	public void setOsType(Integer osType) {
		this.osType = osType;
	}

	@Column(name = "disk_size")
	public Integer getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}

	@Column(name = "image_name", length = 200)
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ecsSpec")
	public Set<EcsSpecHistory> getEcsSpecHistories() {
		return ecsSpecHistories;
	}

	public void setEcsSpecHistories(Set<EcsSpecHistory> ecsSpecHistories) {
		this.ecsSpecHistories = ecsSpecHistories;
	}

}
