<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>TAG Dissociate Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>解绑标签页面</h3>
		</div>

		<div class="form-group">
			<label for="ecsId" class="col-sm-2 control-label">服务资源列表</label>
			<div class="col-sm-4">
				<select id="serviceId" name="serviceId" class="form-control">
					<c:forEach var="item" items="${ecsList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="ecsId" class="col-sm-2 control-label">标签列表</label>
			<div class="col-sm-4">
				<select id="tagId" name="tagId" class="form-control">
					<c:forEach var="item" items="${tagList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">解绑</button>
			</div>
		</div>

	</form>

</body>
</html>
