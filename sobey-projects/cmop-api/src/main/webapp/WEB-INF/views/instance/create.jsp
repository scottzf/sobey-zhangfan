<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ECS Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>创建ECS页面</h3>
			<blockquote>
				<p>SobeyCloud为您提供一种随时获取的、弹性的计算能力，这种计算能力的体现就是云主机（ECS）。
					云主机就是一台配置好了的服务器，它有您期望的硬件配置、操作系统和网络配置，所以您完全可以动态地、按需使用计算能力。</p>
			</blockquote>
		</div>

		<div class="form-group">
			<label for="tenantsId" class="col-sm-2 control-label">租户</label>
			<div class="col-sm-4">
				<select id="tenantsId" name="tenantsId" class="form-control">
					<c:forEach var="item" items="${tenantsList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>


		<div class="form-group">
			<label for="idcId" class="col-sm-2 control-label">数据中心</label>
			<div class="col-sm-4">
				<select id="idcId" name="idcId" class="form-control">
					<c:forEach var="item" items="${idcList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="vmName" class="col-sm-2 control-label">ECS名称</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="vmname" name="vmname">
			</div>
		</div>

		<div class="form-group">
			<label for="ecsSpecId" class="col-sm-2 control-label">规格</label>
			<div class="col-sm-4">
				<select id="ecsSpecId" name="ecsSpecId" class="form-control">
					<c:forEach var="item" items="${ecsSpecList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="remark" class="col-sm-2 control-label">备注</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="remark" name="remark"
					placeholder="备注" value="CMOP v2.0 Demo">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>
	</form>

</body>
</html>
