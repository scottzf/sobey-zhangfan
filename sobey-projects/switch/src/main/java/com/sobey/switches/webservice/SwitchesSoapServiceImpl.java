package com.sobey.switches.webservice;

import java.io.File;
import java.io.IOException;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.core.utils.PropertiesLoader;
import com.sobey.core.utils.TelnetUtil;
import com.sobey.switches.constans.MethodEnum;
import com.sobey.switches.constans.WsConstants;
import com.sobey.switches.service.SwitchService;
import com.sobey.switches.webservice.response.dto.ESGParameter;
import com.sobey.switches.webservice.response.dto.VlanParameter;
import com.sobey.switches.webservice.response.result.WSResult;

@WebService(serviceName = "SwitchesSoapService", endpointInterface = "com.sobey.switches.webservice.SwitchesSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class SwitchesSoapServiceImpl implements SwitchesSoapService {

	/**
	 * 加载applicationContext.propertie文件
	 */
	public static PropertiesLoader CORE_LOADER = new PropertiesLoader("classpath:/Core.properties");
	public static PropertiesLoader ACCESS_LOADER = new PropertiesLoader("classpath:/Access.properties");

	/* 核心交换机 */
	private static final String CORE_IP = CORE_LOADER.getProperty("CORE_IP");
	private static final String CORE_USERNAME = CORE_LOADER.getProperty("CORE_USERNAME");
	private static final String CORE_PASSWORD = CORE_LOADER.getProperty("CORE_PASSWORD");

	/* 接入层交换机,可能有数量不定的接入层交换机 */
	private static final String ACCESS_IP = ACCESS_LOADER.getProperty("ACCESS_IP");
	private static final String ACCESS_USERNAME = ACCESS_LOADER.getProperty("ACCESS_USERNAME");
	private static final String ACCESS_PASSWORD = ACCESS_LOADER.getProperty("ACCESS_PASSWORD");

	/**
	 * access配置文件中,不同交换机之间的分割符号.
	 */
	private static final String SEPARATOR_CHARS = ",";

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
	private SwitchService service;

	@Override
	public WSResult createVlanByCoreSwtich(@WebParam(name = "vlanParameter") VlanParameter vlanParameter) {

		WSResult result = new WSResult();

		String command = service.createVlanOnCoreLayer(vlanParameter.getVlanId(), vlanParameter.getGateway(),
				vlanParameter.getNetMask());

		String filePath = getFilePath(vlanParameter.getVlanId().toString());

		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.createVlan);

			// 如果报错,则将已插入的vlan删除.
			if (resultStr.contains(TerminalResultHandle.IP_OVERLAPS_ERROR)) {
				deleteVlanByCoreSwtich(vlanParameter.getVlanId());
			}

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult deleteVlanByCoreSwtich(@WebParam(name = "vlanId") Integer vlanId) {

		WSResult result = new WSResult();

		String command = service.deleteVlanOnAccessLayer(vlanId);

		String filePath = getFilePath(vlanId.toString());

		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.deleteVlan);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult createESGBySwtich(@WebParam(name = "esgParameter") ESGParameter esgParameter) {

		/*
		 * 在核心交换机上执行脚本.
		 */

		WSResult result = new WSResult();

		String command = service.createEsg(esgParameter.getAclNumber(), esgParameter.getVlanId(),
				esgParameter.getDesc(), esgParameter.getPermits(), esgParameter.getDenys());

		String filePath = getFilePath(esgParameter.getAclNumber().toString());

		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			// 如果报错,则将已插入的acl删除.
			if (resultStr.contains(TerminalResultHandle.CREATEACL_ERROR)) {
				deleteESGBySwtich(esgParameter.getAclNumber());
			}

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.createAcl);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult deleteESGBySwtich(@WebParam(name = "aclNumber") Integer aclNumber) {

		/*
		 * 在核心交换机上执行脚本.
		 */

		WSResult result = new WSResult();

		String command = service.deleteEsg(aclNumber);

		String filePath = getFilePath(aclNumber.toString());

		TelnetUtil.execCommand(CORE_IP, CORE_USERNAME, CORE_PASSWORD, command, filePath);

		try {

			String resultStr = FileUtils.readFileToString(new File(filePath));

			result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.deleteAcl);

		} catch (IOException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult createVlanByAccessSwtich(VlanParameter vlanParameter) {

		WSResult result = new WSResult();

		String command = service.createVlanOnAccessLayer(vlanParameter.getVlanId(), vlanParameter.getGateway(),
				vlanParameter.getNetMask());

		try {

			String[] ips = StringUtils.split(ACCESS_IP, SEPARATOR_CHARS);
			String[] names = StringUtils.split(ACCESS_USERNAME, SEPARATOR_CHARS);
			String[] passwords = StringUtils.split(ACCESS_PASSWORD, SEPARATOR_CHARS);

			for (int i = 0; i < ips.length; i++) {

				// Format:接入层交换机IP地址 - VlanId
				String filePath = getFilePath(ips[i] + "-" + vlanParameter.getVlanId().toString() + "-" + i);

				TelnetUtil.execCommand(ips[i], names[i], passwords[i], command, filePath);

				String resultStr = FileUtils.readFileToString(new File(filePath));
				result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.createVlan);

				// 每次执行完成,暂停2s,避免nonsocket的错误
				Thread.sleep(2000);
			}

		} catch (IOException e) {
			result.setDefaultError();
		} catch (InterruptedException e) {
			result.setDefaultError();
		}

		return result;
	}

	@Override
	public WSResult deleteVlanByAccessSwtich(Integer vlanId) {

		WSResult result = new WSResult();

		String command = service.deleteVlanOnAccessLayer(vlanId);

		try {

			String[] ips = StringUtils.split(ACCESS_IP, SEPARATOR_CHARS);
			String[] names = StringUtils.split(ACCESS_USERNAME, SEPARATOR_CHARS);
			String[] passwords = StringUtils.split(ACCESS_PASSWORD, SEPARATOR_CHARS);

			for (int i = 0; i < ips.length; i++) {

				// Format:接入层交换机IP地址 - VlanId
				String filePath = getFilePath(ips[i] + "-" + vlanId.toString() + "-" + i);

				TelnetUtil.execCommand(ips[i], names[i], passwords[i], command, filePath);

				String resultStr = FileUtils.readFileToString(new File(filePath));
				result = TerminalResultHandle.ResultHandle(resultStr, MethodEnum.deleteVlan);

				// 每次执行完成,暂停2s,避免nonsocket的错误
				Thread.sleep(2000);
			}

		} catch (IOException e) {
			result.setDefaultError();
		} catch (InterruptedException e) {
			result.setDefaultError();
		}

		return result;
	}

}
