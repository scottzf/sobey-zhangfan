package com.sobey.zabbix.entity;

/**
 * 定义 zabbix api Item
 * 
 * @author Administrator
 *
 */
public class ZItem {

	/** The Item ID value */
	private String itemId;

	/** The Type value */
	private String type;

	/** The Item custom port value */
	private String port;

	/** The Host ID value */
	private String hostId;

	/** The Item name value */
	private String name;

	/** The Item key value */
	private String key;

	/** The Check Stringerval value */
	private String delay;

	/** The How long to keep item history (days) value */
	private String history;

	/** The How long to keep item trends (days) value */
	private String trends;

	/** The Last value value */
	private String lastValue;

	/** The Last check value */
	private String lastclock;

	/** The Previous value value */
	private String prevValue;

	/** The Item status value */
	private String status;

	/** The Value type value */
	private String valueType;

	/** The value */
	private String trapperHosts;

	/** The Value units value */
	private String units;

	/** The Value multiplier value */
	private String multiplier;

	/** The Store values as delta value */
	private String delta;

	/** The value */
	private String prevorgValue;

	/** The value */
	private String formula;

	/** The Item check error value */
	private String error;

	/** The Last log size value */
	private String lastlogSize;

	/** The Log time format value */
	private String logTimefmt;

	/** The Parent item ID value */
	private String templateId;

	/** The Value map ID value */
	private String valueMapId;

	/** The Flexible delay value */
	private String delayflex;

	/** The value */
	private String params;

	/** The value */
	private String dataType;

	/** The value */
	private String authType;

	/** The value */
	private String userName;

	/** The value */
	private String password;

	/** The value */
	private String publicKey;

	/** The value */
	private String privateKey;

	/** The Micro time value */
	private String mTime;

	/** The Host Stringerface ID value */
	private String StringerfaceId;

	/** The Item description value */
	private String description;

	/** The Host inventory field number, that will be updated with the value returned by the item value */
	private String inventoryLink;

	private String filter;

	private String lastns;

	private String flags;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHostId() {
		return hostId;
	}

	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getTrends() {
		return trends;
	}

	public void setTrends(String trends) {
		this.trends = trends;
	}

	public String getLastValue() {
		return lastValue;
	}

	public void setLastValue(String lastValue) {
		this.lastValue = lastValue;
	}

	public String getLastclock() {
		return lastclock;
	}

	public void setLastclock(String lastclock) {
		this.lastclock = lastclock;
	}

	public String getPrevValue() {
		return prevValue;
	}

	public void setPrevValue(String prevValue) {
		this.prevValue = prevValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getTrapperHosts() {
		return trapperHosts;
	}

	public void setTrapperHosts(String trapperHosts) {
		this.trapperHosts = trapperHosts;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(String multiplier) {
		this.multiplier = multiplier;
	}

	public String getDelta() {
		return delta;
	}

	public void setDelta(String delta) {
		this.delta = delta;
	}

	public String getPrevorgValue() {
		return prevorgValue;
	}

	public void setPrevorgValue(String prevorgValue) {
		this.prevorgValue = prevorgValue;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getLastlogSize() {
		return lastlogSize;
	}

	public void setLastlogSize(String lastlogSize) {
		this.lastlogSize = lastlogSize;
	}

	public String getLogTimefmt() {
		return logTimefmt;
	}

	public void setLogTimefmt(String logTimefmt) {
		this.logTimefmt = logTimefmt;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getValueMapId() {
		return valueMapId;
	}

	public void setValueMapId(String valueMapId) {
		this.valueMapId = valueMapId;
	}

	public String getDelayflex() {
		return delayflex;
	}

	public void setDelayflex(String delayflex) {
		this.delayflex = delayflex;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public String getStringerfaceId() {
		return StringerfaceId;
	}

	public void setStringerfaceId(String stringerfaceId) {
		StringerfaceId = stringerfaceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInventoryLink() {
		return inventoryLink;
	}

	public void setInventoryLink(String inventoryLink) {
		this.inventoryLink = inventoryLink;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getLastns() {
		return lastns;
	}

	public void setLastns(String lastns) {
		this.lastns = lastns;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

}
