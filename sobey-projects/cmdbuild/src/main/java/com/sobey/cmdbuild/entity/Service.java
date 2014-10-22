package com.sobey.cmdbuild.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.sobey.cmdbuild.entity.basic.ServiceBasic;

@Entity
@Table(name = "service", schema = "public")
public class Service extends ServiceBasic {

	public Service() {
	}

}
