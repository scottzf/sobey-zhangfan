<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Instance Sync Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="page-header">
			<h3>vCenter主机、虚拟机关联关系同步至CMDBuild页面</h3>
		</div>

		<div class="form-group">
			<label for="idcId" class="col-sm-2 control-label">数据中心</label>
			<div class="col-sm-4">
				<select id="remark" name="remark" class="form-control">
					<c:forEach var="item" items="${idcList}">
						<option value="${item.remark }">${item.description}</option>
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
