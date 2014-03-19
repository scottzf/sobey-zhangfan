package com.sobey.instance.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.instance.constans.WsConstants;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;
import com.sobey.instance.webservice.response.result.WSResult;

/**
 * instance对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "InstanceSoapService", targetNamespace = WsConstants.NS)
public interface InstanceSoapService {

	/**
	 * 调用vcenter接口,clone虚拟机
	 * 
	 * @param parameter
	 *            {@link CloneVMParameter}
	 * @return
	 */
	WSResult cloneVMByInstance(@WebParam(name = "cloneVMParameter") CloneVMParameter cloneVMParameter);

	/**
	 * 调用vcenter接口,销毁虚拟机
	 * 
	 * @param parameter
	 *            {@link CloneVMParameter}
	 * @return
	 */
	WSResult destroyVMByInstance(@WebParam(name = "destroyVMParameter") DestroyVMParameter destroyVMParameter);

	/**
	 * 调用vcenter接口,修改虚拟机配置
	 * 
	 * @param parameter
	 *            {@link CloneVMParameter}
	 * @return
	 */
	WSResult reconfigVMByInstance(@WebParam(name = "reconfigVMParameter") ReconfigVMParameter reconfigVMParameter);

	/**
	 * 调用vcenter接口,对虚拟机进行电源操作
	 * 
	 * @param parameter
	 *            {@link CloneVMParameter}
	 * @return
	 */
	WSResult powerVMByInstance(@WebParam(name = "powerVMParameter") PowerVMParameter powerVMParameter);

	/**
	 * 调用vcenter接口,获得宿主机和虚拟机之间的关联关系,放入HashMap中返回.<br>
	 * <b>返回的HashMap中,key为VM名称,value为Host名称</b>
	 * 
	 * @return
	 */
	RelationVMParameter getVMAndHostRelationByInstance();

}
