<%@ page language="java" contentType="text/html; charset=UTF-8"
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

					<div class="ibox-content">
						<form id="ArticleRotationForm" name="ArticleRotationForm" class="form-horizontal" method="post">
							<input type="hidden" name="id" id="id" value="${pd.id}" />
							<div class="form-group">
								<label class="col-sm-2 control-label">请输入您要上传的问题</label>
								<div class="col-sm-4">
									<input type="text" id="name" class="form-control required" name="name" style="width:500px;" class="form-control" placeholder="请输入您要编辑的问题，需要替换的部分请用&代替。例:你喜欢&的电影吗？" />
								</div>
								
								<label class="col-sm-2 control-label">请选择板块：</label>
								<div class="col-sm-2">
									<select class="form-control" name="sectionName" id="sectionName" value="${pd.sectionName}" 
						                  ajax--url="/api/qa/full_qa_section_list" ajax--column="ID,TEXT" style="width:140px;">
						                  <option value="">--选择板块--</option>
						            </select>
					            </div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">请输入您的观点</label>
								<div class="col-sm-2">
									<input type="text" class="form-control required" id="answer1" name="answer1" style="width:500px;" class="form-control" placeholder="ps:非常喜欢" />
									<input type="text" class="form-control required" id="answer2" name="answer2" style="width:500px;" class="form-control" placeholder="ps:喜欢" />
									<input type="text" class="form-control required" id="answer3" name="answer3" style="width:500px;" class="form-control" placeholder="ps:一般" />
									<input type="text" class="form-control required" id="answer4" name="answer4" style="width:500px;" class="form-control" placeholder="ps:不喜欢" />
									<input type="text" class="form-control required" id="answer5" name="answer5" style="width:500px;" class="form-control" placeholder="ps:厌恶" />
								</div>
								
								<div class="form-group">
									<div class="col-sm-2 col-sm-offset-3 text-center" style="margin-top:150px">
										<button id="btn_Save" class="btn btn-primary" type="button" onclick="submitForm();">导入</button>
										<button id="btn_Cancel" class="btn btn-white" type="button" onclick="goBack();">取消</button>
									</div>
								</div>  
							</div>
							
							
							<div class="form-group">
								<div class="col-sm-6">
									<div class="form-group">
										<label class="col-sm-12 control-label"><p class="text-center">选择Excel</p></label>
									</div>
									
									<div class="form-group">
										<div class="col-sm-12">
											<input id="inp" type="file" name="file" multiple class="file-loading">
											<input type="hidden" name="HEL_PHOTO" id="HEL_PHOTO" value="${pd.HEL_PHOTO }">
										</div>
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
	<!-- 附件上传 
	<script src="hplus/js/plugins/webuploader/webuploader_pegasus.js"></script>-->
	
	<script type="text/javascript">
		//表单ID
		var formId = "#ArticleRotationForm";
		$(document).ready(function(){
			//初始化下拉菜单
			$("#sectionName").createOption();
		});
		
		var IDSAndPid=[];
		var ids;
		var MASTER_ID=234;//master_id(参数是举例，请根据实际传参)
		var initFileName="QA感兴趣的电影.xls" //初始化照片名(参数是举例，请根据实际传参)
		var initFilePath="D:\\\\20170222\\d8ed7fbd4a9b445cb446f7d727579eb2.jpg"//初始化照片路径(参数是举例，请根据实际传参)
		var initFileID= "d8ed7fbd4a9b445cb446f7d727579eb2"//初始化照片id，如果多的话可设置为数组(参数是举例，请根据实际传参)
	    $("#inp").fileinput({
	    	language: 'zh', //设置语言
	    	browseClass: "btn btn-primary btn-block",
	        showCaption: true,//是否在选择按钮旁边显示文件名
	        resizeImage: false,
	        maxImageWidth: 50,
	        maxImageHeight: 50,
	        resizePreference: 'height',
	    	showUpload:true,//是否展示上传按钮
	    	//showRemove:false,//是否展示移除按钮
	    	//showClose:false,//是否展示关闭按钮
	    	//showDelete: false,//是否显示删除图标
	    	showUploadedThumbs:false,//上传完成后是否显示缩略图
	    	uploadUrl:'/api/qa/upload_excel', //上传的url
	    	uploadAsync: false,//异步支持
	    	showUploadedThumbs:false,
	    	//previewFileType: ["text","image"],//文件類型
	    	//allowedFileExtensions: ["txt", "md", "ini", "text",'jpg','png'],//允许上传的文件扩展名
	    	overwriteInitial: true,
	        initialPreviewAsData: true,
	    	initialPreview: [//初始化预览
	    		'http://www.xunxinkeji.cn/hplus/img/qa.png'
	        ],
	        initialPreviewConfig: [//初始化设置 {标题 宽度 删除的url 是否顯示刪除 顺序  扩展字符}
	           {caption: initFileName, width: "120px",url:'/filesUploads/delete',showDelete: false ,showZoom: false,key:1,extra: {id: '20e3a27be3d84329833c200772d21a0b'}}        
	        ],
	        uploadExtraData:[//上传时附加的参数额外的参数，注意，此处jquery无效。
	           { "qaName":name,"qaSectionName":sectionName}
	        ], 
	        //maxFileCount: 10, //表示允许同时上传的最大文件个数
	        //showCaption: false,//是否显示标题   
	        dropZoneEnabled: false,//是否显示拖拽区域
	        //minImageWidth: 50, //图片的最小宽度
	        //minImageHeight: 50,//图片的最小高度
	        //maxImageWidth: 1000,//图片的最大宽度
	        //maxImageHeight: 1000,//图片的最大高度
	        //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
	        //minFileCount: 0,
	        //enctype: 'multipart/form-data',
	        //validateInitialCount:true,
	    }).on('fileselect', function(event, numFiles, label) {//选择文件时
	    	//$(this).fileinput("upload");//选择完文件后立即上传
	    })
	    .on('fileuploaded', function(event, data, previewId, index) {//文件上传成功后
	    	var json=JSON.parse(data.jqXHR.responseText)[0];
	    	var IDS=json.id;	
			var PATH=json.path;//文件路径
	 	})
	 	.on('filebatchuploadsuccess', function(event, data, previewId, index) {//批量上传成功 必须:uploadAsync: false
	 		console.log('File batch upload success');
	 		ids=JSON.parse(data.jqXHR.responseText)
	 		console.log(ids)
	 	})
	 	.on('filebatchuploadcomplete', function(event, files, extra) {//批量上传成功完成
	    console.log('File batch upload complete');
	    
		})
	 	.on('fileremoved', function(event, id) {
	// 		var hasGotTemp=getTemp(id,IDSAndPid);
	// 		if(hasGotTemp==null){
	// 			alert("没有找到对应的值")
	// 		}else{
	// 			var IDS=hasGotTemp
	 //		    $.ajax({
	// 		        url:'/filesUploads/delete',
	// 		        type:'post',
	 //		        dataType:'json',
	 //		        data:{"IDS":IDS},
	 //		        success:function(){
	 //		        	console.log('remove has success!');
	 //		        	var willRemoveTemp=removeTemp(id,IDSAndPid);
	 //		        	if(willRemoveTemp==null){
	 //		        		alert("没有找到对应的值1")
	// 		        	}else{
	 //		        		IDSAndPid.del(willRemoveTemp);
	 //		        	}	
	 //		        },
	 //		        error:function(){
	 //		        	console.log('remove has error!')
	 //		   	    }
	 //		    })
	 //		}
		console.log("file removed")
		})
		.on('filedeleted', function(event, id) {//删除预加载的文件
	        console.log("has filedeleted")
		})
		.on('filecleared', function(event){//清空的事件
			//var IAPL=IDSAndPid.length;
			//循环删除
			//for(var i=0;i<IAPL;i++){
			//	var willDelete=IDSAndPid[i].value;
			//    $.ajax({
	 		 //       url:'/filesUploads/delete',
	 		 //       type:'post',
	 		//        dataType:'json',
	 		//        data:{"IDS":willDelete},
	 		//        success:function(){
	 		//        	console.log('clear has success!');
	 		//        	var willRemoveTemp=removeTemp(id,IDSAndPid);
	 		//        	if(willRemoveTemp==null){
	 		//        		alert("没有找到对应的值2")
	 		//        	}else{
	 		//        		IDSAndPid.del(willRemoveTemp);
	 		//        	}	
	 		//        },
	 		//        error:function(){
	 		 //       	console.log('clear has error!')
	 		//   	    }
	 		//    })
	 		for(var i=0;i<ids.length;i++){
	 			console.log(ids[i].id)
	 			    $.ajax({
	 	 		        url:'/filesUploads/delete',
	 	 		        type:'post',
	 	 		        dataType:'json',
	 	 		        data:{"IDS":ids[i].id},
	 	 		        success:function(){
	 	 		        	console.log('clear has success!');
	 	 		        	//var willRemoveTemp=removeTemp(id,IDSAndPid);
	 	 		        	//if(willRemoveTemp==null){
	 	 		        	//	alert("没有找到对应的值2")
	 	 		        	//}else{
	 	 		        	//	IDSAndPid.del(willRemoveTemp);
	 	 		        	//}	
	 	 		        },
	 	 		        error:function(){
	 	 		        	console.log('clear has error!')
	 	 		   	    }
	 	 		    })
	 		}
			
			//清空数组
			//for(var i=0;i<IAPL;i++){
				//IDSAndPid.del[0]
			//}
		})
		//找array中json的key对应的value。找到返回value，找不到为null
		function getTemp(id,array){
			console.log(id)
			for(var i=0;i<array.length;i++){
				console.log('循环次数'+i)
				if(array[i].id !=null && array[i].id !='undefined' && array[i].id !=''){
					console.log("结果"+array[i][id])
					return array[i][id]
				}
			}
			return null;
		}
		//找到要删除元素的下标,找到返回下标，找不到为null
		function removeTemp(id,array){
			var time=-1;
			for(var i=0;i<array.length;i++){
				if(array[i].id!=null && array[i].id!='undefiend' && array[i].id!=''){
					time=i
				}
			}
			if(time==-1){
				return null;
			}
			return time;
		}
		//返回到列表页面 
		function goBack(){
			this.location.href="<%=basePath%>api/qa/qa_question_manager";
		}
		//给arry增加根据下标删除方法
		Array.prototype.del = function(dx) { 
			if(isNaN(dx)||dx>this.length){return false;} 
			this.splice(dx,1); 
		} 
		//表单提交
	    function submitForm(){
	        var name = $('#name').val();
	        var sectionName = $("#sectionName").val();
	        var answer1 = $('#answer1').val();
	        var answer2 = $('#answer2').val();
	        var answer3 = $('#answer3').val();
	        var answer4 = $('#answer4').val();
	        var answer5 = $('#answer5').val();
	        
	        var answers = new Array();
	        if(answer1 != null && answer1 != ''){
	        	answers[0] = answer1;
	        }
	        if(answer2 != null && answer2 != ''){
	        	answers[1] = answer2;
	        }
	        if(answer3 != null && answer3 != ''){
	        	answers[2] = answer3;
	        }
	        if(answer4 != null && answer4 != ''){
	        	answers[3] = answer4;
	        }
	        if(answer5 != null && answer5 != ''){
	        	answers[4] = answer5;
	        }
	        
	        var results = JSON.stringify(answers);
	        if(answers != null && answers != ''){
	           	$.ajax({
					type: "POST",
					url: '<%=basePath%>api/qa/import_excel_database?tm=' +  new Date().getTime(),
					data: {"name":name,"sectionName":sectionName,"answers":results},
					dataType: 'json',
					//beforeSend: validateData,
					cache: false,
					success: function(data) {
						if (data.meta.message == 'ok') {
							layer.msg("导入成功");
							alert("导入成功");;
						} else {
							layer.msg(data.error_tip);
						}
					}
				});
	        	
	        } else{
	        	layer.msg("答案为空不能提交");
	        }
	    	
	    }
		
		
		
	</script>
	
</body>
</html>
