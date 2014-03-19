package com.sobey.storage.webservice.response.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.storage.constans.WsConstants;

/**
 * netapp上,执行脚本所需参数的对象:
 * 
 * <pre>
 * volumeName		卷名
 * beforeClientIPaddress	修改前的客户端IP集合
 * afterClientIPaddress	修改后的客户端IP集合
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "RemountEs3Parameter")
@XmlType(name = "RemountEs3Parameter", namespace = WsConstants.NS)
public class RemountEs3Parameter {

	/**
	 * 卷名
	 */
	private String volumeName;

	/**
	 * 修改前的客户端IP
	 */
	private List<String> beforeClientIPaddress;

	/**
	 * 修改后的客户端IP
	 */
	private List<String> afterClientIPaddress;

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public List<String> getBeforeClientIPaddress() {
		return beforeClientIPaddress;
	}

	public void setBeforeClientIPaddress(List<String> beforeClientIPaddress) {
		this.beforeClientIPaddress = beforeClientIPaddress;
	}

	public List<String> getAfterClientIPaddress() {
		return afterClientIPaddress;
	}

	public void setAfterClientIPaddress(List<String> afterClientIPaddress) {
		this.afterClientIPaddress = afterClientIPaddress;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
