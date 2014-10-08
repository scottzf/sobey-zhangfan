<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ES3 Data Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>存储用量查询</h3>
		</div>

		<div class="form-group">
			<label for="tenantsId" class="col-sm-2 control-label">ES3列表</label>
			<div class="col-sm-4">
				<select id="es3Id" name="es3Id" class="form-control">
					<c:forEach var="item" items="${es3List}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="tenantsId" class="col-sm-2 control-label"></label>
			<div class="col-sm-4">
				<p class="form-control-static">已用空间:${volumeData.value}
					${volumeData.units}</p>
				<p class="form-control-static">已用空间百分比:${volumeDataPre.value}
					${volumeDataPre.units}</p>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Refresh</button>
			</div>
		</div>
	</form>
</body>
</html>
