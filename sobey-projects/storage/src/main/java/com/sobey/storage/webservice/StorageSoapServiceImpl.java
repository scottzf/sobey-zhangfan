package com.sobey.storage.webservice;

import java.io.File;
import java.io.IOException;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.core.utils.JschUtil;
import com.sobey.core.utils.PropertiesLoader;
import com.sobey.storage.constans.MethodEnum;
import com.sobey.storage.constans.WsConstants;
import com.sobey.storage.service.NetAppService;
import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;
import com.sobey.storage.webservice.response.dto.MountEs3Parameter;
import com.sobey.storage.webservice.response.dto.RemountEs3Parameter;
import com.sobey.storage.webservice.response.dto.UmountEs3Parameter;
import com.sobey.storage.webservice.response.result.WSResult;

@WebService(serviceName = "StorageSoapService", endpointInterface = "com.sobey.storage.webservice.StorageSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class StorageSoapServiceImpl implements StorageSoapService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	protected static PropertiesLoader STORAGE_LOADER = new PropertiesLoader("classpath:/storage.properties");

	/* netapp controller登录 */
	protected static final String STORAGE_IP = STORAGE_LOADER.getProperty("STORAGE_IP");
	protected static final String STORAGE_USERNAME = STORAGE_LOADER.getProperty("STORAGE_USERNAME");
	protected static final String STORAGE_PASSWORD = STORAGE_LOADER.getProperty("STORAGE_PASSWORD");

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
	private NetAppService service;

	@Override
	public WSResult createEs3ByStorage(@WebParam(name = "createEs3Parameter") CreateEs3Parameter createEs3Parameter) {

		WSResult result = new WSResult();

		String command = service.createEs3(createEs3Parameter);

		boolean isconnect = JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// 判断是否连接交换机
		if (!isconnect) {

			try {

				String result2 = FileUtils.readFileToString(new File(getFilePath(createEs3Parameter.getVolumeName())));

				result = TerminalResultHandle.ResultHandle(result2, MethodEnum.create);

			} catch (IOException e) {
				result.setDefaultError();
			}

		} else {
			result.setCode(WSResult.SYSTEM_ERROR);
			result.setMessage("交换机未连接,请联系管理员");
		}

		return result;
	}

	@Override
	public WSResult deleteEs3ByStorage(@WebParam(name = "deleteEs3Parameter") DeleteEs3Parameter deleteEs3Parameter) {

		WSResult result = new WSResult();

		String command = service.deleteEs3(deleteEs3Parameter);

		boolean isconnect = JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// 判断是否连接交换机
		if (!isconnect) {

			try {

				String result2 = FileUtils.readFileToString(new File(getFilePath(deleteEs3Parameter.getVolumeName())));

				result = TerminalResultHandle.ResultHandle(result2, MethodEnum.delete);

			} catch (IOException e) {
				result.setDefaultError();
			}

		} else {
			result.setCode(WSResult.SYSTEM_ERROR);
			result.setMessage("交换机未连接,请联系管理员");
		}

		return result;

	}

	@Override
	public WSResult mountEs3ByStorage(@WebParam(name = "mountEs3Parameter") MountEs3Parameter mountEs3Parameter) {

		WSResult result = new WSResult();

		String command = service.mountEs3(mountEs3Parameter);

		boolean isconnect = JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// 判断是否连接交换机
		if (!isconnect) {

			try {

				String result2 = FileUtils.readFileToString(new File(getFilePath(mountEs3Parameter.getVolumeName())));

				result = TerminalResultHandle.ResultHandle(result2, MethodEnum.mount);

			} catch (IOException e) {
				result.setDefaultError();
			}

		} else {
			result.setCode(WSResult.SYSTEM_ERROR);
			result.setMessage("交换机未连接,请联系管理员");
		}

		return result;

	}

	@Override
	public WSResult umountEs3ByStorage(@WebParam(name = "umountEs3Parameter") UmountEs3Parameter umountEs3Parameter) {

		WSResult result = new WSResult();

		String command = service.umountEs3(umountEs3Parameter);

		boolean isconnect = JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// 判断是否连接交换机
		if (!isconnect) {

			try {

				String result2 = FileUtils.readFileToString(new File(getFilePath(umountEs3Parameter
						.getClientIPaddress())));

				result = TerminalResultHandle.ResultHandle(result2, MethodEnum.umount);

			} catch (IOException e) {
				result.setDefaultError();
			}

		} else {
			result.setCode(WSResult.SYSTEM_ERROR);
			result.setMessage("交换机未连接,请联系管理员");
		}

		return result;

	}

	@Override
	public WSResult remountEs3ByStorage(@WebParam(name = "remountEs3Parameter") RemountEs3Parameter remountEs3Parameter) {

		WSResult result = new WSResult();

		String command = service.remountEs3(remountEs3Parameter);

		boolean isconnect = JschUtil.execCommand(STORAGE_IP, STORAGE_USERNAME, STORAGE_PASSWORD, command);

		// 判断是否连接交换机
		if (!isconnect) {

			try {

				String result2 = FileUtils.readFileToString(new File(getFilePath(remountEs3Parameter.getVolumeName())));

				result = TerminalResultHandle.ResultHandle(result2, MethodEnum.remount);

			} catch (IOException e) {
				result.setDefaultError();
			}

		} else {
			result.setCode(WSResult.SYSTEM_ERROR);
			result.setMessage("交换机未连接,请联系管理员");
		}

		return result;

	}

}
