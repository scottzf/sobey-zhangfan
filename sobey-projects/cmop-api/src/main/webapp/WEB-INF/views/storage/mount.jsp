<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>ES3 Mount Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>挂载ES3页面</h3>
			<blockquote>
				<p>云存储（ES3）独立于云主机的生命周期而存在，可以被挂载到任意运行中的云主机上。</p>
			</blockquote>
		</div>

		<div class="form-group">
			<label for="ecsId" class="col-sm-2 control-label">ECS列表</label>
			<div class="col-sm-4">
				<select id="ecsId" name="ecsId" class="form-control">
					<c:forEach var="item" items="${ecsList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
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
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>
	</form>

</body>
</html>
