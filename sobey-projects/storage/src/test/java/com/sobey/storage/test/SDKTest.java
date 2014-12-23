package com.sobey.storage.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

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

public class SDKTest {

	private static String host = "172.20.0.11";
	private static String userName = "liukai";
	private static String password = "liukai@s0bey";

	// private static String host = "10.10.2.34";
	// private static String userName = "root";
	// private static String password = "XA@S0bey";

	Protocol protocol = Protocol.INSECURE_HTTPS;

	ApiRunner runner = new ApiRunner(ApiTarget.builder().withHost(host).withUserName(userName).withPassword(password)
			.withTargetType(TargetType.FILER).useProtocol(protocol).build());

	@Test
	public void volList() {

		VolumeListInfoIterStartRequest volListReq = new VolumeListInfoIterStartRequest();
		// volListReq.setVolume("sobey533");

		Iterator<VolumeInfo> volumeIter = runner.iterate(volListReq, 10);

		VolumeInfo volume;

		int i = 0;
		while (volumeIter.hasNext()) {
			System.out.println("------------------------------------------------");
			volume = volumeIter.next();
			System.out.println("Name               : " + volume.getName());
			System.out.println("Type               : " + volume.getType());
			System.out.println("State              : " + volume.getState());
			System.out.println("Total size (GB) : " + MathsUtil.div(volume.getSizeTotal().doubleValue(), 1073741824));
			System.out.println("Used size (GB)  : " + MathsUtil.div(volume.getSizeUsed().doubleValue(), 1073741824));
			System.out.println("Available size (GB)  : "
					+ MathsUtil.div(volume.getSizeAvailable().doubleValue(), 1073741824));
			System.out.println("Snapshot Blocks Reserved size (GB) : "
					+ MathsUtil.div(volume.getSnapshotBlocksReserved().doubleValue(), 1048576));
			System.out.println("------------------------------------------------");
			i++;
		}
		System.out.println(i);
	}

	@Test
	public void VolumeCreate() {
		VolumeCreateRequest request = new VolumeCreateRequest();
		request.withContainingAggrName("aggr0").withSize("20M").withVolume("xman");
		runner.run(request);
	}

	@Test
	public void VolumeDestroy() {
		VolumeDestroyRequest request = new VolumeDestroyRequest();
		request.setName("xman");
		runner.run(request);
	}

	@Test
	public void VolumeOffline() {
		// 脱机
		VolumeOfflineRequest request = new VolumeOfflineRequest();
		request.setName("xman");
		runner.run(request);
	}

	@Test
	public void ExportfsListRules() {

		// 获得卷挂载信息
		NfsExportfsListRulesRequest ruleGetRequest = new NfsExportfsListRulesRequest();
		ruleGetRequest.setPathname("/vol/xman");

		NfsExportfsListRulesResponse ruleList = runner.run(ruleGetRequest);

		for (ExportsRuleInfo ruleInfo : ruleList.getRules()) {

			for (ExportsHostnameInfo hostnameInfo : ruleInfo.getRoot()) {
				System.out.println(hostnameInfo.getName());
			}
		}
	}

	@Test
	public void modifyRule() {

		NfsExportfsListRulesRequest ruleGetRequest = new NfsExportfsListRulesRequest();
		ruleGetRequest.setPathname("/vol/xman");
		NfsExportfsListRulesResponse ruleList = runner.run(ruleGetRequest);

		// 新增访问权限
		for (ExportsRuleInfo exportsRuleInfo : ruleList.getRules()) {

			List<ExportsHostnameInfo> rootlist = exportsRuleInfo.getRoot();

			ExportsHostnameInfo hostnameInfo = new ExportsHostnameInfo();
			hostnameInfo.setAllHosts(false);
			hostnameInfo.setName("10.10.100.2");

			rootlist.add(hostnameInfo);

			NfsExportfsModifyRuleRequest exportfsModifyRuleRequest = new NfsExportfsModifyRuleRequest();
			exportfsModifyRuleRequest.setPersistent(true);
			exportfsModifyRuleRequest.setRule(exportsRuleInfo);

			runner.run(exportfsModifyRuleRequest);
		}

		ExportfsListRules();
	}

	@Test
	public void removeRule() {

		// 删除多余的IP, 需要new 一个新的ExportsRuleInfo,并将可访问的IP写入.
		ExportsRuleInfo exportsRuleInfo = new ExportsRuleInfo();
		exportsRuleInfo.setPathname("/vol/xman");
		List<ExportsHostnameInfo> rootlist = new ArrayList<ExportsHostnameInfo>();

		ExportsHostnameInfo hostnameInfo = new ExportsHostnameInfo();
		hostnameInfo.setAllHosts(false);
		hostnameInfo.setName("10.10.100.1");

		rootlist.add(hostnameInfo);

		exportsRuleInfo.setRoot(rootlist);

		NfsExportfsModifyRuleRequest exportfsModifyRuleRequest = new NfsExportfsModifyRuleRequest();
		exportfsModifyRuleRequest.setPersistent(true);
		exportfsModifyRuleRequest.setRule(exportsRuleInfo);

		runner.run(exportfsModifyRuleRequest);
		ExportfsListRules();
	}

}
