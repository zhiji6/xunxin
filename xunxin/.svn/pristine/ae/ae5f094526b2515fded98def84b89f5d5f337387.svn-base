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
        <div class="row"
            <div class="col-sm-12">
                <div class="ibox float-e-margins">

                    <div class="ibox-content">
                        <form id="ArticleRotationForm" name="ArticleRotationForm" class="form-horizontal" method="post">
                            <div class="form-group">
                                <input type="hidden" name="questionID" id="questionID" value="${pd.questionID}" />
                                <label class="col-sm-2 control-label">请输入您要上传的问题</label>
                                <div class="col-sm-4">
                                    <input type="text" id="name" class="form-control required" name="name" style="width:500px;" value="${pd.name}" class="form-control" placeholder="请输入您要编辑的问题" />
                                </div>
                                
                                <label class="col-sm-2 control-label">请选择板块：</label>
                                <div class="col-sm-2">
                                    <select class="form-control" name="sectionName" id="sectionName" value="${pd.sectionName}" 
                                          ajax--url="/api/qa/full_qa_section_list" ajax--column="ID,TEXT" style="width:140px;">
                                          <option value="">--选择板块--</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group" id="linked">
                                <label class="col-sm-2 control-label">请输入你要搜索的外链</label>
                                <div class="col-sm-4">
                                    <input type="text" id="linked_url" class="form-control required" name="linked_url" style="width:500px;" class="form-control" vlaue="${pd.linked_url}" placeholder="请输入你要搜索的外链" />
                                </div>
                                
                                <div class="col-sm-2 col-sm-offset-1 text-left" style="padding-left:260px;">
                                    <button id="btn_Save" class="btn btn-primary" type="button" onclick="spiderUrl();">获取摘要</button>
                                </div>
                            </div>
                            
                            
                            <div class="form-group">
                                <label class="col-sm-2 control-label">请输入您要上传的内容</label>
                                
                                <!-- 加载编辑器的容器 -->  
                                <script id="container" name="container" style="margin: 0px auto;margin-left:294px;" type="text/plain">${pd.content} </script>  
                                   
                            </div>  
                                
                            <div class="form-group">
                                <label class="col-sm-2 control-label">请输入您的观点</label>
                                <div class="col-sm-2" id="answers">
	                                <c:forEach var="item" items="${pd.answers}" varStatus="i">
                                    <input type="text" class="form-control required" id="answer" name="answer" style="width:500px;" class="form-control" value="${item.answer}" />
	                                </c:forEach>    
                                </div>
                                
                                <div class="col-sm-2 col-sm-offset-2 text-right">
                                    <button id="btn_Save" class="btn btn-primary" type="button" onclick="add_answer();">添加观点</button>
                                </div>
                            </div>
                            
                            <div class="form-group">  
                                <div class="form-group">
                                    <div class="col-sm-2 col-sm-offset-3 text-center" style="margin-top:150px">
                                        <button id="btn_Save" class="btn btn-primary" type="button" onclick="submitForm();">确认修改</button>
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
            //初始化下拉菜单
            $("#sectionName").createOption();
            //初始化富文本
            initUedit();
        });
        
        //表单提交
        function submitForm(){
            var questionID = $('#questionID').val();   //问题名称
            var name = $('#name').val();   //问题名称
            var sectionName = $("#sectionName").val();  //板块
            var linked_recordId = $("#linked_recordId").val();     //外链记录ID
            var content = UE.getEditor('container').getContent();
            
            var answers = new Array();
            $("input[name='answer']").each(function(){ 
                answers.push($(this).val());
            }); 
            
            if(linked_recordId != null && linked_recordId != ''){
                $.ajax({
                    type: "POST",
                    url: '<%=basePath%>api/qa/update_qa_to_repertory?tm=' +  new Date().getTime(),
                    data: {
                        "id" : questionID,
                        "name" : name,
                        "sectionName" : sectionName,
                        "images" : images,
                        "linked_recordId" : linked_recordId,
                        "content" : content,
                        "tags" : tags,
                        "answers" : answers,
                    },
                    dataType: 'json',
                    //beforeSend: validateData,
                    cache: false,
                    success: function(data) {
                        if (data.msg == 'success') {
                            var action = "";
                            if(id == ""){
                                action = "hospitalExpertTmp/saveAdd";
                            }else{
                                action = "hospitalExpertTmp/saveEdit";
                            }
                            $(formId).attr("action", action);
                            $(formId).submit();
                        } else {
                            layer.msg(data.error_tip);
                        }
                    }
                });
                
            } else{
                layer.msg("无用户头像不能保存");
            }
            
        }
        
        //返回到列表页面 
        function goBack(){
            this.location.href="<%=basePath%>api/qa/qa_question_manager";
        }
        
        //获取摘要
        function spiderUrl(){
            var linked_url = $("#linked_url").val();
            if(linked_url != null && linked_url != ''){
            $.ajax({
                type: "POST",
                url: '<%=basePath%>api/qa/spider_linked_url?tm=' +  new Date().getTime(),
                data: {"linked_url":linked_url},
                dataType: 'json',
                cache: false,
                success: function(data) {
                    if (data.meta.message == 'ok') {
                        var id = data.data.id;
                        var title = data.data.title;
                        var source = data.data.source;
                        var content = data.data.content;
                        var image = data.data.image;
                        
                        var format_v = "<div class=\"form-group\">"
                            + "<label class=\"col-sm-2 control-label\">" + "Q&A摘要:" + "</label>"
                            + "<div class=\"col-sm-4\" style=\"border:1px black solid;height:318px;margin-left: 12px;border-radius: 10px\" >"
                                + "<input type=\"hidden\" name=\"linked_recordId\" id=\"linked_recordId\" value=\'" + id + "' />"
                                + "<div>"
                                   + "<span class=\"title\" style=\"font-size: 20px;size: 20px; disabled=\"true\"><a>" + title + "</a></span>"
                                + "</div>" 
                                + "<div class=\"img\" style=\"\">"
                                   + "<img alt=\"\" src='" + image + "' disabled=\"true\" style=\"float: right;width: 139px;height: 167px \" />"
                                + "</div>"
                                + "<div>"  
                                   + "<span class=\"content\" disabled=\"true\"><a>" + content + "</a></span>"
                                + "</div>"  
                                + "<div style=\"height: 46px;margin-top:200px;padding-bottom: -10px;\">"
                                   + "<span class=\"source\" disabled=\"true\"><a>" + source + "</a></span>"
                                + "</div>" 
                           + "</div>"  
                       + " </div>";
                       
                    $("#linked").append("<br/><br/><br/>");//添加换行
                    $("#linked").append(format_v);
                    
                    UE.getEditor('container').setContent("[链接]",true);
                                    
                    } else {
                        layer.msg(data.meta.message);
                    }
                }
            });
            
            } else{
                 layer.msg("请输入正确链接");
            }
                
        }
        
        //添加观点
        function add_answer(){
            var format_v = "<input type=\"text\" class=\"form-control required\" id=\"answer\" name=\"answer\" style=\"width:500px;\" class=\"form-control\" placeholder=\"ps:观点\" />";
            $("#answers").append(format_v);
        }
        
        
    </script>
    
    <script type="text/javascript">  
     //初始化Uedit
    function initUedit(){
       //<!-- 实例化编辑器 -->  
        var editor = UE.getEditor('container',{    
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个    
             toolbars: [[
                'fullscreen', 'source', '|', 'undo', 'redo', '|',
                'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                'directionalityltr', 'directionalityrtl', 'indent', '|',
                'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
                'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                'print', 'preview', 'searchreplace', 'drafts', 'help'
            ]],
            //focus时自动清空初始化时的内容    
            autoClearinitialContent:true,    
            //关闭字数统计    
            wordCount:false,    
            //关闭elementPath    
            elementPathEnabled:false,
            zIndex : 9010,
            //initialFramePosition : center,
            initialFrameWidth : "80%",
            //默认的编辑区域高度    
            initialFrameHeight:300    
            //更多其他参数，请参考ueditor.config.js中的配置项    
        });    
        
        
    } 
     
    </script>  
    
    
    
</body>
</html>