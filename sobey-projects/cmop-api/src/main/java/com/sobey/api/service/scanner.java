/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 被Spring各种Scheduler反射调用的Service POJO.
 * 
 * TODO 生产环境下去掉注释. 时间配置请查看 applicationContext-quartz-cron-local.xml
 * 
 */
@Component
public class scanner {

	@Autowired
	private ApiService service;

	public void executeSyncDNS() {
		// service.syncDNS();
		System.out.println("DNS同步完成!");
	}

	public void executeSyncELB() {
		// service.syncELB();
		System.out.println("ELB同步完成!");
	}

	public void executeSyncVolume() {
		// service.syncVolume();
		System.out.println("Volume同步完成!");
	}

	public void executeSyncVM() {
		// service.syncVM("xa");
		System.out.println("VM同步完成!");
	}

	public void createProduced() {

		// service.createProduced(producedDTO);
		System.out.println("VM同步完成!");
	}

}
