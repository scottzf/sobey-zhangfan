package com.sobey.api.service;

import java.util.List;

import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.storage.CreateEs3Parameter;

public interface ApiService {

	/**
	 * 创建租户,同时创建默认的VPN和ESG,并为租户分配一个未使用的Vlan.
	 * 
	 * @param tenantsDTO
	 */
	public void createTenants(TenantsDTO tenantsDTO);

	/**
	 * 创建ECS
	 * 
	 * @param tenantsId
	 * @param cloneVMParameter
	 */
	public void createECS(Integer tenantsId, CloneVMParameter cloneVMParameter);

	/**
	 * 销毁ECS
	 * 
	 * @param ecsId
	 */
	public void destroyECS(Integer ecsId);

	/**
	 * 针对ECS的电源操作
	 * 
	 * @param ecsId
	 * @param powerOperation
	 */
	public void powerOpsECS(Integer ecsId, String powerOperation);

	/**
	 * 修改ECS的配置
	 * 
	 * @param ecsId
	 * @param ecsSpecId
	 */
	public void reconfigECS(Integer ecsId, Integer ecsSpecId);

	/**
	 * 同步ECS
	 * 
	 * @param datacenter
	 * @return
	 */
	public String syncVM(String datacenter);

	/**
	 * 创建ES3
	 * 
	 * @param tenantsId
	 * @param createEs3Parameter
	 */
	public void createES3(Integer tenantsId, CreateEs3Parameter createEs3Parameter);

	/**
	 * 挂载ES3
	 * 
	 * @param es3Id
	 * @param ecsId
	 */
	public void attachES3(Integer es3Id, Integer ecsId);

	/**
	 * 卸载ES3
	 * 
	 * @param es3Id
	 * @param ecsId
	 */
	public void detachES3(Integer es3Id, Integer ecsId);

	/**
	 * 删除ES3
	 * 
	 * @param es3Id
	 */
	public void deleteES3(Integer es3Id);

	/**
	 * 分配EIP
	 * 
	 * @param eipDTO
	 * @param eipPolicyDTOs
	 */
	public void allocateEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs);

	/**
	 * 回收EIP
	 * 
	 * @param eipId
	 */
	public void recoverEIP(Integer eipId);

	/**
	 * 绑定EIP
	 * 
	 * @param eipId
	 * @param serviceId
	 */
	public void associateEIP(Integer eipId, Integer serviceId);

	/**
	 * 解绑EIP
	 * 
	 * @param eipId
	 * @param serviceId
	 */
	public void dissociateEIP(Integer eipId, Integer serviceId);

}
