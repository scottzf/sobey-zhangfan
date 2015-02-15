package com.sobey.instance.webservice;

import javax.jws.WebService;

import org.apache.cxf.feature.Features;
import org.springframework.beans.factory.annotation.Autowired;

import com.sobey.instance.constans.WsConstants;
import com.sobey.instance.service.DiskService;
import com.sobey.instance.service.FolderService;
import com.sobey.instance.service.HostService;
import com.sobey.instance.service.NetworkService;
import com.sobey.instance.service.VMRCService;
import com.sobey.instance.service.VMService;
import com.sobey.instance.webservice.response.dto.BindingDVSPortGroupParameter;
import com.sobey.instance.webservice.response.dto.BindingNetworkDevicePortGroupParameter;
import com.sobey.instance.webservice.response.dto.BindingPortGroupParameter;
import com.sobey.instance.webservice.response.dto.CloneVMParameter;
import com.sobey.instance.webservice.response.dto.CreatePortGroupParameter;
import com.sobey.instance.webservice.response.dto.CreateStandardSwitchParameter;
import com.sobey.instance.webservice.response.dto.DestroyVMParameter;
import com.sobey.instance.webservice.response.dto.HostInfoDTO;
import com.sobey.instance.webservice.response.dto.PowerVMParameter;
import com.sobey.instance.webservice.response.dto.ReconfigVMParameter;
import com.sobey.instance.webservice.response.dto.RelationVMParameter;
import com.sobey.instance.webservice.response.dto.RenameVMParameter;
import com.sobey.instance.webservice.response.dto.RunNetworkDeviceVMParameter;
import com.sobey.instance.webservice.response.dto.RunVMParameter;
import com.sobey.instance.webservice.response.dto.VMDiskParameter;
import com.sobey.instance.webservice.response.dto.VMInfoDTO;
import com.sobey.instance.webservice.response.dto.VMRCDTO;
import com.sobey.instance.webservice.response.result.DTOListResult;
import com.sobey.instance.webservice.response.result.DTOResult;
import com.sobey.instance.webservice.response.result.WSResult;

@WebService(serviceName = "InstanceSoapService", endpointInterface = "com.sobey.instance.webservice.InstanceSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class InstanceSoapServiceImpl implements InstanceSoapService {

	@Autowired
	public VMService vmService;

	@Autowired
	public FolderService folderService;

	@Autowired
	public NetworkService networkService;

	@Autowired
	public DiskService diskService;

	@Autowired
	public HostService hostService;

	@Autowired
	public VMRCService vmrcService;

	@Override
	public WSResult cloneVMByInstance(CloneVMParameter cloneVMParameter) {
		return vmService.cloneVM(cloneVMParameter);
	}

	@Override
	public WSResult destroyVMByInstance(DestroyVMParameter destroyVMParameter) {
		return vmService.destroyVM(destroyVMParameter);
	}

	@Override
	public WSResult reconfigVMByInstance(ReconfigVMParameter reconfigVMParameter) {
		return vmService.reconfigVM(reconfigVMParameter);
	}

	@Override
	public WSResult powerVMByInstance(PowerVMParameter powerVMParameter) {
		return vmService.powerVM(powerVMParameter);
	}

	@Override
	public RelationVMParameter getRelationByInstance(String datacenter) {
		return vmService.getRelationVM(datacenter);
	}

	@Override
	public DTOResult<VMInfoDTO> findVMInfoDTO(String vmName, String datacenter) {
		return vmService.findVMInfoDTO(vmName, datacenter);
	}

	@Override
	public WSResult createStandardSwitchByInstance(CreateStandardSwitchParameter createStandardSwitchParameter) {
		return networkService.createStandardSwitch(createStandardSwitchParameter);
	}

	@Override
	public WSResult createPortGroupByInstance(CreatePortGroupParameter createPortGroupParameter) {
		return networkService.createPortGroup(createPortGroupParameter);
	}

	@Override
	public WSResult bindingPortGroupByInstance(BindingPortGroupParameter bindingPortGroupParameter) {
		return networkService.bindingPortGroup(bindingPortGroupParameter);
	}

	@Override
	public WSResult bindingNetworkDevicePortGroupByInstance(
			BindingNetworkDevicePortGroupParameter bindingNetworkDevicePortGroupParameter) {
		return networkService.bindingNetworkDevicePortGroup(bindingNetworkDevicePortGroupParameter);
	}

	@Override
	public WSResult createVMDiskByInstance(VMDiskParameter vmDiskParameter) {
		return diskService.createVMDisk(vmDiskParameter);
	}

	@Override
	public WSResult deleteVMDiskByInstance(VMDiskParameter vmDiskParameter) {
		return diskService.deleteVMDisk(vmDiskParameter);
	}

	@Override
	public DTOListResult<HostInfoDTO> getHostInfoDTO(String datacenter) {
		return hostService.getHostInfoDTOs(datacenter);
	}

	@Override
	public DTOResult<HostInfoDTO> findHostInfoDTO(String hostName, String datacenter) {
		return hostService.findHostInfoDTO(hostName, datacenter);
	}

	@Override
	public WSResult bindingDVSPortGroupByInstance(BindingDVSPortGroupParameter bindingDVSPortGroupParameter) {
		return networkService.bindingDVSPortGroup(bindingDVSPortGroupParameter);
	}

	@Override
	public DTOResult<VMRCDTO> findVMRCDTO(String vmName, String datacenter) {
		return vmrcService.connectVMRC(vmName, datacenter);
	}

	@Override
	public WSResult runVMByInstance(RunVMParameter runVMParameter) {
		return vmService.runVM(runVMParameter);
	}

	@Override
	public WSResult runNetworkDeviceVMByInstance(RunNetworkDeviceVMParameter networkDeviceVMParameter) {
		return vmService.runNetworkDeviceVM(networkDeviceVMParameter);
	}

	@Override
	public WSResult createFolderByInstance(String datacenter, String folderName) {
		return folderService.createFolder(datacenter, folderName, null);
	}

	@Override
	public WSResult createFolderOnParentByInstance(String datacenter, String folderName, String parentFolderName) {
		return folderService.createFolder(datacenter, folderName, parentFolderName);
	}

	@Override
	public WSResult moveVMByInstance(String datacenter, String vmName, String folderName) {
		return folderService.moveVM(datacenter, vmName, folderName);
	}

	@Override
	public DTOListResult<VMInfoDTO> getVMInfoDTOInFolderByInstance(String datacenter, String folderName) {
		return vmService.getVMInfoDTOInFolder(datacenter, folderName);
	}

	@Override
	public WSResult renameVMByInstance(RenameVMParameter renameVMParameter) {
		return vmService.renameVM(renameVMParameter);
	}

}
