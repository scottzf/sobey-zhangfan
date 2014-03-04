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
 * volumeSize		卷大小(MB)
 * clientIPaddress		允许挂载的客户端IP
 * netAppIPaddress		netApp Ip
 * beforeClientIPaddress	修改前的客户端IP集合
 * afterClientIPaddress	修改后的客户端IP集合
 * </pre>
 * 
 * @author Administrator
 * 
 */
@XmlRootElement(name = "NetAppParameter")
@XmlType(name = "NetAppParameter", namespace = WsConstants.NS)
public class NetAppParameter {

	/**
	 * 卷名
	 */
	private String volumeName;

	/**
	 * 卷大小(MB)
	 */
	private String volumeSize;

	/**
	 * 允许挂载的客户端IP
	 */
	private String clientIPaddress;

	/**
	 * netapp IP
	 */
	private String netAppIPaddress;

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

	public String getVolumeSize() {
		return volumeSize;
	}

	public void setVolumeSize(String volumeSize) {
		this.volumeSize = volumeSize;
	}

	public String getClientIPaddress() {
		return clientIPaddress;
	}

	public void setClientIPaddress(String clientIPaddress) {
		this.clientIPaddress = clientIPaddress;
	}

	public String getNetAppIPaddress() {
		return netAppIPaddress;
	}

	public void setNetAppIPaddress(String netAppIPaddress) {
		this.netAppIPaddress = netAppIPaddress;
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
