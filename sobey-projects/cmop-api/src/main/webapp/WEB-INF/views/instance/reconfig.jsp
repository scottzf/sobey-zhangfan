<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Instance Reconfig Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>ECS配置更新页面</h3>
		</div>

		<div class="form-group">
			<label for="ecsId" class="col-sm-2 control-label">ECS</label>
			<div class="col-sm-4">
				<select id="ecsId" name="ecsId" class="form-control">
					<c:forEach var="item" items="${ecsList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
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
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Reconfig</button>
			</div>
		</div>
	</form>

</body>
</html>
