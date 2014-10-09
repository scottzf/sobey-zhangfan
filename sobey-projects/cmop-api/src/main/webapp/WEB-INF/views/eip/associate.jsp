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
			<blockquote>
				<p>在SobeyCloud系统中，您可以将申请到的公网 IP （Elastic IP） 地址分配到任意云主机
					，并随时可以解绑、再分配到其他云主机，如此可以快速替换您的对外云主机。</p>
			</blockquote>
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
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>

	</form>

</body>
</html>
