package com.sobey.api.webservice.response.result;

import javax.xml.bind.annotation.XmlType;

import com.sobey.api.constans.WsConstants;

/**
 * 某个对象返回的通用IdResult.
 * 
 * @author Administrator
 */
@XmlType(name = "IdResult", namespace = WsConstants.NS)
public class IdResult extends WSResult {

	private Integer id;

	public IdResult() {
		this.id = Integer.valueOf(WSResult.SUCESS);
	}

	public IdResult(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
