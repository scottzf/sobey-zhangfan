<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>
<title>RootPartition Demo</title>

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
					placeholder="开始时间" value="2014-01-15 08:30">
			</div>
		</div>
		
		<div class="form-group">
			<label for="endDate" class="col-sm-2 control-label">结束时间</label>
			<div class="col-sm-2">
				<input type="text" class="form-control datepicker" readonly="readonly" id="endDate" name="endDate"
					placeholder="结束时间" value="2014-01-15 12:30">
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="button" class="btn btn-primary">Search</button>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<div id="monitor-rootPartition-freeSpace" style="height: 200px; width: 600px;"></div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<div id="monitor-rootPartition-freePer" style="height: 200px; width: 600px;"></div>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<div id="monitor-rootPartition-inode" style="height: 200px; width: 600px;"></div>
			</div>
		</div>
		
		
	</form>

	<script>
		$(document).ready(function() {
			
			$(document).on("click","button",function(){
			 
				$.ajax({
					type : "POST",
					url : "${ctx}/monitor/rootPartition/",
					dataType : "json",
					data : {
						ipaddress: $("#ipaddress").val() ,
						startDate: $("#startDate").val() ,
						endDate: $("#endDate").val() 
					},
					success : function(msg) {
						
						//为满足jqplot数据格式,对json数据进行解析、处理.
						var freeSpaceResult = [],freePerResult = [],inodeResult = [];

						for ( var o in msg) {
							for ( var ping in msg[o]) {
								
								var temp = [],temp1=[],temp2=[];
								
								temp.push(msg[o][ping].endTime);
								temp.push(msg[o][ping].freeSpace);
								freeSpaceResult.push(temp);
								
								temp1.push(msg[o][ping].endTime);
								temp1.push(msg[o][ping].freePer);
								freePerResult.push(temp1);
								
								temp2.push(msg[o][ping].endTime);
								temp2.push(msg[o][ping].inode);
								inodeResult.push(temp2);
								
							}
						}
						 
						//如果查询没有结果,给一个0的初始值
						var intiArray = [];
						intiArray.push(0);
						intiArray.push(0);
						
						if(freeSpaceResult.length == 0 ){
							freeSpaceResult.push(intiArray);
						}
						
						if(freePerResult.length == 0 ){
							freePerResult.push(intiArray);
						}
						
						if(inodeResult.length == 0 ){
							inodeResult.push(intiArray);
						}
						

						$.jqplot('monitor-rootPartition-freeSpace', [ freeSpaceResult ], {
							title : 'RootPartition, FreeSpace.',
							series : [ {label : 'RootPartition, FreeSpace.',neighborThreshold : -1	} ],
							axes : {
								xaxis : {
									renderer : $.jqplot.DateAxisRenderer,
									tickRenderer : $.jqplot.CanvasAxisTickRenderer,
									tickOptions : {	angle : -30,formatString : '%Y-%m-%d %H:%M:%S'}
								},
								yaxis : {
									renderer : $.jqplot.LogAxisRenderer,
									tickOptions : {	suffix : 'MB'}
								}
							},
							cursor : {show : true,zoom : true	}
						});
						
						$.jqplot('monitor-rootPartition-freePer', [ freePerResult ], {
							title : 'RootPartition, FreePer.',
							series : [ {label : 'RootPartition, FreePer.',neighborThreshold : -1	} ],
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
						
						$.jqplot('monitor-rootPartition-inode', [ inodeResult ], {
							title : 'RootPartition, Inode.',
							series : [ {label : 'RootPartition, Inode.',neighborThreshold : -1	} ],
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
