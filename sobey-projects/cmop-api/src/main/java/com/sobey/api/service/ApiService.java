package com.sobey.api.service;

import java.util.List;

import com.sobey.api.constans.LookUpConstants;
import com.sobey.api.webservice.response.result.WSResult;
import com.sobey.generate.cmdbuild.DnsDTO;
import com.sobey.generate.cmdbuild.DnsPolicyDTO;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.EcsSpecDTO;
import com.sobey.generate.cmdbuild.EipDTO;
import com.sobey.generate.cmdbuild.EipPolicyDTO;
import com.sobey.generate.cmdbuild.ElbDTO;
import com.sobey.generate.cmdbuild.ElbPolicyDTO;
import com.sobey.generate.cmdbuild.Es3DTO;
import com.sobey.generate.cmdbuild.EsgDTO;
import com.sobey.generate.cmdbuild.EsgPolicyDTO;
import com.sobey.generate.cmdbuild.IdcDTO;
import com.sobey.generate.cmdbuild.TagDTO;
import com.sobey.generate.cmdbuild.TenantsDTO;

public interface ApiService {

	/**
	 * 创建一个租户,同时创建一个默认ESG和默认VPN账户
	 * 
	 * @param tenantsDTO
	 */
	public WSResult createTenants(TenantsDTO tenantsDTO);

	/**
	 * 创建一个ECS
	 * 
	 * @param ecsDTO
	 */
	public WSResult createECS(EcsDTO ecsDTO);

	/**
	 * 销毁ECS
	 * 
	 * @param ecsId
	 */
	public void destroyECS(Integer ecsId);

	/**
	 * 对ECS的电源操作,目前只支持开机和关机操作
	 * 
	 * @param ecsId
	 * @param powerOperation
	 *            {@link LookUpConstants}
	 */
	public WSResult powerOpsECS(Integer ecsId, String powerOperation);

	/**
	 * 修改ECS的配置
	 * 
	 * @param ecsId
	 * @param ecsSpecId
	 */
	public WSResult reconfigECS(Integer ecsId, Integer ecsSpecId);

	/**
	 * 同步ECS和VMware的关联关系至CMDBuild中.
	 * 
	 * @param datacenter
	 * @return
	 */
	public String syncVM(String datacenter);

	/**
	 * 创建ES3
	 * 
	 * @param es3DTO
	 */
	public WSResult createES3(Es3DTO es3DTO);

	/**
	 * 挂载ES3
	 * 
	 * @param es3Id
	 * @param ecsId
	 * @return
	 */
	public WSResult attachES3(Integer es3Id, Integer ecsId);

	/**
	 * 卸载ES3
	 * 
	 * @param es3Id
	 * @param ecsId
	 */
	public WSResult detachES3(Integer es3Id, Integer ecsId);

	/**
	 * 删除ES3
	 * 
	 * @param es3Id
	 */
	public WSResult deleteES3(Integer es3Id);

	/**
	 * 分配公网IP
	 * 
	 * @param eipDTO
	 * @param eipPolicyDTOs
	 */
	public WSResult allocateEIP(EipDTO eipDTO, List<EipPolicyDTO> eipPolicyDTOs);

	/**
	 * 回收EIP
	 * 
	 * @param eipId
	 */
	public void recoverEIP(Integer eipId);

	/**
	 * 关联EIP
	 * 
	 * @param eipId
	 * @param serviceId
	 * @return
	 */
	public WSResult associateEIP(Integer eipId, Integer serviceId);

	/**
	 * 解绑EIP
	 * 
	 * @param eipId
	 * @param serviceId
	 */
	public void dissociateEIP(Integer eipId, Integer serviceId);

	/**
	 * 创建ELB
	 * 
	 * @param elbDTO
	 * @param elbPolicyDTOs
	 * @param ecsIds
	 * @return
	 */
	public WSResult createELB(ElbDTO elbDTO, List<ElbPolicyDTO> elbPolicyDTOs, Integer[] ecsIds);

	/**
	 * 删除ELB
	 * 
	 * @param elbId
	 */
	public void deleteELB(Integer elbId);

	/**
	 * 创建DNS
	 * 
	 * @param dnsDTO
	 * @param dnsPolicyDTOs
	 * @param eipIds
	 */
	public void createDNS(DnsDTO dnsDTO, List<DnsPolicyDTO> dnsPolicyDTOs, Integer[] eipIds);

	/**
	 * 删除DNS
	 * 
	 * @param dnsId
	 */
	public void deleteDNS(Integer dnsId);

	/**
	 * 创建ESG
	 * 
	 * @param esgDTO
	 * @param esgPolicyDTOs
	 */
	public void createESG(EsgDTO esgDTO, List<EsgPolicyDTO> esgPolicyDTOs);

	/**
	 * 删除ESG
	 * 
	 * @param esgId
	 */
	public void deleteESG(Integer esgId);

	/**
	 * 绑定ESG
	 * 
	 * @param ecsId
	 * @param esgId
	 */
	public void associateESG(Integer ecsId, Integer esgId);

	/**
	 * 解绑ESG
	 * 
	 * @param ecsId
	 * @param esgId
	 */
	public void dissociateESG(Integer ecsId, Integer esgId);

	public List<TenantsDTO> getTenantsDTO();

	public List<IdcDTO> getIdcDTO();

	public List<EcsSpecDTO> getEcsSpecDTO();

	public List<EcsDTO> getEcsDTO();

	public List<Es3DTO> getEs3DTO();

	public List<EipDTO> getEipDTO();

	public List<ElbDTO> getElbDTO();

	public List<DnsDTO> getDnsDTO();

	public List<TagDTO> getTagDTO();

	/**
	 * 获得非默认的Esg List
	 * 
	 * @return
	 */
	public List<EsgDTO> getEsgDTO();

	/**
	 * 创建标签
	 * 
	 * @param tagDTO
	 */
	public void createTag(TagDTO tagDTO);

	/**
	 * 删除标签
	 * 
	 * @param tagId
	 */
	public void deleteTag(Integer tagId);

	/**
	 * 绑定标签
	 * 
	 * @param tagId
	 * @param serviceId
	 */
	public void associateTag(Integer tagId, Integer serviceId);

	/**
	 * 解绑标签
	 * 
	 * @param tagId
	 * @param serviceId
	 */
	public void dssociateTag(Integer tagId, Integer serviceId);

}
