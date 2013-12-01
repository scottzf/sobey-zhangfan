package com.sobey.switches.webservice.pojo;

/**
 * 源ip和目的ip
 */
public class SourceAndDest {

	private String source;
	private String sourceSubnetMask;
	private String destination;
	private String destinationSubnetMask;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSourceSubnetMask() {
		return sourceSubnetMask;
	}

	public void setSourceSubnetMask(String sourceSubnetMask) {
		this.sourceSubnetMask = sourceSubnetMask;
	}

	public String getDestinationSubnetMask() {
		return destinationSubnetMask;
	}

	public void setDestinationSubnetMask(String destinationSubnetMask) {
		this.destinationSubnetMask = destinationSubnetMask;
	}

}
