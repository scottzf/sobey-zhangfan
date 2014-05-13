package com.sobey.nagios.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 数据库中返回的Stream对象.
 * 
 * @author Administrator
 * 
 */
public class NagiosStreamResult {

	/**
	 * 监控IP
	 */
	private String hostName;

	/**
	 * 监控项
	 */
	private String serviceName;

	/**
	 * 开始时间
	 */
	private Date lastCheck;

	/**
	 * 结束时间
	 */
	private Date lastStateChange;

	/**
	 * 尝试次数
	 */
	private String currentCheckAttempt;

	/**
	 * 最大尝试次数
	 */
	private String maxCheckAttempts;

	/**
	 * 监控结果
	 */
	private String output;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Date getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(Date lastCheck) {
		this.lastCheck = lastCheck;
	}

	public Date getLastStateChange() {
		return lastStateChange;
	}

	public void setLastStateChange(Date lastStateChange) {
		this.lastStateChange = lastStateChange;
	}

	public String getCurrentCheckAttempt() {
		return currentCheckAttempt;
	}

	public void setCurrentCheckAttempt(String currentCheckAttempt) {
		this.currentCheckAttempt = currentCheckAttempt;
	}

	public String getMaxCheckAttempts() {
		return maxCheckAttempts;
	}

	public void setMaxCheckAttempts(String maxCheckAttempts) {
		this.maxCheckAttempts = maxCheckAttempts;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
