package com.sobey.cmdbuild.webservice.response.dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.basic.PortBasicDTO;

@XmlRootElement(name = "NicPortDTO")
@XmlType(name = "NicPortDTO", namespace = WsConstants.NS)
public class NicPortDTO extends PortBasicDTO {

	private Integer nic;
	private NicDTO nicDTO;

	public Integer getNic() {
		return nic;
	}

	public void setNic(Integer nic) {
		this.nic = nic;
	}

	public NicDTO getNicDTO() {
		return nicDTO;
	}

	public void setNicDTO(NicDTO nicDTO) {
		this.nicDTO = nicDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}