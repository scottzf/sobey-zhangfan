<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Instance Relation Demo</title>
</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div class="form-group">

			<c:forEach var="map" items="${relations}">
				<div class="checkbox">
					<label> ${map.value } - ${map.key } </label>
				</div>
			</c:forEach>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary">Refresh</button>
			</div>
		</div>
	</form>

</body>
</html>
