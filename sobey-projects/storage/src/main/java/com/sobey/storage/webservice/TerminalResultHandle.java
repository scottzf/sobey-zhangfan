package com.sobey.storage.webservice;

import com.sobey.storage.constans.MethodEnum;
import com.sobey.storage.webservice.response.result.WSResult;

/**
 * 对终端返回的信息进行处理.
 * 
 * 先将netapp返回的错误提示进行归纳,将其公共的信息抽象出来,然后将执行脚本返回的信息进行对比. <br>
 * 如果包含,说明报错,返回<b>false</b>.
 * 
 * @return
 */
public class TerminalResultHandle {

	private static final String CREATE_ERROR = "already exists";
	private static final String DELETE_ERROR = "No volume named";
	private static final String MOUNT_ERROR_ALREAD_YMOUNTED = "already mounted";
	private static final String MOUNT_ERROR_CANNOT_CREATE_DIRECTORY = "mkdir: cannot create directory";
	private static final String UMOUNT_ERROR = "not mounted";
	private static final String UMOUNT_ERROR_BUSY = "device is busy";

	public static WSResult ResultHandle(String info, MethodEnum methodEnum) {

		WSResult result = new WSResult();

		switch (methodEnum) {
		case create:

			if (info.contains(CREATE_ERROR)) {
				result.setError(WSResult.SYSTEM_ERROR, "卷名已存在");
			}

			break;
		case delete:
			if (info.contains(DELETE_ERROR)) {
				result.setError(WSResult.SYSTEM_ERROR, "卷不存在");
			}

			break;
		case mount:

			if (info.contains(MOUNT_ERROR_ALREAD_YMOUNTED)) {
				result.setError(WSResult.SYSTEM_ERROR, "卷已挂载");
			}

			if (info.contains(MOUNT_ERROR_CANNOT_CREATE_DIRECTORY)) {
				result.setError(WSResult.SYSTEM_ERROR, "卷不存在");
			}

			break;
		case umount:

			if (info.contains(UMOUNT_ERROR)) {
				result.setError(WSResult.SYSTEM_ERROR, "卷未挂载");
			}
			if (info.contains(UMOUNT_ERROR_BUSY)) {
				result.setError(WSResult.SYSTEM_ERROR, "卷在使用中");
			}

			break;

		default:
			break;
		}

		return result;

	}

}
