<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ES3 Create Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>创建ES3页面</h3>
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
			<label for="es3TypeId" class="col-sm-2 control-label">存储类型</label>
			<div class="col-sm-4">
				<select id="es3TypeId" name="es3TypeId" class="form-control">
					<option value="44">高吞吐</option>
					<option value="73">高IOPS</option>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="volumeName" class="col-sm-2 control-label">ES3名</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="volumeName"
					name="volumeName" placeholder="ES3名" value="sobey">
			</div>
		</div>

		<div class="form-group">
			<label for="volumeSize" class="col-sm-2 control-label">ES3大小</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="volumeSize"
					name="volumeSize" placeholder="ES3大小(MB)" value="20">
			</div>
		</div>
		
		<div class="form-group">
			<label for=remark class="col-sm-2 control-label">备注</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="remark"
					name="remark" placeholder="备注" value="">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Create</button>
			</div>
		</div>
	</form>

</body>
</html>
