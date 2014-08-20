<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Esg Associate Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>绑定ESG页面</h3>
		</div>

		<div class="form-group">
			<label for="ecsId" class="col-sm-2 control-label">Ecs列表</label>
			<div class="col-sm-4">
				<select id="ecsId" name="ecsId" class="form-control">
					<c:forEach var="item" items="${ecsList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="ecsId" class="col-sm-2 control-label">Esg列表</label>
			<div class="col-sm-4">
				<select id="esgId" name="esgId" class="form-control">
					<c:forEach var="item" items="${esgList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">绑定</button>
			</div>
		</div>

	</form>

</body>
</html>