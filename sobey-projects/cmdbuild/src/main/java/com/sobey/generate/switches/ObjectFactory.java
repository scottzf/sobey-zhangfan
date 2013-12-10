package com.sobey.generate.switches;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * com.sobey.generate.switches package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content.
 * The Java representation of XML content can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory methods for each of these are provided in
 * this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _CreateESGBySwtich_QNAME = new QName("http://switches.generate.sobey.com",
			"createESGBySwtich");
	private final static QName _DeleteESGBySwtichResponse_QNAME = new QName("http://switches.generate.sobey.com",
			"deleteESGBySwtichResponse");
	private final static QName _DeleteESGBySwtich_QNAME = new QName("http://switches.generate.sobey.com",
			"deleteESGBySwtich");
	private final static QName _RuleParameter_QNAME = new QName("http://switches.generate.sobey.com", "RuleParameter");
	private final static QName _ESGParameter_QNAME = new QName("http://switches.generate.sobey.com", "ESGParameter");
	private final static QName _DeleteVlanBySwtichResponse_QNAME = new QName("http://switches.generate.sobey.com",
			"deleteVlanBySwtichResponse");
	private final static QName _CreateVlanBySwtichResponse_QNAME = new QName("http://switches.generate.sobey.com",
			"createVlanBySwtichResponse");
	private final static QName _CreateVlanBySwtich_QNAME = new QName("http://switches.generate.sobey.com",
			"createVlanBySwtich");
	private final static QName _VlanParameter_QNAME = new QName("http://switches.generate.sobey.com", "VlanParameter");
	private final static QName _DeleteVlanBySwtich_QNAME = new QName("http://switches.generate.sobey.com",
			"deleteVlanBySwtich");
	private final static QName _CreateESGBySwtichResponse_QNAME = new QName("http://switches.generate.sobey.com",
			"createESGBySwtichResponse");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
	 * com.sobey.generate.switches
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link DeleteESGBySwtichResponse }
	 * 
	 */
	public DeleteESGBySwtichResponse createDeleteESGBySwtichResponse() {
		return new DeleteESGBySwtichResponse();
	}

	/**
	 * Create an instance of {@link CreateESGBySwtich }
	 * 
	 */
	public CreateESGBySwtich createCreateESGBySwtich() {
		return new CreateESGBySwtich();
	}

	/**
	 * Create an instance of {@link DeleteESGBySwtich }
	 * 
	 */
	public DeleteESGBySwtich createDeleteESGBySwtich() {
		return new DeleteESGBySwtich();
	}

	/**
	 * Create an instance of {@link RuleParameter }
	 * 
	 */
	public RuleParameter createRuleParameter() {
		return new RuleParameter();
	}

	/**
	 * Create an instance of {@link DeleteVlanBySwtichResponse }
	 * 
	 */
	public DeleteVlanBySwtichResponse createDeleteVlanBySwtichResponse() {
		return new DeleteVlanBySwtichResponse();
	}

	/**
	 * Create an instance of {@link ESGParameter }
	 * 
	 */
	public ESGParameter createESGParameter() {
		return new ESGParameter();
	}

	/**
	 * Create an instance of {@link CreateVlanBySwtichResponse }
	 * 
	 */
	public CreateVlanBySwtichResponse createCreateVlanBySwtichResponse() {
		return new CreateVlanBySwtichResponse();
	}

	/**
	 * Create an instance of {@link VlanParameter }
	 * 
	 */
	public VlanParameter createVlanParameter() {
		return new VlanParameter();
	}

	/**
	 * Create an instance of {@link CreateVlanBySwtich }
	 * 
	 */
	public CreateVlanBySwtich createCreateVlanBySwtich() {
		return new CreateVlanBySwtich();
	}

	/**
	 * Create an instance of {@link CreateESGBySwtichResponse }
	 * 
	 */
	public CreateESGBySwtichResponse createCreateESGBySwtichResponse() {
		return new CreateESGBySwtichResponse();
	}

	/**
	 * Create an instance of {@link DeleteVlanBySwtich }
	 * 
	 */
	public DeleteVlanBySwtich createDeleteVlanBySwtich() {
		return new DeleteVlanBySwtich();
	}

	/**
	 * Create an instance of {@link WSResult }
	 * 
	 */
	public WSResult createWSResult() {
		return new WSResult();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CreateESGBySwtich }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "createESGBySwtich")
	public JAXBElement<CreateESGBySwtich> createCreateESGBySwtich(CreateESGBySwtich value) {
		return new JAXBElement<CreateESGBySwtich>(_CreateESGBySwtich_QNAME, CreateESGBySwtich.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DeleteESGBySwtichResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "deleteESGBySwtichResponse")
	public JAXBElement<DeleteESGBySwtichResponse> createDeleteESGBySwtichResponse(DeleteESGBySwtichResponse value) {
		return new JAXBElement<DeleteESGBySwtichResponse>(_DeleteESGBySwtichResponse_QNAME,
				DeleteESGBySwtichResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DeleteESGBySwtich }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "deleteESGBySwtich")
	public JAXBElement<DeleteESGBySwtich> createDeleteESGBySwtich(DeleteESGBySwtich value) {
		return new JAXBElement<DeleteESGBySwtich>(_DeleteESGBySwtich_QNAME, DeleteESGBySwtich.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link RuleParameter }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "RuleParameter")
	public JAXBElement<RuleParameter> createRuleParameter(RuleParameter value) {
		return new JAXBElement<RuleParameter>(_RuleParameter_QNAME, RuleParameter.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link ESGParameter }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "ESGParameter")
	public JAXBElement<ESGParameter> createESGParameter(ESGParameter value) {
		return new JAXBElement<ESGParameter>(_ESGParameter_QNAME, ESGParameter.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DeleteVlanBySwtichResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "deleteVlanBySwtichResponse")
	public JAXBElement<DeleteVlanBySwtichResponse> createDeleteVlanBySwtichResponse(DeleteVlanBySwtichResponse value) {
		return new JAXBElement<DeleteVlanBySwtichResponse>(_DeleteVlanBySwtichResponse_QNAME,
				DeleteVlanBySwtichResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CreateVlanBySwtichResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "createVlanBySwtichResponse")
	public JAXBElement<CreateVlanBySwtichResponse> createCreateVlanBySwtichResponse(CreateVlanBySwtichResponse value) {
		return new JAXBElement<CreateVlanBySwtichResponse>(_CreateVlanBySwtichResponse_QNAME,
				CreateVlanBySwtichResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CreateVlanBySwtich }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "createVlanBySwtich")
	public JAXBElement<CreateVlanBySwtich> createCreateVlanBySwtich(CreateVlanBySwtich value) {
		return new JAXBElement<CreateVlanBySwtich>(_CreateVlanBySwtich_QNAME, CreateVlanBySwtich.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link VlanParameter }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "VlanParameter")
	public JAXBElement<VlanParameter> createVlanParameter(VlanParameter value) {
		return new JAXBElement<VlanParameter>(_VlanParameter_QNAME, VlanParameter.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link DeleteVlanBySwtich }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "deleteVlanBySwtich")
	public JAXBElement<DeleteVlanBySwtich> createDeleteVlanBySwtich(DeleteVlanBySwtich value) {
		return new JAXBElement<DeleteVlanBySwtich>(_DeleteVlanBySwtich_QNAME, DeleteVlanBySwtich.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CreateESGBySwtichResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://switches.generate.sobey.com", name = "createESGBySwtichResponse")
	public JAXBElement<CreateESGBySwtichResponse> createCreateESGBySwtichResponse(CreateESGBySwtichResponse value) {
		return new JAXBElement<CreateESGBySwtichResponse>(_CreateESGBySwtichResponse_QNAME,
				CreateESGBySwtichResponse.class, null, value);
	}

}
