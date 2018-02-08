﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<system:header/>
<!-- jsp文件头和头部 -->
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>认证信息管理修改</h5>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal inmodal fade" tabindex="-1" role="dialog"  aria-hidden="true"></div>
		<form id="authentication" action="api/auth/authentication_manager_examine" method="post" enctype="application/x-www-form-urlencoded" style="margin:0 0 3cm 4cm">
		  <div class="form-group">
		    <label for="exampleInputEmail1">认证信息</label>
		    <input type="text" class="form-control Readonly" id="exampleInputEmail1" placeholder="认证信息" style="width:50%;" Readonly value="${authentication.authInfo}">
		  	<input type="hidden" name="id" value="${authentication.id}">
		  </div>
		  <div class="form-group">
		    <label for="exampleInputPassword1">认证类型</label>
		     <select class="form-control" name="authType" id="authType" value="" disabled="disabled"
                                    ajax--url="api/auth/query_dict?type=authType" ajax--column="ID,TEXT" ajax-- style="width:150px;" Readonly>
                                    <option value="" >--选择认证类型--</option>
             </select> 
		  </div>
		  <div class="form-group">
		  <label for="exampleInputEmail1">认证状态</label>
		    <select class="form-control" name="authState" id="authState"  
                                      ajax-- style="width:150px;">
        							<option value="0">--审核通过--</option>
        							<option value="1">--审核不通过--</option>
            </select>
		  </div>
		  <div class="form-group">
		  <label for="exampleInputEmail1">附加信息</label>
		    <img src="http://p2.ifengimg.com/a/2017_50/8ddee390f9c2127_size41_w690_h460.jpg" class="img-responsive" alt="图片不存在" style="width:300px;height:300px;">
		  </div>
		  <div class="form-group">
		  <label for="exampleInputEmail1">备注</label>
		    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="备注" style="width:50%;" Readonly value="${authentication.authRemark}">
		  </div>
		   <div class="form-group">
		  <label for="exampleInputEmail1">用户名</label>
		    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="用户名" style="width:50%;" Readonly value="${authentication.nickName}">
		  </div>
		  <button type="submit" class="btn btn-default">审核通过</button>
		  <button type="submit" class="btn btn-default">驳回处理</button>
		</form>

	<!-- 全局js -->
	<system:jsFooter/>
<script type="text/javascript">
		$(document).ready(function (){
				$("#authType").createOption();
				$("#authType option[value='${authentication.authType}']").attr("selected","selected"); 
				$("#authState option[value='${authentication.authState}']").attr("selected","selected"); 
				$(".btn-default").click( function () { 
					var type =  $(this).html();
					if(type != null && type == '审核通过'){
						$("#authState").find("option[value='0']").attr("selected",true);
					}
					else if(type != null && type == '驳回处理'){
						$("#authState").find("option[value='1']").attr("selected",true);
					}
					$("#authentication").submit();
				});
			
			
			
			});
</script>
</body>
</html>