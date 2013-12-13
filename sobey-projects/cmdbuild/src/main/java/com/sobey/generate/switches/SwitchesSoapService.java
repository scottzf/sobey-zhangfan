package com.sobey.generate.switches;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.8 2013-12-13T13:56:36.152+08:00 Generated source version: 2.7.8
 * 
 */
@WebService(targetNamespace = "http://switches.generate.sobey.com", name = "SwitchesSoapService")
@XmlSeeAlso({ ObjectFactory.class })
public interface SwitchesSoapService {

	@WebResult(name = "return", targetNamespace = "")
	@RequestWrapper(localName = "deleteESGBySwtich", targetNamespace = "http://switches.generate.sobey.com", className = "com.sobey.generate.switches.DeleteESGBySwtich")
	@WebMethod
	@ResponseWrapper(localName = "deleteESGBySwtichResponse", targetNamespace = "http://switches.generate.sobey.com", className = "com.sobey.generate.switches.DeleteESGBySwtichResponse")
	public com.sobey.generate.switches.WSResult deleteESGBySwtich(
			@WebParam(name = "aclNumber", targetNamespace = "") java.lang.Integer aclNumber);

	@WebResult(name = "return", targetNamespace = "")
	@RequestWrapper(localName = "createVlanBySwtich", targetNamespace = "http://switches.generate.sobey.com", className = "com.sobey.generate.switches.CreateVlanBySwtich")
	@WebMethod
	@ResponseWrapper(localName = "createVlanBySwtichResponse", targetNamespace = "http://switches.generate.sobey.com", className = "com.sobey.generate.switches.CreateVlanBySwtichResponse")
	public com.sobey.generate.switches.WSResult createVlanBySwtich(
			@WebParam(name = "vlanParameter", targetNamespace = "") com.sobey.generate.switches.VlanParameter vlanParameter);

	@WebResult(name = "return", targetNamespace = "")
	@RequestWrapper(localName = "deleteVlanBySwtich", targetNamespace = "http://switches.generate.sobey.com", className = "com.sobey.generate.switches.DeleteVlanBySwtich")
	@WebMethod
	@ResponseWrapper(localName = "deleteVlanBySwtichResponse", targetNamespace = "http://switches.generate.sobey.com", className = "com.sobey.generate.switches.DeleteVlanBySwtichResponse")
	public com.sobey.generate.switches.WSResult deleteVlanBySwtich(
			@WebParam(name = "vlanId", targetNamespace = "") java.lang.Integer vlanId);

	@WebResult(name = "return", targetNamespace = "")
	@RequestWrapper(localName = "createESGBySwtich", targetNamespace = "http://switches.generate.sobey.com", className = "com.sobey.generate.switches.CreateESGBySwtich")
	@WebMethod
	@ResponseWrapper(localName = "createESGBySwtichResponse", targetNamespace = "http://switches.generate.sobey.com", className = "com.sobey.generate.switches.CreateESGBySwtichResponse")
	public com.sobey.generate.switches.WSResult createESGBySwtich(
			@WebParam(name = "esgParameter", targetNamespace = "") com.sobey.generate.switches.ESGParameter esgParameter);
}
