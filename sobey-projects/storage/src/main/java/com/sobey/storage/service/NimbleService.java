package com.sobey.storage.service;

import org.springframework.stereotype.Service;

import com.sobey.storage.webservice.response.dto.CreateEs3Parameter;
import com.sobey.storage.webservice.response.dto.DeleteEs3Parameter;

/**
 * Nimble Service
 * 
 * @author Administrator
 *
 */
@Service
public class NimbleService {

	/**
	 * 创建卷
	 * 
	 * @param parameter
	 * @return
	 */
	public String createEs3(CreateEs3Parameter parameter) {

		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/del.sh ").append(parameter.getVolumeName()).append(" \n");

		return sb.toString();
	}

	/**
	 * 删除卷
	 * 
	 * @param parameter
	 * @return
	 */
	public String deleteEs3(DeleteEs3Parameter parameter) {
		// TODO Auto-generated method stub

		StringBuilder sb = new StringBuilder();
		sb.append("/root/sc/del.sh ").append(parameter.getVolumeName()).append(" \n");

		return sb.toString();
	}
}
