package com.sobey.storage.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.netapp.nmsdk.client.ApiRunner;
import com.netapp.nmsdk.client.ApiTarget;
import com.netapp.nmsdk.client.ApiTarget.Protocol;
import com.netapp.nmsdk.client.ApiTarget.TargetType;
import com.netapp.nmsdk.ontap.api.nfs.ExportsHostnameInfo;
import com.netapp.nmsdk.ontap.api.nfs.ExportsRuleInfo;
import com.netapp.nmsdk.ontap.api.nfs.NfsExportfsListRulesRequest;
import com.netapp.nmsdk.ontap.api.nfs.NfsExportfsListRulesResponse;
import com.netapp.nmsdk.ontap.api.nfs.NfsExportfsModifyRuleRequest;
import com.netapp.nmsdk.ontap.api.volume.VolumeCreateRequest;
import com.netapp.nmsdk.ontap.api.volume.VolumeDestroyRequest;
import com.netapp.nmsdk.ontap.api.volume.VolumeInfo;
import com.netapp.nmsdk.ontap.api.volume.VolumeListInfoIterStartRequest;
import com.netapp.nmsdk.ontap.api.volume.VolumeOfflineRequest;
import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.Es3SizeParameter;
import com.sobey.storage.webservice.response.dto.ModifytEs3RuleParameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
import com.sobey.storage.webservice.response.dto.UmountEs3Parameter;
import com.sobey.storage.webservice.response.dto.VolumeInfoDTO;
import com.sobey.storage.webservice.response.result.DTOListResult;
import com.sobey.storage.webservice.response.result.WSResult;

/**
 * netapp 脚本模板生成类.
 * 
 * @author Administrator
 * 
 */
@Service
public class NetAppService {

	/**
	 * netapp中默认的聚合名
	 */
	private static String Default_AggrName = "aggr0";

	/**
	 * netapp存储的基本单位 M
	 * 
	 * TODO 注意存储单位.测试环境无法创建GB大小的volume.故暂时用MB.
	 */
	private static String Default_StorageUntil = "M";

	/**
	 * netapp连接器
	 * 
	 * @param ip
	 *            netapp控制器IP
	 * @param username
	 *            netapp控制器登录名
	 * @param password
	 *            netapp控制器登录密码
	 * @return
	 */
	private ApiRunner getApiRunner(String ip, String username, String password) {
		return new ApiRunner(ApiTarget.builder().withHost(ip).withUserName(username).withPassword(password)
				.withTargetType(TargetType.FILER).useProtocol(Protocol.INSECURE_HTTPS).build());
	}

	/**
	 * 创建卷
	 * 
	 * @param parameter
	 * @return
	 */
	public WSResult createEs3(CreateEs3Parameter parameter) {

		WSResult result = new WSResult();

		try {

			ApiRunner runner = getApiRunner(parameter.getControllerIP(), parameter.getUsername(),
					parameter.getPassword());

			VolumeCreateRequest request = new VolumeCreateRequest();
			request.withContainingAggrName(Default_AggrName).withSize(parameter.getVolumeSize() + Default_StorageUntil)
					.withVolume(parameter.getVolumeName());

			runner.run(request);

		} catch (Exception e) {
			result.setError(WSResult.PARAMETER_ERROR, e.getMessage());
		}
		return result;
	}

	/**
	 * 删除卷
	 * 
	 * @param parameter
	 * @return
	 */
	public WSResult deleteEs3(DeleteEs3Parameter parameter) {

		WSResult result = new WSResult();

		try {

			ApiRunner runner = getApiRunner(parameter.getControllerIP(), parameter.getUsername(),
					parameter.getPassword());

			// change status to "offline"
			VolumeOffline(runner, parameter.getVolumeName());

			VolumeDestroyRequest request = new VolumeDestroyRequest();
			request.setName(parameter.getVolumeName());
			runner.run(request);

		} catch (Exception e) {
			result.setError(WSResult.PARAMETER_ERROR, e.getMessage());
		}
		return result;
	}

	/**
	 * 修改卷的Client Permissions,即允许哪些IP可以挂载卷.
	 * 
	 * @param parameter
	 * @return
	 */
	public WSResult modifyEs3Rule(ModifytEs3RuleParameter parameter) {

		WSResult result = new WSResult();

		try {

			ApiRunner runner = getApiRunner(parameter.getControllerIP(), parameter.getUsername(),
					parameter.getPassword());

			// 获得卷下面Client Permissions信息
			NfsExportfsListRulesRequest ruleGetRequest = new NfsExportfsListRulesRequest();
			ruleGetRequest.setPathname("/vol/" + parameter.getVolumeName());
			NfsExportfsListRulesResponse ruleList = runner.run(ruleGetRequest);

			// 新增访问权限
			for (ExportsRuleInfo exportsRuleInfo : ruleList.getRules()) {

				// 将exportsRuleInfo.setRoot里的参数重新定义,使得新的覆盖旧的参数.

				List<ExportsHostnameInfo> rootlist = new ArrayList<ExportsHostnameInfo>();

				for (String ip : parameter.getClientIPs()) {
					ExportsHostnameInfo hostnameInfo = new ExportsHostnameInfo();
					hostnameInfo.setAllHosts(false);
					hostnameInfo.setName(ip);
					rootlist.add(hostnameInfo);
				}

				exportsRuleInfo.setRoot(rootlist);

				NfsExportfsModifyRuleRequest exportfsModifyRuleRequest = new NfsExportfsModifyRuleRequest();
				exportfsModifyRuleRequest.setPersistent(true);
				exportfsModifyRuleRequest.setRule(exportsRuleInfo);

				runner.run(exportfsModifyRuleRequest);
			}

		} catch (Exception e) {
			result.setError(WSResult.PARAMETER_ERROR, e.getMessage());
		}

		return result;
	}

	/**
	 * 将卷状态改为offline
	 * 
	 * @param runner
	 * @param volumeName
	 *            卷名
	 */
	private void VolumeOffline(ApiRunner runner, String volumeName) {
		VolumeOfflineRequest request = new VolumeOfflineRequest();
		request.setName(volumeName);
		runner.run(request);
	}

	/**
	 * 获得ES3总大小
	 * 
	 * @param parameter
	 * @return
	 */
	public String getEs3SizeTotal(Es3SizeParameter parameter) {

		ApiRunner runner = getApiRunner(parameter.getControllerIP(), parameter.getUsername(), parameter.getPassword());

		VolumeListInfoIterStartRequest volListReq = new VolumeListInfoIterStartRequest();
		volListReq.setVolume(parameter.getVolumeName());

		Iterator<VolumeInfo> volumeIter = runner.iterate(volListReq, 10);

		String size = "";

		while (volumeIter.hasNext()) {

			VolumeInfo volume = volumeIter.next();
			size = volume.getSizeTotal().toString();

		}
		return size;
	}

	/**
	 * 获得ES3已使用大小
	 * 
	 * @param parameter
	 * @return
	 */
	public String getEs3SizeUsed(Es3SizeParameter parameter) {

		ApiRunner runner = getApiRunner(parameter.getControllerIP(), parameter.getUsername(), parameter.getPassword());

		VolumeListInfoIterStartRequest volListReq = new VolumeListInfoIterStartRequest();
		volListReq.setVolume(parameter.getVolumeName());

		Iterator<VolumeInfo> volumeIter = runner.iterate(volListReq);

		String size = "";

		while (volumeIter.hasNext()) {

			VolumeInfo volume = volumeIter.next();
			size = volume.getSizeUsed().toString();

		}
		return size;
	}

	public DTOListResult<VolumeInfoDTO> getVolumeInfoDTO(Es3SizeParameter parameter) {

		DTOListResult<VolumeInfoDTO> result = new DTOListResult<VolumeInfoDTO>();

		ApiRunner runner = getApiRunner(parameter.getControllerIP(), parameter.getUsername(), parameter.getPassword());

		VolumeListInfoIterStartRequest volListReq = new VolumeListInfoIterStartRequest();
		Iterator<VolumeInfo> volumeIter = runner.iterate(volListReq);

		VolumeInfo volume;

		List<VolumeInfoDTO> dtos = new ArrayList<VolumeInfoDTO>();

		while (volumeIter.hasNext()) {

			volume = volumeIter.next();

			VolumeInfoDTO volumeInfoDTO = new VolumeInfoDTO();
			volumeInfoDTO.setName(volume.getName());
			volumeInfoDTO.setTotalSize(volume.getSizeTotal().toString());
			volumeInfoDTO.setUsedSize(volume.getSizeUsed().toString());
			volumeInfoDTO.setAvailableSize(volume.getSizeAvailable().toString());
			dtos.add(volumeInfoDTO);
		}

		result.setDtos(dtos);

		return result;

	}

	/**
	 * 调用在<b>netapp脚本服务器</b>的挂载volume脚本.<br>
	 * eg:
	 * 
	 * <pre>
	 * /root/sc/mountdisk.sh 10.10.1.18 10.10.1.6 liukai
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link MountEs3Parameter}
	 * @return
	 */
	public String mountEs3(MountEs3Parameter parameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/mountdisk.sh ").append(parameter.getClientIP()).append(" ")
				.append(parameter.getControllerIP()).append(" ").append(parameter.getVolumeName()).append("\n");
		return sb.toString();
	}

	/**
	 * 调用在<b>netapp脚本服务器</b>上的卸载volume脚本.<br>
	 * eg:
	 * 
	 * <pre>
	 * /root/sc/umountdiks.sh 10.10.1.18
	 * </pre>
	 * 
	 * @param parameter
	 *            {@link UmountEs3Parameter}
	 * @return
	 */
	public String umountEs3(UmountEs3Parameter parameter) {
		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/umountdiks.sh ").append(parameter.getClientIP()).append(" \n");
		return sb.toString();
	}

}
