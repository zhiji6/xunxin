<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<system:header/>
<!-- jsp文件头和头部 -->
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">

					<div class="ibox-content">
						<form id="ArticleRotationForm" name="ArticleRotationForm" class="form-horizontal" method="post">
							<div class="form-group">
								<label class="col-sm-2 control-label">接口名称</label>
								<div class="col-sm-4">
									<input type="text" name="api_name" class="form-control required" id="api_name" value="${api_name}" placeholder="接口名称"/>
								</div>
								
								<label class="col-sm-2 control-label">请选择模块：</label>
								<div class="col-sm-2">
									 <select class="form-control" name="api_model" id="api_model" value="${api_model}" style="width:140px;">
	                                    <option value="">--接口模块--</option>
	                                    <option value="0">--APP--</option>
	                                    <option value="1">--BASE--</option>
	                                    <option value="2">--WEB--</option>
	                     			</select>
					            </div>
							</div>
							    
							<div class="form-group">
								<label class="col-sm-2 control-label">接口Url</label>
								<div class="col-sm-4">
									<input type="text" name="api_url" class="form-control required" id="api_url" value="${api_url}" placeholder="接口路径"/>
								</div>
							
								<label class="col-sm-2 control-label">API的状态</label>
								<div class="col-sm-2">
									 <select class="form-control" name="api_state" id="api_state" value="${api_state}" style="width:140px;">
	                                    <option value="">--API的状态--</option>
	                                    <option value="0">--不可用--</option>
	                                    <option value="1">--可用--</option>
	                                    <option value="2">--废弃--</option>
	                     			</select>
					            </div>
							</div>
							
							<div class="form-group">
								<div class="form-group">
									<div class="col-sm-2 col-sm-offset-3 text-center" style="margin-top:150px">
										<button id="btn_Save" class="btn btn-primary" type="button" onclick="submitForm();">添加</button>
										<button id="btn_Cancel" class="btn btn-white" type="button" onclick="goBack();">取消</button>
									</div>
								</div>  
							</div>
							
							
						</form>
					</div>
				</div>
			</div>	
		</div>
	</div>
	
	<!-- 全局js -->
    <system:jsFooter/>
	<!-- 自定义js -->
	<script type="text/javascript">
		//表单ID
			var formId = "#ArticleRotationForm";
			$(document).ready(function(){
				//changePcode();
			});
			
	    	//初始化下拉菜单
	    	$("#api_model").createOption();
	    	$("#api_state").createOption();
	    	
		//表单提交
	    function submitForm(){
	    	var api_name = $("#api_name").val();
			var api_model = $("#api_model").val();
			var api_url = $("#api_url").val();
			var api_state = $("#api_state").val();
			
           	$.ajax({
				type: "POST",
				url: '<%=basePath%>api/system/add_api_document?tm=' +  new Date().getTime(),
				data: {
					"api_name": api_name,
					"api_model":api_model,
					"api_url":api_url,
					"api_state":api_state
				},
				dataType: 'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data) {
					if (data.meta.message == 'ok') {
						layer.msg("添加成功");
					} else {
						layer.msg(data.error_tip);
					}
				}
			});
           	
	    }
		
	    //返回到列表页面 
		function goBack(){
			this.location.href="<%=basePath%>api/system/system_api_manager";
		}
		
		
		
		
		
	</script>
	
    </script>  
	
	
	
</body>
</html>