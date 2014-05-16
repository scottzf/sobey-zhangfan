package com.sobey.api.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.api.utils.CMDBuildUtil;
import com.sobey.generate.cmdbuild.DTOResult;
import com.sobey.generate.cmdbuild.EcsDTO;
import com.sobey.generate.cmdbuild.ServerDTO;
import com.sobey.generate.instance.CloneVMParameter;
import com.sobey.generate.instance.DestroyVMParameter;
import com.sobey.generate.instance.InstanceSoapService;
import com.sobey.generate.instance.PowerVMParameter;
import com.sobey.generate.instance.ReconfigVMParameter;
import com.sobey.generate.instance.RelationVMParameter.RelationMaps.Entry;
import com.sobey.generate.instance.WSResult;

/**
 * Instance
 * 
 * @author Administrator
 * 
 */
@Service
public class InstanceService {

	@Autowired
	private CmdbuildService cmdbuildService;

	@Autowired
	private InstanceSoapService service;

	public WSResult desoroyVM(DestroyVMParameter destroyVMParameter) {
		return service.destroyVMByInstance(destroyVMParameter);
	}

	public HashMap<String, String> relationVM(String datacenter) {

		HashMap<String, String> map = new HashMap<String, String>();

		// 将RelationVMParameter转换成HashMap

		for (Entry entry : service.getVMAndHostRelationByInstance(datacenter).getRelationMaps().getEntry()) {
			map.put(entry.getKey(), entry.getValue());
		}

		return map;
	}

	public WSResult cloneVM(CloneVMParameter cloneVMParameter) {
		return service.cloneVMByInstance(cloneVMParameter);
	}

	public WSResult powerVM(PowerVMParameter powerVMParameter) {
		return service.powerVMByInstance(powerVMParameter);
	}

	public WSResult reconfigVM(ReconfigVMParameter reconfigVMParameter) {
		return service.reconfigVMByInstance(reconfigVMParameter);
	}

	/**
	 * 同步vcenter和CMDBuild数据
	 */
	public String syncVM(String datacenter) {

		StringBuffer sb = new StringBuffer();

		// 从vcenter中获得VM和Host的关联关系
		HashMap<String, String> vcenterMap = relationVM(datacenter);

		for (java.util.Map.Entry<String, String> entry : vcenterMap.entrySet()) {

			HashMap<String, String> ecsMap = new HashMap<String, String>();
			ecsMap.put("EQ_code", entry.getKey());

			// 根据VM的名称获得CMDBuild中的对象Ecs
			DTOResult dtoResult = cmdbuildService.findEcs(CMDBuildUtil.wrapperSearchParams(ecsMap));

			EcsDTO ecsDTO = (EcsDTO) dtoResult.getDto();

			// 根据SHost的名称获得CMDB中的对象Server
			ServerDTO serverDTO = (ServerDTO) cmdbuildService.findServer(ecsDTO.getServer()).getDto();

			// 比较VM从vcenter获取的关联关系中的Host名称和根据CMDBuild中查询得到的关联的Host名称是否相同
			// 如果不相同,则更改CMDBuild中关联的Server对象.
			if (!entry.getValue().equals(serverDTO.getCode())) {

				sb.append("vcenter中对应的宿主机:" + entry.getValue() + "<br>");
				sb.append("CMDBuild中对应的宿主机:" + serverDTO.getCode() + "<br>");
				sb.append("--------------------------------------------------");

				HashMap<String, String> serverMap = new HashMap<String, String>();
				serverMap.put("EQ_code", entry.getValue());

				ServerDTO serverDTO2 = (ServerDTO) cmdbuildService.findServer(
						CMDBuildUtil.wrapperSearchParams(serverMap)).getDto();

				ecsDTO.setServer(serverDTO2.getId());
				cmdbuildService.updateEcs(ecsDTO.getId(), ecsDTO);

			}

		}
		return sb.toString();
	}

}
