<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Eip Recover Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>回收EIP页面</h3>
			<blockquote>
				<p>当您不再使用公网 IP （Elastic IP） ，SobeyCloud将对其进行回收。</p>
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
			<div class="col-sm-offset-2 col-sm-4">
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</div>

	</form>

</body>
</html>
