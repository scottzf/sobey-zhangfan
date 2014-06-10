package com.sobey.cmdbuild.webservice.response.result;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

/**
 * 多条件对象.
 * 
 * @author Administrator
 *
 */
@XmlRootElement(name = "SearchParams")
@XmlType(name = "SearchParams", namespace = WsConstants.NS)
public class SearchParams {

	private HashMap<String, Object> paramsMap;

	public HashMap<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(HashMap<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
