<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>


<!-- Fixed navbar -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${ctx}/">CMOP2</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Tenants<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/tenants/create/">创建租户</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">ECS<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/instance/create/">创建ECS</a></li>
						<li><a href="${ctx}/instance/destroy/">销毁ECS</a></li>
						<li><a href="${ctx}/instance/power/">ECS电源管理</a></li>
						<li><a href="${ctx}/instance/reconfig/">更改ECS规格</a></li>
						<li><a href="${ctx}/instance/sync/">同步关联关系</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">ES3<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/storage/create/">创建Es3</a></li>
						<li><a href="${ctx}/storage/delete/">删除Es3</a></li>
						<li><a href="${ctx}/storage/mount/">挂载Es3</a></li>
						<li><a href="${ctx}/storage/umount/">卸载Es3</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">ELB<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/elb/create/">创建ELB</a></li>
						<li><a href="${ctx}/elb/delete/">删除ELB</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">EIP<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/eip/allocate/">创建EIP</a></li>
						<li><a href="${ctx}/eip/recover/">回收EIP</a></li>
						<li><a href="${ctx}/eip/associate/">绑定EIP</a></li>
						<li><a href="${ctx}/eip/dissociate/">解绑EIP</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">DNS<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/dns/create/">创建DNS</a></li>
						<li><a href="${ctx}/dns/delete/">删除DNS</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">ESG<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/esg/create/">创建ESG</a></li>
						<li><a href="${ctx}/esg/delete/">删除ESG</a></li>
						<li><a href="${ctx}/esg/associate/">绑定ESG</a></li>
						<li><a href="${ctx}/esg/dissociate/">解绑ESG</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">标签<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/tag/create/">创建标签</a></li>
						<li><a href="${ctx}/tag/delete/">删除标签</a></li>
						<li><a href="${ctx}/tag/associate/">绑定标签</a></li>
						<li><a href="${ctx}/tag/dissociate/">解绑标签</a></li>
					</ul></li>

				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Monitor<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/zabbix/">ECS监控</a></li>
					</ul></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</div>
