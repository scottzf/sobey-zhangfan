package com.sobey.storage.webservice;

import java.io.File;
import java.io.IOException;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.core.utils.JschUtil;
import com.sobey.storage.constans.MethodEnum;
import com.sobey.storage.constans.WsConstants;
import com.sobey.storage.service.NetAppService;
import com.sobey.storage.service.NimbleService;
import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.Es3SizeParameter;
import com.sobey.storage.webservice.response.dto.ModifytEs3RuleParameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
import com.sobey.storage.webservice.response.dto.UmountEs3Parameter;
import com.sobey.storage.webservice.response.dto.VolumeInfoDTO;
import com.sobey.storage.webservice.response.result.DTOListResult;
import com.sobey.storage.webservice.response.result.WSResult;

@WebService(serviceName = "StorageSoapService", endpointInterface = "com.sobey.storage.webservice.StorageSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class StorageSoapServiceImpl implements StorageSoapService {

	/**
	 * 获得文件的相对路径,文件名自定义.
	 * 
	 * @param input
	 * @return
	 */
	private static String getFilePath(String input) {
		return "logs/" + input + ".txt";
	}

	@Autowired
	private NetAppService netAppService;

	@Autowired
	private NimbleService nimbleService;

	@Override
	public WSResult createEs3ByStorage(@WebParam(name = "createEs3Parameter") CreateEs3Parameter createEs3Parameter) {
		return netAppService.createEs3(createEs3Parameter);
	}

	@Override
	public WSResult deleteEs3ByStorage(@WebParam(name = "deleteEs3Parameter") DeleteEs3Parameter deleteEs3Parameter) {
		return netAppService.deleteEs3(deleteEs3Parameter);
	}

	@Override
	public WSResult mountEs3ByStorage(@WebParam(name = "mountEs3Parameter") MountEs3Parameter mountEs3Parameter) {

		WSResult result = new WSResult();

		try {

			String command = netAppService.mountEs3(mountEs3Parameter);

			String filePath = getFilePath(mountEs3Parameter.getVolumeName());

			JschUtil.execCommand(mountEs3Parameter.getControllerIP(), mountEs3Parameter.getUsername(),
					mountEs3Parameter.getPassword(), command, filePath);

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.mount);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult umountEs3ByStorage(@WebParam(name = "umountEs3Parameter") UmountEs3Parameter umountEs3Parameter) {

		WSResult result = new WSResult();

		try {

			String command = netAppService.umountEs3(umountEs3Parameter);

			String filePath = getFilePath(umountEs3Parameter.getClientIP());

			JschUtil.execCommand(umountEs3Parameter.getControllerIP(), umountEs3Parameter.getUsername(),
					umountEs3Parameter.getPassword(), command, filePath);

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.umount);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public String getEs3SizeTotal(Es3SizeParameter es3SizeParameter) {
		return netAppService.getEs3SizeTotal(es3SizeParameter);
	}

	@Override
	public String getEs3SizeUsed(Es3SizeParameter es3SizeParameter) {
		return netAppService.getEs3SizeUsed(es3SizeParameter);
	}

	@Override
	public WSResult modifytEs3RuleParameterByStorage(ModifytEs3RuleParameter modifytEs3RuleParameter) {
		return netAppService.modifyEs3Rule(modifytEs3RuleParameter);
	}

	@Override
	public DTOListResult<VolumeInfoDTO> getVolumeInfoDTO(Es3SizeParameter es3SizeParameter) {
		return netAppService.getVolumeInfoDTO(es3SizeParameter);
	}

	@Override
	public WSResult createEs3ByNimble(CreateEs3Parameter createEs3Parameter) {
		// TODO Auto-generated method stub
		WSResult result = new WSResult();

		// String command = nimbleService.createEs3(createEs3Parameter);

		String filePath = getFilePath(createEs3Parameter.getVolumeName());

		// JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.create);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult deleteEs3ByNimble(DeleteEs3Parameter deleteEs3Parameter) {

		// TODO Auto-generated method stub
		WSResult result = new WSResult();

		// String command = nimbleService.deleteEs3(deleteEs3Parameter);

		String filePath = getFilePath(deleteEs3Parameter.getVolumeName());

		// JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.delete);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

}
