package com.sobey.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sobey.generate.storage.CreateEs3Parameter;
import com.sobey.generate.storage.DeleteEs3Parameter;
import com.sobey.generate.storage.MountEs3Parameter;
import com.sobey.generate.storage.RemountEs3Parameter;
import com.sobey.generate.storage.StorageSoapService;
import com.sobey.generate.storage.UmountEs3Parameter;
import com.sobey.generate.storage.WSResult;

/**
 * Storage
 * 
 * @author Administrator
 * 
 */
@Service
public class StorageService {

	@Autowired
	private StorageSoapService service;

	public WSResult createEs3(CreateEs3Parameter createEs3Parameter) {
		return service.createEs3ByStorage(createEs3Parameter);
	}

	public WSResult deleteEs3(DeleteEs3Parameter deleteEs3Parameter) {
		return service.deleteEs3ByStorage(deleteEs3Parameter);
	}

	public WSResult mountEs3(MountEs3Parameter mountEs3Parameter) {
		return service.mountEs3ByStorage(mountEs3Parameter);
	}

	public WSResult umountEs3(UmountEs3Parameter umountEs3Parameter) {
		return service.umountEs3ByStorage(umountEs3Parameter);
	}

	public WSResult remountEs3(RemountEs3Parameter remountEs3Parameter) {
		return service.remountEs3ByStorage(remountEs3Parameter);
	}

}
