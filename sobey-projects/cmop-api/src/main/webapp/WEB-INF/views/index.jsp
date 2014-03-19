<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Demo</title>
</head>

<body>

	<div class="page-header">
		<h1>CMOP v2.0 核心功能测试页面</h1>
	</div>
	<ol>
		<li>提供云主机参数录入DEMO页面。IaaS中间件会拼装脚本或API完成指定云主机的自动化创建，交付ip、密码</li>
		<li>提供诸如云主机登录/重启/关机/存储挂载等DEMO页面</li>
		<li>提供云主机或云硬盘监控参数录入DEMO页面。IaaS中间件会拼装脚本为指定云主机、云硬盘实施监控，交付监控数据如CPU使用百分比、磁盘空间使用率、ip地址流量等</li>
		<li>提供域名解析或全局/局部负载参数录入DEMO页面。IaaS中间件会拼装脚本完成域名解析配置工作，交付可用的域名</li>
	</ol>

</body>
</html>
