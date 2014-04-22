<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>Ping Demo</title>


<link rel="stylesheet" href="${ctx}/static/jqplot/jquery.jqplot.min.css">


<script src="${ctx}/static/jqplot/jquery.jqplot.min.js"></script>
<script src="${ctx}/static/jqplot/excanvas.min.js"></script>

<script src="${ctx}/static/jqplot/plugins/jqplot.cursor.min.js"></script>
<script
	src="${ctx}/static/jqplot/plugins/jqplot.dateAxisRenderer.min.js"></script>
<script src="${ctx}/static/jqplot/plugins/jqplot.logAxisRenderer.min.js"></script>
<script
	src="${ctx}/static/jqplot/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script
	src="${ctx}/static/jqplot/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
</head>


<body>

	<form class="form-horizontal" role="form" method="post" action=".">

		<div id="chart1" style="height: 200px; width: 600px;"></div>


		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="submit" class="btn btn-primary">Create</button>
			</div>
		</div>
	</form>

	<script>
		$(document).ready(function() {

			$.ajax({
				type : "POST",
				url : "${ctx}/monitor/ping/",
				dataType : "json",
				success : function(data) {

					var result = [];

					for ( var o in data) {
						for ( var ping in data[o]) {
							var temp = [];
							temp.push(data[o][ping].endTime);
							temp.push(data[o][ping].rta);
							result.push(temp);
						}
					}

					$.jqplot('chart1', [ result ], {
						title : 'Ping, RTA.',
						series : [ {
							label : 'Ping, RTA.',
							neighborThreshold : -1
						} ],
						axes : {
							xaxis : {
								renderer : $.jqplot.DateAxisRenderer,
								tickRenderer : $.jqplot.CanvasAxisTickRenderer,
								tickOptions : {
									angle : -30,
									formatString : '%Y-%m-%d %H:%M:%S'
								}
							},
							yaxis : {
								renderer : $.jqplot.LogAxisRenderer,
								tickOptions : {
									prefix : 'ms'
								}
							}
						},
						cursor : {
							show : true,
							zoom : true
						}
					});

				}

			});

		});
	</script>

</body>
</html>
