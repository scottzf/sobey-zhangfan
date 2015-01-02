package com.sobey.instance.webservice.response.dto;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.instance.constans.WsConstants;

/**
 * 
 * 获得Host和VM的关联关系 webservice不能直接传递对象,故用一个对象对其包装.
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "RelationVMParameter")
@XmlType(name = "RelationVMParameter", namespace = WsConstants.NS)
public class RelationVMParameter {

	/**
	 * 数据中心
	 */
	private String datacenter;

	/**
	 * Host和VM的关系:key为VM名称,value为Host名称.
	 */
	private HashMap<String, String> relationMaps;

	public String getDatacenter() {
		return datacenter;
	}

	public HashMap<String, String> getRelationMaps() {
		return relationMaps;
	}

	public void setDatacenter(String datacenter) {
		this.datacenter = datacenter;
	}

	public void setRelationMaps(HashMap<String, String> relationMaps) {
		this.relationMaps = relationMaps;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
