package com.sobey.instance.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.instance.constans.WsConstants;
import com.sobey.instance.webservice.response.dto.BindingPortGroupParameter;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.CreatePortGroupParameter;
import com.sobey.instance.webservice.response.dto.CreateStandardSwitchParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.HostInfoDTO;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;
import com.sobey.instance.webservice.response.dto.VMDiskParameter;
import com.sobey.instance.webservice.response.dto.VMInfoDTO;
import com.sobey.instance.webservice.response.result.DTOListResult;
import com.sobey.instance.webservice.response.result.DTOResult;
import com.sobey.instance.webservice.response.result.WSResult;

/**
 * Instance对外暴露的唯一的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "InstanceSoapService", targetNamespace = WsConstants.NS)
public interface InstanceSoapService {

	// =============== Virtual Machine ===============//
	/**
	 * Clone虚拟机
	 * 
	 * @param cloneVMParameter
	 *            {@link CloneVMParameter}
	 * @return
	 */
	WSResult cloneVMByInstance(@WebParam(name = "cloneVMParameter") CloneVMParameter cloneVMParameter);

	/**
	 * 销毁虚拟机
	 * 
	 * @param destroyVMParameter
	 *            {@link DestroyVMParameter}
	 * @return
	 */
	WSResult destroyVMByInstance(@WebParam(name = "destroyVMParameter") DestroyVMParameter destroyVMParameter);

	/**
	 * 修改虚拟机配置
	 * 
	 * @param reconfigVMParameter
	 *            {@link ReconfigVMParameter}
	 * @return
	 */
	WSResult reconfigVMByInstance(@WebParam(name = "reconfigVMParameter") ReconfigVMParameter reconfigVMParameter);

	/**
	 * 对虚拟机进行电源操作
	 * 
	 * @param powerVMParameter
	 *            {@link PowerVMParameter}
	 * @return
	 */
	WSResult powerVMByInstance(@WebParam(name = "powerVMParameter") PowerVMParameter powerVMParameter);

	/**
	 * 调用vcenter接口,获得宿主机和虚拟机之间的关联关系,放入HashMap中返回.<br>
	 * 返回的HashMap中,<b>key为VM名称,value为Host名称</b>
	 * 
	 * @return
	 */
	RelationVMParameter getRelationByInstance(@WebParam(name = "datacenter") String datacenter);

	/**
	 * 根据虚拟机名称获得虚拟机信息
	 * 
	 * @param vmName
	 *            虚拟机名称
	 * @return
	 */
	DTOResult<VMInfoDTO> findVMInfoDTO(@WebParam(name = "vmName") String vmName,
			@WebParam(name = "datacenter") String datacenter);

	// =============== Network ===============//

	/**
	 * 在宿主机上创建标准交换机
	 * 
	 * @param createStandardSwitchParameter
	 *            {@link CreateStandardSwitchParameter}
	 * @return
	 */
	WSResult createStandardSwitchByInstance(
			@WebParam(name = "createStandardSwitchParameter") CreateStandardSwitchParameter createStandardSwitchParameter);

	/**
	 * 在标准交换机上创建标准交换机网络(虚拟端口组)
	 * 
	 * @param createPortGroupParameter
	 *            {@link CreatePortGroupParameter}
	 * @return
	 */
	WSResult createPortGroupInstance(
			@WebParam(name = "createPortGroupParameter") CreatePortGroupParameter createPortGroupParameter);

	/**
	 * 为虚拟机绑定端口组
	 * 
	 * @param bindingPortGroupParameter
	 *            {@link BindingPortGroupParameter}
	 * @return
	 */
	WSResult bindingPortGroupInstance(
			@WebParam(name = "bindingPortGroupParameter") BindingPortGroupParameter bindingPortGroupParameter);

	// =============== Disk ===============//

	/**
	 * 为虚拟机分配存储空间
	 * 
	 * @param vmDiskParameter
	 *            {@link CreateVMDiskParameter}
	 * @return
	 */
	WSResult createVMDiskByInstance(@WebParam(name = "vmDiskParameter") VMDiskParameter vmDiskParameter);

	/**
	 * 删除虚拟机分配的指定存储空间
	 * 
	 * @param vmDiskParameter
	 *            {@link VMDiskParameter}
	 * @return
	 */
	WSResult deleteVMDiskByInstance(@WebParam(name = "vmDiskParameter") VMDiskParameter vmDiskParameter);

	// =============== Host ===============//

	/**
	 * 根据datacenter获得Host列表
	 * 
	 * @param datacenter
	 *            数据中心
	 * @return
	 */
	DTOListResult<HostInfoDTO> getHostInfoDTO(@WebParam(name = "datacenter") String datacenter);

	/**
	 * 获得指定Host的信息
	 * 
	 * @param hostName
	 *            Host名称
	 * @param datacenter
	 *            数据中心
	 * @return
	 */
	DTOResult<HostInfoDTO> findHostInfoDTO(@WebParam(name = "hostName") String hostName,
			@WebParam(name = "datacenter") String datacenter);

}
