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
					data-toggle="dropdown">Instance <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/instance/clone/">虚拟机克隆</a></li>
						<li><a href="${ctx}/instance/power/">虚拟机电源管理</a></li>
						<li><a href="${ctx}/instance/reconfig/">虚拟机配置管理</a></li>
						<li><a href="${ctx}/instance/destroy/">虚拟机销毁</a></li>
						<li><a href="${ctx}/instance/relation/">主机、虚拟机关联关系</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Dns <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/dns/create/">创建Dns</a></li>
						<li><a href="${ctx}/dns/delete/">删除Dns</a></li>
					</ul></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">Loadbalancer <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/loadbalancer/create/">创建Elb</a></li>
						<li><a href="${ctx}/loadbalancer/delete/">删除Elb</a></li>
					</ul></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</div>
