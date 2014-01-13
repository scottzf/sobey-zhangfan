package com.sobey.cmdbuild.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "\"Map_ecs_esg\"", schema = "public")
public class MapEcsEsg {

	protected Integer id;
	protected String idClass1;
	protected Integer idObj1;
	protected String idClass2;
	protected Integer idObj2;
	protected Character status;
	protected String user;
	protected Date beginDate;
	protected Date endDate;

	@Id
	@Column(name = "\"Id\"", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "\"IdClass1\"", nullable = false)
	public String getIdClass1() {
		return idClass1;
	}

	public void setIdClass1(String idClass1) {
		this.idClass1 = idClass1;
	}

	@Column(name = "\"IdObj1\"", nullable = false)
	public Integer getIdObj1() {
		return idObj1;
	}

	public void setIdObj1(Integer idObj1) {
		this.idObj1 = idObj1;
	}

	@Column(name = "\"IdClass2\"", nullable = false)
	public String getIdClass2() {
		return idClass2;
	}

	public void setIdClass2(String idClass2) {
		this.idClass2 = idClass2;
	}

	@Column(name = "\"IdObj2\"", nullable = false)
	public Integer getIdObj2() {
		return idObj2;
	}

	public void setIdObj2(Integer idObj2) {
		this.idObj2 = idObj2;
	}

	@Column(name = "\"Status\"", length = 1)
	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Column(name = "\"User\"", length = 40)
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"BeginDate\"", nullable = false, length = 29)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
