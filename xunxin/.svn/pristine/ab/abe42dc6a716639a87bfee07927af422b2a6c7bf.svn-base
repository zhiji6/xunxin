<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <div class="wrapper wrapper-content animated fadeInRight" >
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>Q&A名称: ${pd.name}</h5>
                    </div>
                    <div class="ibox-content">
                        <form id="qaDetailsForm" name="qaDetailsForm" class="form-horizontal" method="post">
                            <input type="hidden" name="id" id="id" value="${pd.id}" />
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Q&A名称：</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control required" name="name" id="name" value="${pd.name}"  disabled="true" style="width:800px;"  />
                                </div>
                            </div>
                            
                            <c:if test="${pd.linked_recordId != null && pd.linked_recordId != '' }">
                                 <div class="form-group">
                                    <label class="col-sm-2 control-label">Q&A摘要：</label>
                                    <div class="col-sm-4" style="border:1px black solid;width:690px;height:318px;margin-left: 12px;border-radius: 10px" >
                                        <div style="width:520px;height:30px;float:left">
                                            <span class="title" style="text-align:center;display:inline-block;width:500px;overflow:hidden;" disabled="true"><a style="font-size:25px;width:520px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;color:#ff6699;font-family:楷体;">${pd.title}</a></span>
                                        </div>
                                        <div class="img" style="">
                                            <img alt="" src="${pd.image}" disabled="true" style="float: right;width:139px;height: 167px">
                                        </div>
                                        <div style="width:520px;height:227px;padding-top:30px;">
                                            <span class="content" disabled="true"><a style="color:black;font-size:15px;overflow:hidden;text-overflow:ellipsis;display:-webkit-box;-webkit-line-clamp:10;-webkit-box-orient: vertical;">${pd.content}</a></span>
                                        </div>
                                        <div style="height:46px;padding-top:20px;padding-bottom:10px;">
                                            <span class="source" disabled="true" style="padding-left:50px"><a style="font-size:30px;font-family:楷体;">${pd.source}</a></span>
                                        </div>
                                    </div>
                                 </div>
                            </c:if>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Q&A内容：</label>
                                <div class="col-sm-4">
                                    <textarea type="text" class="form-control required" name="qa_content" id="qa_content" disabled="true" style="width:800px;height:250px">${pd.qa_content}</textarea>     
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Q&A图片：</label>
                                <div class="col-sm-4" style="width:1200px;border:0;display:inline-block;">
                                    <c:forEach items="${pd.imgs}" var="items" varStatus="is">
                                        <img alt="" src="${items.url}" style="width:300px;float:left">    
                                    </c:forEach> 
                                </div>
                            </div>
                            
                            <div class="hr-line-dashed"></div>   <!--  分割线 -->
                            <c:forEach items="${pd.answers}" var="item" varStatus="i">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Q&A观点 ${i.index}： </label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control required" name="answer" id="answer" value="${item.answer}"  disabled="true" style="width:800px;"  />
                                </div>
                            </div>
                            </c:forEach>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">用户性别：</label>
                                <div class="col-sm-2" style="word-break:break-all;">
                                    <input type="text" class="form-control required" name="gender" id="gender" value="${pd.gender}"  disabled="true" />
                                </div>
                                <label class="col-sm-2 control-label">用户昵称：</label>
                                <div class="col-sm-2" style="word-break:break-all;">
                                    <input type="text" class="form-control required"style="word-break:break-all;" name="nickName" id="nickName" value="${pd.nickName}"  disabled="true" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">Q&A收藏数：</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control required" name="collectionCount" id="collectionCount" value="${pd.collectionCount}"  disabled="true" />
                                </div>
                                <label class="col-sm-2 control-label">Q&A已选数：</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control required" name="selectCount" id="selectCount" value="${pd.selectCount}"  disabled="true" />
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
        $(document).ready(function(){
            console.info("${pd.linked_recordId}");
        });
    </script>  
    
    
    
</body>
</html>