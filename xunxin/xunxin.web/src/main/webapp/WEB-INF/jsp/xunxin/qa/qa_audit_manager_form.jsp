﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<style>
</style>
<head>

<system:header/> 
</head>
 <body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>QA审核</h5>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal inmodal fade" tabindex="-1" role="dialog"  aria-hidden="true"></div>
		<form action="api/auth/authentication_manager_examine" method="post" enctype="application/x-www-form-urlencoded" style="margin:0 0 3cm 4cm">
		  <div class="form-group">
		    <label >问题名称</label>
		    <input type="text" class="form-control Readonly" id="name" placeholder="问题名称" style="width:50%;" Readonly value="${question.name}">
		  	<input type="hidden" name="id" value="${question.id}">
		  </div>
		  <div class="form-group">
		  	<label >所属板块</label>
		    <input type="text" class="form-control Readonly" id="typeName" placeholder="所属板块" style="width:50%;" Readonly value="${question.typeName}">
		    <input type="hidden" name="type" value="${question.type}">
		  </div>
		  <div class="form-group">
		  	 <label >发布人</label>
		    <input type="text" class="form-control Readonly" id="nickName" placeholder="发布人" style="width:50%;" Readonly value="${question.nickName}">
		  </div>
          <div class="form-group">
              <div>
              <label >Q&A内容：</label>
              </div>
                  <textarea type="text" class="form-control required" name="qa_content" id="qa_content" disabled="true" style="width:800px;height:250px">${question.content}</textarea>     
          </div>
		  <div class="form-group">
		  	<label >标题</label>
		    <input type="text" class="form-control Readonly" id="title" placeholder="标题" style="width:50%;" Readonly value="${link.title}">
		  </div>
		  <div class="form-group">
		  	<label >超链接</label>
		    <a  href="${link.linked_url}">${link.source}</a>
		  </div>
          <div class="form-group">
              <label >Q&A图片：</label>
              <div class="col-sm-4 gallerys" style="width:1200px;border:0;display:inline-block;">
                  <c:forEach items="${imgs}" var="items" varStatus="is">
                      <img class="gallery-pic" alt="" src="${items.url}" style="width:300px;max-width: 300px;max-height: 300px;float:left" ondblclick="$.openPhotoGallery(this)">    
                  </c:forEach> 
              </div>
          </div>
		  <div class="form-group">
		  	<div>
		  		<label >观点</label>
		  	</div>
		  		<c:forEach items="${answers }" var="item">
		  			 <input type="text" class="form-control Readonly"  placeholder="观点" style="width:50%;" Readonly value="${item.answer}">
		  		</c:forEach>
		  </div>
		  <div class="form-group">
		  	<label >标签</label>
		  		<c:forEach items="${tags }" var="item">
		  			 <input type="text" class="form-control Readonly"  placeholder="观点" style="width:50%;" Readonly value="${item.tagName}">
		  		</c:forEach>
		  </div>
          <div class="form-group">
              <label >Q&A收藏数：</label>
              <div >
                  <input type="text" class="form-control required" name="collectionCount" id="collectionCount" value="${collectionCount}"  disabled="true" style="width:5%;"/>
              </div>
              <label >Q&A已选数：</label>
              <div >
                  <input type="text" class="form-control required" name="selectCount" id="selectCount" value="${selectCount}"  disabled="true" style="width:5%;"/>
              </div>
          </div>
		  <div class="form-group">
		  <label >备注</label>
		    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="备注" style="width:50%;" Readonly value="${question.remark}">
		    <div>
		    	<select class="form-control not_through" name="not_through" id="notThroughSelect" value="" 
                        ajax--url="api/auth/query_dict?type=not_through" ajax--column="ID,TEXT" ajax-- style="width:150px;">
                        <option value="">--选择审核不通过原因--</option>
         		</select>
		    </div>
		    <textarea id="" class="form-control not_through" rows="3" maxlength="50"  style="resize:none; width:600px; height:100px;"></textarea>
		  </div>
		  <button type="button" class="btn btn-default">通过</button>
		  <button type="button" class="btn btn-warning">不通过</button>
		  <button type="button" class="btn btn-danger not_through">确认不通过</button>
		  <button type="button" class="btn btn-default nextAnswer">下一题</button>
		</form>

	<!-- 全局js -->
	<system:jsFooter/>

</head>
<body>
<script type="text/javascript">
		$(document).ready(function (){
			//$('#editor').wysiwyg();
			$("#notThroughSelect").createOption();
			$(".not_through").hide();
			//$(".nextAnswer").hide();
			
			$("button").click( function () { 
				var type = $(this).html();
				//不通过
				if(type != null && type == '不通过'){
					$(".not_through").show();
					$("#notThroughSelect").val("");
					$("textarea").val('');
					$(this).hide();
				}
				//通过
				else if(type != null && type == '通过'){
					$(".not_through").hide();
					$(".btn-warning").show();
					var id = $("input[name='id']").val();
					var status =1; 
					var type = $("input[name='type']").val();
					if(id == null || id == '' || status == null || status == ''){
						layer.msg('数据加载出错。');
						return false;
					}
					audit(id,status,null,type);
				}
				else if(type != null && type == '确认不通过'){
					var id = $("input[name='id']").val();
					var status =2; 
					var type = $("input[name='type']").val();
					var content = $("textarea").val();
					if(id == null || id == '' || status == null || status == ''|| content == null || content ==''){
						layer.msg('数据加载出错。');
						return false;
					}
					audit(id,status,content,type);
				}
				else if(type != null && type == '下一题'){
					nextQuestion();
				}
				
			});
			
			$("#notThroughSelect").change( function() {
				$("textarea").val('');
				$("textarea").val($(this).find("option:selected").text())
				});
			//富文本
			//initUedit();
		});
		//审核方法
		function audit(id,status,content,type){
			$.ajax({
				type: "POST",
				contentType:"application/x-www-form-urlencoded",
				url: '<%=basePath%>api/qa/qa_audit_manager_examine?tm=' +  new Date().getTime(),
				data: {
					id: id,
					status:status,
					content:content,
					type:type
				},
				dataType: 'json',
				//beforeSend: validateData,
				cache: false,
				success: function(data) {
					if (data.data == 'OK') {
						layer.msg('审核成功');
						nextQuestion();
					} else {
						layer.msg(data.meta.message);
					}
				}
			}); 
		}
		//下一题
			function nextQuestion() {
				var notId = $("input[name='id']").val();
				var type = $("input[name='type']").val();
				if(notId == null || notId == '' || type == null || type == ''){
					layer.msg('数据加载出错。');
					return false;
				}
					 window.location.href='<%=basePath%>api/qa/qa_audit_manager_to_examine?notId='+notId+'&type='+type;
			}
</script>
</body>
</html>