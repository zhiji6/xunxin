﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<style>
 form {
 	margin:0 0 3cm 4cm
 }
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
						<h5>用户管理体系修改</h5>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal inmodal fade" tabindex="-1" role="dialog"  aria-hidden="true"></div>
		<form action="api/auth/authentication_manager_examine" method="post" enctype="application/x-www-form-urlencoded" style="margin:0 0 3cm 4cm">
		  <div class="form-group">
		    <label for="exampleInputEmail1">用户名</label>
		    <input type="text" class="form-control Readonly" id="exampleInputEmail1" placeholder="用户名" style="width:50%;" Readonly value="${userRelation.nickName }">
		  	<input type="hidden" name="userId" value="${userRelation.userId }" id="userId">
		  </div>
		  <div class="form-group">
		    <label for="exampleInputPassword1">好友列表</label>
		     <select class="form-control" name="friendId" id="friends" value="" style="width:20%;">
                                    <option value="" >--选择认好友--</option>
                                    <c:forEach items="${userRelation.friendList }" var="item">
	                                    <c:if test="${item.nickName != null }">
	                                    	<option value="${item.friendId }" >--${item.nickName }--</option>
	                                    </c:if>
                                    </c:forEach>
             </select>
             <button type="button" class="btn btn-danger">移除好友</button> 
		  </div>
		  <div class="form-group">
		    <label for="exampleInputPassword1">关注列表</label>
		     <select class="form-control" name="friendId" id="follows" value="" style="width:20%;">
                                    <option value="" >--选择关注的人--</option>
                                    <c:forEach items="${userRelation.followList }" var="item">
	                                    <c:if test="${item.nickName != null}">
	                                    	<option value="${item.friendId }" >--${item.nickName }--</option>
	                                    </c:if>
                                    </c:forEach>
             </select>
             <button type="button" class="btn btn-danger">移除关注的人</button>  
		  </div>
		  <div class="form-group">
		    <label for="exampleInputPassword1">屏蔽的人列表</label>
		     <select class="form-control" name="friendId" id="shields" value="" style="width:20%;">
                                    <option value="" >--选择屏蔽的人--</option>
                                    <c:forEach items="${userRelation.shieldList }" var="item">
	                                    <c:if test="${item.nickName != null}">
	                                    	<option value="${item.shieldId }" >--${item.nickName }--</option>
	                                    </c:if>
                                    </c:forEach>
             </select>
             <button type="button" class="btn btn-danger">移屏蔽的人</button>  
		  </div>
		  <div class="form-group">
		    <label for="exampleInputPassword1">举报的人列表</label>
		     <select class="form-control" name="friendId" id="reports" value="" style="width:20%;">
                                    <option value="" >--选择举报的人--</option>
                                    <%-- <c:forEach items="${userRelation.reportList }" var="item">
                                        <c:if test="${item.reportName != null}">
                                       	<option value="${item.reportId }" >--${item.reportName }--</option>
                                       </c:if>
                                    	
                                    </c:forEach> --%>
             </select>
             <button type="button" class="btn btn-danger">移除举报的人</button>  

		  </div>

		  <button  id="toBack" class="btn btn-default">返回</button>
		</form>

	<!-- 全局js -->
	<system:jsFooter/>
<script type="text/javascript">
		$(document).ready(function (){
			//重构用户体系
			$(".btn-danger").click( function () {
				var idType = $(this).prev().attr('id');
				var id = $(this).prev().val();
				if(id == null || id ==''){
					layer.msg('请只选中一条信息。');
					return false;
				}else{
					$.ajax({
						   type: "POST",
						   dataType: 'json',
						   url: 'api/info/easemob_users_manager_restructure?tm=' + new Date().getTime(),
						   data: {
								userId: $("#userId").val(),
								id :id,
								idType: idType
							},
						   success: function(data){
							   if(data != null && data == 'OK'){
									layer.msg('操作成功。');
									$("#"+idType).find("option:selected").remove();
								}else{
									layer.msg('操作失败。');
								}
						   }
						});
				}
			});
			$("#toBack").click( function () {
				window.location.href = "<%=basePath%>info/easemob_users_manager";
			});
		});
</script>
</body>
</html>