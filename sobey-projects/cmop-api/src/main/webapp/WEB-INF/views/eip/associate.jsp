<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Eip Associate Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>绑定EIP页面</h3>
		</div>

		<div class="form-group">
			<label for="eipId" class="col-sm-2 control-label">EIP列表</label>
			<div class="col-sm-4">
				<select id="eipId" name="eipId" class="form-control">
					<c:forEach var="item" items="${eipList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label for="serviceId" class="col-sm-2 control-label">服务资源列表</label>
			<div class="col-sm-4">
				<select id="serviceId" name="serviceId" class="form-control">
					<c:forEach var="item" items="${elbList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
					<c:forEach var="item" items="${ecsList}">
						<option value="${item.id }">${item.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Recover</button>
			</div>
		</div>

	</form>

</body>
</html>
