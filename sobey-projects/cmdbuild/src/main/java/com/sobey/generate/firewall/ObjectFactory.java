package com.sobey.generate.firewall;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * com.sobey.generate.firewall package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content.
 * The Java representation of XML content can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory methods for each of these are provided in
 * this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _EIPPolicyParameter_QNAME = new QName("http://firewall.generate.sobey.com",
			"EIPPolicyParameter");
	private final static QName _CreateEIPByFirewallResponse_QNAME = new QName("http://firewall.generate.sobey.com",
			"createEIPByFirewallResponse");
	private final static QName _ChangeVPNUserAccesssAddressByFirewallResponse_QNAME = new QName(
			"http://firewall.generate.sobey.com", "changeVPNUserAccesssAddressByFirewallResponse");
	private final static QName _CreateVPNUserByFirewall_QNAME = new QName("http://firewall.generate.sobey.com",
			"createVPNUserByFirewall");
	private final static QName _EIPParameter_QNAME = new QName("http://firewall.generate.sobey.com", "EIPParameter");
	private final static QName _DeleteVPNUserByFirewall_QNAME = new QName("http://firewall.generate.sobey.com",
			"deleteVPNUserByFirewall");
	private final static QName _CreateEIPByFirewall_QNAME = new QName("http://firewall.generate.sobey.com",
			"createEIPByFirewall");
	private final static QName _ChangeVPNUserAccesssAddressByFirewall_QNAME = new QName(
			"http://firewall.generate.sobey.com", "changeVPNUserAccesssAddressByFirewall");
	private final static QName _DeleteEIPByFirewallResponse_QNAME = new QName("http://firewall.generate.sobey.com",
			"deleteEIPByFirewallResponse");
	private final static QName _VPNUserParameter_QNAME = new QName("http://firewall.generate.sobey.com",
			"VPNUserParameter");
	private final static QName _DeleteEIPByFirewall_QNAME = new QName("http://firewall.generate.sobey.com",
			"deleteEIPByFirewall");
	private final static QName _DeleteVPNUserByFirewallResponse_QNAME = new QName("http://firewall.generate.sobey.com",
			"deleteVPNUserByFirewallResponse");
	private final static QName _CreateVPNUserByFirewallResponse_QNAME = new QName("http://firewall.generate.sobey.com",
			"createVPNUserByFirewallResponse");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
	 * com.sobey.generate.firewall
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link CreateEIPByFirewallResponse }
	 * 
	 */
	public CreateEIPByFirewallResponse createCreateEIPByFirewallResponse() {
		return new CreateEIPByFirewallResponse();
	}

	/**
	 * Create an instance of {@link EIPPolicyParameter }
	 * 
	 */
	public EIPPolicyParameter createEIPPolicyParameter() {
		return new EIPPolicyParameter();
	}

	/**
	 * Create an instance of {@link DeleteVPNUserByFirewall }
	 * 
	 */
	public DeleteVPNUserByFirewall createDeleteVPNUserByFirewall() {
		return new DeleteVPNUserByFirewall();
	}

	/**
	 * Create an instance of {@link EIPParameter }
	 * 
	 */
	public EIPParameter createEIPParameter() {
		return new EIPParameter();
	}

	/**
	 * Create an instance of {@link CreateVPNUserByFirewall }
	 * 
	 */
	public CreateVPNUserByFirewall createCreateVPNUserByFirewall() {
		return new CreateVPNUserByFirewall();
	}

	/**
	 * Create an instance of {@link ChangeVPNUserAccesssAddressByFirewallResponse }
	 * 
	 */
	public ChangeVPNUserAccesssAddressByFirewallResponse createChangeVPNUserAccesssAddressByFirewallResponse() {
		return new ChangeVPNUserAccesssAddressByFirewallResponse();
	}

	/**
	 * Create an instance of {@link VPNUserParameter }
	 * 
	 */
	public VPNUserParameter createVPNUserParameter() {
		return new VPNUserParameter();
	}

	/**
	 * Create an instance of {@link DeleteEIPByFirewallResponse }
	 * 
	 */
	public DeleteEIPByFirewallResponse createDeleteEIPByFirewallResponse() {
		return new DeleteEIPByFirewallResponse();
	}

	/**
	 * Create an instance of {@link ChangeVPNUserAccesssAddressByFirewall }
	 * 
	 */
	public ChangeVPNUserAccesssAddressByFirewall createChangeVPNUserAccesssAddressByFirewall() {
		return new ChangeVPNUserAccesssAddressByFirewall();
	}

	/**
	 * Create an instance of {@link CreateEIPByFirewall }
	 * 
	 */
	public CreateEIPByFirewall createCreateEIPByFirewall() {
		return new CreateEIPByFirewall();
	}

	/**
	 * Create an instance of {@link CreateVPNUserByFirewallResponse }
	 * 
	 */
	public CreateVPNUserByFirewallResponse createCreateVPNUserByFirewallResponse() {
		return new CreateVPNUserByFirewallResponse();
	}

	/**
	 * Create an instance of {@link DeleteVPNUserByFirewallResponse }
	 * 
	 */
	public DeleteVPNUserByFirewallResponse createDeleteVPNUserByFirewallResponse() {
		return new DeleteVPNUserByFirewallResponse();
	}

	/**
	 * Create an instance of {@link DeleteEIPByFirewall }
	 * 
	 */
	public DeleteEIPByFirewall createDeleteEIPByFirewall() {
		return new DeleteEIPByFirewall();
	}

	/**
	 * Create an instance of {@link WSResult }
	 * 
	 */
	public WSResult createWSResult() {
		return new WSResult();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link EIPPolicyParameter }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "EIPPolicyParameter")
	public JAXBElement<EIPPolicyParameter> createEIPPolicyParameter(EIPPolicyParameter value) {
		return new JAXBElement<EIPPolicyParameter>(_EIPPolicyParameter_QNAME, EIPPolicyParameter.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CreateEIPByFirewallResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "createEIPByFirewallResponse")
	public JAXBElement<CreateEIPByFirewallResponse> createCreateEIPByFirewallResponse(CreateEIPByFirewallResponse value) {
		return new JAXBElement<CreateEIPByFirewallResponse>(_CreateEIPByFirewallResponse_QNAME,
				CreateEIPByFirewallResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ChangeVPNUserAccesssAddressByFirewallResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "changeVPNUserAccesssAddressByFirewallResponse")
	public JAXBElement<ChangeVPNUserAccesssAddressByFirewallResponse> createChangeVPNUserAccesssAddressByFirewallResponse(
			ChangeVPNUserAccesssAddressByFirewallResponse value) {
		return new JAXBElement<ChangeVPNUserAccesssAddressByFirewallResponse>(
				_ChangeVPNUserAccesssAddressByFirewallResponse_QNAME,
				ChangeVPNUserAccesssAddressByFirewallResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CreateVPNUserByFirewall }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "createVPNUserByFirewall")
	public JAXBElement<CreateVPNUserByFirewall> createCreateVPNUserByFirewall(CreateVPNUserByFirewall value) {
		return new JAXBElement<CreateVPNUserByFirewall>(_CreateVPNUserByFirewall_QNAME, CreateVPNUserByFirewall.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link EIPParameter }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "EIPParameter")
	public JAXBElement<EIPParameter> createEIPParameter(EIPParameter value) {
		return new JAXBElement<EIPParameter>(_EIPParameter_QNAME, EIPParameter.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DeleteVPNUserByFirewall }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "deleteVPNUserByFirewall")
	public JAXBElement<DeleteVPNUserByFirewall> createDeleteVPNUserByFirewall(DeleteVPNUserByFirewall value) {
		return new JAXBElement<DeleteVPNUserByFirewall>(_DeleteVPNUserByFirewall_QNAME, DeleteVPNUserByFirewall.class,
				null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CreateEIPByFirewall }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "createEIPByFirewall")
	public JAXBElement<CreateEIPByFirewall> createCreateEIPByFirewall(CreateEIPByFirewall value) {
		return new JAXBElement<CreateEIPByFirewall>(_CreateEIPByFirewall_QNAME, CreateEIPByFirewall.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ChangeVPNUserAccesssAddressByFirewall }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "changeVPNUserAccesssAddressByFirewall")
	public JAXBElement<ChangeVPNUserAccesssAddressByFirewall> createChangeVPNUserAccesssAddressByFirewall(
			ChangeVPNUserAccesssAddressByFirewall value) {
		return new JAXBElement<ChangeVPNUserAccesssAddressByFirewall>(_ChangeVPNUserAccesssAddressByFirewall_QNAME,
				ChangeVPNUserAccesssAddressByFirewall.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEIPByFirewallResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "deleteEIPByFirewallResponse")
	public JAXBElement<DeleteEIPByFirewallResponse> createDeleteEIPByFirewallResponse(DeleteEIPByFirewallResponse value) {
		return new JAXBElement<DeleteEIPByFirewallResponse>(_DeleteEIPByFirewallResponse_QNAME,
				DeleteEIPByFirewallResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link VPNUserParameter }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "VPNUserParameter")
	public JAXBElement<VPNUserParameter> createVPNUserParameter(VPNUserParameter value) {
		return new JAXBElement<VPNUserParameter>(_VPNUserParameter_QNAME, VPNUserParameter.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEIPByFirewall }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "deleteEIPByFirewall")
	public JAXBElement<DeleteEIPByFirewall> createDeleteEIPByFirewall(DeleteEIPByFirewall value) {
		return new JAXBElement<DeleteEIPByFirewall>(_DeleteEIPByFirewall_QNAME, DeleteEIPByFirewall.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DeleteVPNUserByFirewallResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "deleteVPNUserByFirewallResponse")
	public JAXBElement<DeleteVPNUserByFirewallResponse> createDeleteVPNUserByFirewallResponse(
			DeleteVPNUserByFirewallResponse value) {
		return new JAXBElement<DeleteVPNUserByFirewallResponse>(_DeleteVPNUserByFirewallResponse_QNAME,
				DeleteVPNUserByFirewallResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CreateVPNUserByFirewallResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://firewall.generate.sobey.com", name = "createVPNUserByFirewallResponse")
	public JAXBElement<CreateVPNUserByFirewallResponse> createCreateVPNUserByFirewallResponse(
			CreateVPNUserByFirewallResponse value) {
		return new JAXBElement<CreateVPNUserByFirewallResponse>(_CreateVPNUserByFirewallResponse_QNAME,
				CreateVPNUserByFirewallResponse.class, null, value);
	}

}
