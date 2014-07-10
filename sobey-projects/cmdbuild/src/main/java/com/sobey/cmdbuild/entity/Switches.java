package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.DeviceBasic;

/**
 * Switches generated by hbm2java
 */
@Entity
@Table(name = "switches", schema = "public")
public class Switches extends DeviceBasic {

	private Set<SwitchesHistory> switchesHistories = new HashSet<SwitchesHistory>(0);

	public Switches() {
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "switches")
	public Set<SwitchesHistory> getSwitchesHistories() {
		return switchesHistories;
	}

	public void setSwitchesHistories(Set<SwitchesHistory> switchesHistories) {
		this.switchesHistories = switchesHistories;
	}

}
