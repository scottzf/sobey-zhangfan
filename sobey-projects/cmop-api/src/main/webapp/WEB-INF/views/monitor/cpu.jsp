<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>CPULoad Demo</title>

<script src="${ctx}/static/jqplot/plugins/jqplot.cursor.min.js"></script>
<script	src="${ctx}/static/jqplot/plugins/jqplot.dateAxisRenderer.min.js"></script>
<script src="${ctx}/static/jqplot/plugins/jqplot.logAxisRenderer.min.js"></script>
<script	src="${ctx}/static/jqplot/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script	src="${ctx}/static/jqplot/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>

</head>

<body>

	<form class="form-horizontal" role="form" method="post" action=".">
	
		<div class="form-group">
			<label for="startDate" class="col-sm-2 control-label">IPAddress</label>
			<div class="col-sm-2">
				<input type="text" class="form-control datepicker" id="ipaddress" name="ipaddress"
					placeholder="开始时间" value="172.20.34.1">
			</div>
		</div>
		
		<div class="form-group">
			<label for="startDate" class="col-sm-2 control-label">开始时间</label>
			<div class="col-sm-2">
				<input type="text" class="form-control datepicker" readonly="readonly" id="startDate" name="startDate"
					placeholder="开始时间">
			</div>
		</div>
		
		<div class="form-group">
			<label for="endDate" class="col-sm-2 control-label">结束时间</label>
			<div class="col-sm-2">
				<input type="text" class="form-control datepicker" readonly="readonly" id="endDate" name="endDate"
					placeholder="结束时间">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button type="button" class="btn btn-primary">Search</button>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div id="monitor-cpu-average5" style="height: 200px; width: 600px;"></div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div id="monitor-cpu-average10" style="height: 200px; width: 600px;"></div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<div id="monitor-cpu-average15" style="height: 200px; width: 600px;"></div>
			</div>
		</div>
		
	</form>

	<script>
		$(document).ready(function() {
			
			$(document).on("click","button",function(){
			 
				$.ajax({
					type : "POST",
					url : "${ctx}/monitor/cpu/",
					dataType : "json",
					data : {
						ipaddress: $("#ipaddress").val() ,
						startDate: $("#startDate").val() ,
						endDate: $("#endDate").val() 
					},
					success : function(msg) {
						
						//为满足jqplot数据格式,对json数据进行解析、处理.
						var average5Result = [],average10Result = [],average15Result = [];
						
						for ( var o in msg) {
							for ( var ping in msg[o]) {
								
								var temp = [],temp1=[],temp2=[];
								
								temp.push(msg[o][ping].endTime);
								temp.push(msg[o][ping].average5);
								average5Result.push(temp);
								
								temp1.push(msg[o][ping].endTime);
								temp1.push(msg[o][ping].average10);
								average10Result.push(temp1);
								
								temp2.push(msg[o][ping].endTime);
								temp2.push(msg[o][ping].average15);
								average15Result.push(temp2);
								
							}
						}
						
						//如果查询没有结果,给一个0的初始值
						var intiArray = [];
						intiArray.push(0);
						intiArray.push(0);
						
						if(average5Result.length == 0 ){
							average5Result.push(intiArray);
						}
						
						if(average10Result.length == 0 ){
							average10Result.push(intiArray);
						}
						
						if(average15Result.length == 0 ){
							average15Result.push(intiArray);
						}

						$.jqplot('monitor-cpu-average5', [ average5Result ], {
							title : 'CPULoad, Average5.',
							series : [ {label : 'CPULoad, Average5.',neighborThreshold : -1	} ],
							axes : {
								xaxis : {
									renderer : $.jqplot.DateAxisRenderer,
									tickRenderer : $.jqplot.CanvasAxisTickRenderer,
									tickOptions : {	angle : -30,formatString : '%Y-%m-%d %H:%M:%S'}
								},
								yaxis : {
									renderer : $.jqplot.LogAxisRenderer,
									tickOptions : {	suffix : '%'}
								}
							},
							cursor : {show : true,zoom : true	}
						});
						
						$.jqplot('monitor-cpu-average10', [ average10Result ], {
							title : 'CPULoad, Average10.',
							series : [ {label : 'CPULoad, Average10.',neighborThreshold : -1	} ],
							axes : {
								xaxis : {
									renderer : $.jqplot.DateAxisRenderer,
									tickRenderer : $.jqplot.CanvasAxisTickRenderer,
									tickOptions : {	angle : -30,formatString : '%Y-%m-%d %H:%M:%S'}
								},
								yaxis : {
									renderer : $.jqplot.LogAxisRenderer,
									tickOptions : {	suffix : '%'}
								}
							},
							cursor : {show : true,zoom : true	}
						});
						
						$.jqplot('monitor-cpu-average15', [ average15Result ], {
							title : 'CPULoad, Average15.',
							series : [ {label : 'CPULoad, Average15.',neighborThreshold : -1	} ],
							axes : {
								xaxis : {
									renderer : $.jqplot.DateAxisRenderer,
									tickRenderer : $.jqplot.CanvasAxisTickRenderer,
									tickOptions : {	angle : -30,formatString : '%Y-%m-%d %H:%M:%S'}
								},
								yaxis : {
									renderer : $.jqplot.LogAxisRenderer,
									tickOptions : {	suffix : '%'}
								}
							},
							cursor : {show : true,zoom : true	}
						});
						
					}

				});
				
			});
			
			$("#startDate").datetimepicker({
				changeMonth: true,
				onClose: function(selectedDate) {
					$("#endDate").datepicker("option", "minDate", selectedDate);
				}
			});
			
			$("#endDate").datetimepicker({
				changeMonth: true,
				onClose: function(selectedDate) {
					$("#startDate").datepicker("option", "maxDate", selectedDate);
				}
			});

		});
	</script>

</body>
</html>
