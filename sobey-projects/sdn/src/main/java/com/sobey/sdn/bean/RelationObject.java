package com.sobey.sdn.bean;

import java.util.ArrayList;
import java.util.List;

public class RelationObject {

	private String ip;
	
	private List<NICRelation> nicRelations = new ArrayList<NICRelation>();

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<NICRelation> getNicRelations() {
		return nicRelations;
	}

	public void setNicRelations(List<NICRelation> nicRelations) {
		this.nicRelations = nicRelations;
	}
	
}
