package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.PortBasic;

/**
 * SwitchPort generated by hbm2java
 */
@Entity
@Table(name = "switch_port", schema = "public")
public class SwitchPort extends PortBasic {

	private Integer switches;
	private Set<SwitchPortHistory> switchPortHistories = new HashSet<SwitchPortHistory>(0);

	public SwitchPort() {
	}

	@Column(name = "switches")
	public Integer getSwitches() {
		return switches;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "switchPort")
	public Set<SwitchPortHistory> getSwitchPortHistories() {
		return this.switchPortHistories;
	}

	public void setSwitches(Integer switches) {
		this.switches = switches;
	}

	public void setSwitchPortHistories(Set<SwitchPortHistory> switchPortHistories) {
		this.switchPortHistories = switchPortHistories;
	}

}
