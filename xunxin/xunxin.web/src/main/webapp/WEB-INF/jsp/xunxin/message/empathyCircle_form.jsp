﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<style>
 #editor {overflow:scroll; max-height:300px}
</style>

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
						<h5>共情圈管理</h5>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal inmodal fade" tabindex="-1" role="dialog"  aria-hidden="true"></div>
		<form action="api/auth/authentication_manager_examine" method="post" enctype="application/x-www-form-urlencoded" style="margin:0 0 3cm 4cm">
		  <div class="form-group">
		    <label >用户名称</label>
		    <input type="text" class="form-control Readonly" id="name" placeholder="用户" style="width:50%;" Readonly value="${empathyCircle.nickName}">
		  	<input type="hidden" name="id" value="${empathyCircle.id}">
		  </div>
		  <div class="form-group">
		  	<label >内容</label>
		    <input type="text" class="form-control Readonly" id="content" placeholder="内容" style="width:50%;" Readonly value="${empathyCircle.content}">
		  </div>
		  <div class="form-group">
		  	<label >地址</label>
		    <input type="text" class="form-control Readonly" id="content" placeholder="内容" style="width:50%;" Readonly value="${empathyCircle.address}">
		  </div>
		  <div class="form-group" > 
		  	<label >图片</label>
		  		<c:forEach items="${empathyCircle.photos}" var="item">
		  			<img src="${item.url}" class="img-rounded"  style="width:100px;height:100px;">
		  		</c:forEach>
		  </div>
		  <div class="form-group" id="reply">
		  	<label >回复</label>
		  		<c:forEach items="${empathyCircle.circleComment}" var="item">
		  			<div>
		  			 <span class="label label-default">${item.nickName}</span>回复<span class="label label-default">${item.reployName}</span>${item.content}<input type="hidden"  value="${item.id}">
		  			 <button type="button" class="btn btn-danger">删除</button>
		  			 </div>
		  		</c:forEach>
		  </div>
		  <button type="button" class="btn  circle">删除共情圈</button>
		  <button type="button" class="btn  circle">返回</button>
	</form>
	<!-- 全局js -->
	<system:jsFooter/>
<script type="text/javascript">
		//删除留言
		$(".btn-danger").click( function () { 
		 	var id = $(this).parent().find("input").val(); 
			$.ajax({
				type: "POST",
				contentType:"application/x-www-form-urlencoded",
				url: '<%=basePath%>api/message/empathy_circle_reply_delete?tm=' +  new Date().getTime(),
				data: {
					id: id,
				},
				dataType: 'json',
				cache: false,
				success: function(data) {
					if (data.data == 'OK') {
						layer.msg('删除成功');
						//$(".btn-danger").parent().find("input:[value='"+id+"']").html('');
						 $("#reply").find("input[value='"+id+"']").parent().html('');
					} else {
						layer.msg('删除失败');
					}
				}
			});
		});
		$(".circle").click( function () { 
			var type =  $(this).html();
			if(type != null && type == '删除共情圈'){
			 	var id = $("input[name='id']").val(); 
				$.ajax({
					type: "POST",
					contentType:"application/x-www-form-urlencoded",
					url: '<%=basePath%>api/message/qa_audit_manager_examine?tm=' +  new Date().getTime(),
					data: {
						id: id,
					},
					dataType: 'json',
					cache: false,
					success: function(data) {
						if (data.data == 'OK') {
							layer.msg('删除成功');
							history.go(-1);
						} else {
							layer.msg('删除失败');
						}
					}
				});
			}
			else if(type != null && type == '返回'){
				history.go(-1);
			}

		});
</script>
</body>
</html>