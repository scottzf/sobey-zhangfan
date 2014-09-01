<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ES3 Delete Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>删除ES3页面</h3>
		</div>

		<div class="form-group">
			<label for="" es3Id"" class="col-sm-2 control-label">ES3列表</label>
			<div class="col-sm-4">
				<select id="" es3Id"" name="es3Id" class="form-control">
					<c:forEach var="item" items="${es3List}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Delete</button>
			</div>
		</div>
	</form>

</body>
</html>
