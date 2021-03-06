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
import com.sobey.core.utils.MathsUtil;
import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.ModifytEs3RuleParameter;
import com.sobey.storage.webservice.response.dto.NetAppParameter;
import com.sobey.storage.webservice.response.dto.VolumeInfoDTO;
import com.sobey.storage.webservice.response.result.DTOListResult;
import com.sobey.storage.webservice.response.result.DTOResult;
import com.sobey.storage.webservice.response.result.WSResult;

/**
 * Netapp Service.
 * 
 * @author Administrator
 * 
 */
@Service
public class NetAppService {

	/**
	 * netapp中默认的聚合名 : aggr1
	 */
	private static String Default_AggrName = "aggr1";

	/**
	 * netapp存储的基本单位
	 * 
	 * TODO 注意存储单位.测试环境无法创建GB大小的volume,故暂时用MB(M),待部署到生成环境应该切换成GB.
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

	public DTOListResult<VolumeInfoDTO> getVolumeInfoDTO(NetAppParameter parameter) {

		DTOListResult<VolumeInfoDTO> result = new DTOListResult<VolumeInfoDTO>();

		ApiRunner runner = getApiRunner(parameter.getControllerIP(), parameter.getUsername(), parameter.getPassword());

		VolumeListInfoIterStartRequest volListReq = new VolumeListInfoIterStartRequest();
		Iterator<VolumeInfo> volumeIter = runner.iterate(volListReq);

		List<VolumeInfoDTO> dtos = new ArrayList<VolumeInfoDTO>();

		while (volumeIter.hasNext()) {

			dtos.add(wrapVolumeInfoDTO(volumeIter.next()));
		}

		result.setDtos(dtos);

		return result;
	}

	public DTOResult<VolumeInfoDTO> findVolumeInfoDTO(NetAppParameter parameter) {

		DTOResult<VolumeInfoDTO> result = new DTOResult<VolumeInfoDTO>();

		ApiRunner runner = getApiRunner(parameter.getControllerIP(), parameter.getUsername(), parameter.getPassword());

		VolumeListInfoIterStartRequest volListReq = new VolumeListInfoIterStartRequest();
		volListReq.setVolume(parameter.getVolumeName());// 指定卷名可以查询该卷的信息.

		Iterator<VolumeInfo> volumeIter = runner.iterate(volListReq);

		List<VolumeInfoDTO> dtos = new ArrayList<VolumeInfoDTO>();

		while (volumeIter.hasNext()) {
			dtos.add(wrapVolumeInfoDTO(volumeIter.next()));
		}

		if (!dtos.isEmpty()) {
			result.setDto(dtos.get(0));
		}

		return result;
	}

	/**
	 * VolumeInfo -> VolumeInfoDTO
	 * 
	 * @param volume
	 * @return
	 */
	private VolumeInfoDTO wrapVolumeInfoDTO(VolumeInfo volume) {

		// 1048576 = 1024*1024
		Double snapshotSize = MathsUtil.div(volume.getSnapshotBlocksReserved().doubleValue(), 1048576);

		// bytes -> GB 1073741824 = 1024*1024*1024
		// totalSize =卷实际大小+卷snapshot大小
		Double totalSize = MathsUtil.add(MathsUtil.div(volume.getSizeTotal().doubleValue(), 1073741824), snapshotSize);
		Double usedSize = MathsUtil.div(volume.getSizeUsed().doubleValue(), 1073741824);
		Double availableSize = MathsUtil.div(volume.getSizeAvailable().doubleValue(), 1073741824);
		String usedSizePre = String.valueOf(MathsUtil.div(usedSize, totalSize));

		// 是否是精简模式(Thin Provisioned),"volume" = "NO" ,"none" = "YES"
		String isThinProvisioned = "";
		if ("volume".equals(volume.getSpaceReserve())) {
			isThinProvisioned = "No";
		} else {
			isThinProvisioned = "Yes";
		}

		VolumeInfoDTO volumeInfoDTO = new VolumeInfoDTO(volume.getName(), volume.getState(), volume.getFilesTotal()
				.toString(), volume.getFilesUsed().toString(), volume.getContainingAggregate(), volume.getType(),
				isThinProvisioned, String.valueOf(totalSize), String.valueOf(usedSize), usedSizePre,
				String.valueOf(availableSize), String.valueOf(snapshotSize));

		return volumeInfoDTO;
	}
}
