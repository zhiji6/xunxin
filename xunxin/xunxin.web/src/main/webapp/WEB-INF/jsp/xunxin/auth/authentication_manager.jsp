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
						<h5>认证信息管理</h5>
					</div>
					<div class="ibox-content">
						<div id="toolbar" class="btn-group">
							<div class="pull-left form-inline form-group">
							    <input type="text" id="queryAuthInfo" name="queryAuthInfo" class="form-control" placeholder="认证信息" />
							    <select class="form-control" name="authType" id="authType" value="" 
                                    ajax--url="api/auth/query_dict?type=authType" ajax--column="ID,TEXT" ajax-- style="width:150px;">
                                    <option value="">--选择认证类型--</option>
                     			</select>
                     			<select class="form-control" name="authState" id="authState"  
                                    ajax--url="api/auth/query_dict?type=authState" ajax--column="ID,TEXT" ajax-- style="width:150px;">
                                    <option value="">--选择审核状态--</option>
                     			</select>
								<button type="button" class="btn btn-default btn-primary" onclick="bstQuery();">
							       	查询
							    </button>
							    <!-- <button type="button" class="btn btn-default btn-primary" onclick="toAdd();">
							       	新增
							    </button>  -->
							    <button type="button" class="btn btn-default btn-primary" onclick="toEdit();">
									修改
							    </button>
							    <!-- <button type="button" class="btn btn-default btn-danger" onclick="toDel();">
							       	 删除
							    </button> -->
							</div>
						</div>
						<table id="queryTable" data-mobile-responsive="true"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="myModal" class="modal inmodal fade" tabindex="-1" role="dialog"  aria-hidden="true"></div>
	<!-- 全局js -->
	<system:jsFooter/>
	<script type="text/javascript">
		//表格ID
		var tableId = "#queryTable";
		//表格请求及数据
		var tableColumns = {
			url: 'api/auth/authentication_manager_list?tm=' + new Date().getTime(),
			toolbar:'#toolbar',
			method:'post', 
			contentType:"application/x-www-form-urlencoded",
			dataType:	"json",
 			sidePagination:"server",
 			undefinedText:'',
 			idField:'id',
 	        responseHandler: function (res) {
 	        	res.rows = res.list;
 	        	delete res.list;
 	        	return res;
 	        }, 
			pagination:"true",
			queryParams:queryParam,
			columns: [{
		        field: 'id',
		        visible: false,
		        halign: 'center'
		    }, {
		        field: 'authInfo',
		        title: '认证信息',
		        align: 'center',
		        halign: 'center',
		        width: '10%',
		        //formatter: formatNAMEFun
		    }, {
		        field: 'authType',
		        title: '认证类型',
		        align: 'center',
		        halign: 'center',
		        width: '20%',
		        formatter:formatType
		    }, {
		        field: 'authState',
		        title: '认证状态',
		        align: 'center',
		        halign: 'center',
		        width: '15%',
		        formatter:formatStatus
		    }, {
		        field: 'authTime',
		        title: '创建时间',
		        align: 'left',
		        halign: 'center',
		        width: '20%',
		        formatter:formatDataTime
		    }, {
		        field: 'extendInfo',
		        title: '附加信息',
		        //visible: false,
		        align: 'center',
		        halign: 'center',
		        width: '15%',
		        formatter: function(value,row,index){
                        return '<img  src="'+row.extendInfo+'" class="img-responsive" >';
                }
		    }, {
		        field: 'authRemark',
		        title: '备注',
		        align: 'center',
		        halign: 'center',
		        width: '10%'
		    }, {
		        field: 'userId',
		        visible: false,
		        align: 'center',
		        halign: 'center',
		        width: '10%'
		    }, {
		        field: 'nickName',
		        title: '用户',
		        align: 'center',
		        halign: 'center',
		        width: '10%'
		    }],
		};
		$(document).ready(function (){
			
/* 			var pd = "${pd}";
			console.info(pd);
			var msg = "${msg}";
			console.info(msg);
			if(msg != null && msg != ""){
				if(msg == '200'){
					layer.msg("成功编辑认证信息管理", {time:3000});
				} else if(msg == 'successEdit'){
					layer.msg("成功编辑认证信息管理");
				}else{
					layer.msg("代理商认证信息管理编辑失败！");
				}
			} */
			//初始化下拉菜单
			$("#authType").createOption();
			$("#authState").createOption();
			//初始化及查询生成表格内容
			table = $(tableId).bootstrapTable(tableColumns);
		});
		//查询刷新表格
		function searchRefreshTable(){
			//销毁表格
			$(tableId).bootstrapTable('destroy');
			$(tableId).bootstrapTable(tableColumns);
		}
		//导出Excel
		function toExport(){
			$(tableId).bootstrapTable('exportTable', {
				type : 'excel'
			});
		}
		//跳转到新增页面
		function toAdd(){
			window.location.href = "<%=basePath%>agent/toAdd";
		}
		//跳转到编辑页面
		function toEdit(){
			var id = $('input:checkbox[name="btSelectItem"]:checked').val();
			if(id == null){
				layer.msg('请只选中一条信息再进行编辑。');
				return false;
			}
			window.location.href = '<%=basePath%>api/auth/authentication_manager_to_edit?SP_ID='+id;
		}
		//批量删除数据
		function toDel(){
			var ids = getBstCheckedId('SP_ID');
			if((ids.length < 1)){
				layer.msg('请选中信息再进行删除。');
				return false;
			}
			var idsStr = ids.toString();
			layer.confirm('确定删除已选信息吗？', {
				btn: ['确认','取消'],
				shade: false,
				yes: function(index, layero){
					$.ajax({
						type: "POST",
						url: '<%=basePath%>agent/toDelete?tm=' +  new Date().getTime(),
						data: {
							IDS: idsStr
						},
						dataType: 'json',
						//beforeSend: validateData,
						cache: false,
						success: function(data) {
							if (data.msg == 'success') {
								layer.msg('删除信息成功');
								bstQuery();
							} else {
								layer.msg('删除信息失败');
							}
						}
					});
				}
			});
		}
		//浏览
		function toView(SP_ID){
			if(SP_ID != null && SP_ID != ""){
				layer.full(layer.open({
					type: 2,
					title: '信息浏览',//窗体标题
					area: ['600px', '600px'],//整个窗体宽、高，单位：像素px
					fix: false,//窗体位置不固定
					maxmin: true,//最大、小化是否显示
					scrollbar: true,//整体页面滚动条是否显示
					content: ['/agent/toView?SP_ID=' + SP_ID, 'no'],//URL地址
					closeBtn: 1,//显示关闭按钮
					btn: ['关闭']
				}));
			}else{
				layer.msg("系统未获取到数据主键，请重新选择数据！");
			}
		}
		//操作
		function formatNAMEFun(value, row, index){
			var format_v = "<button type=\"button\" class=\"btn btn-link\" onclick=\"toView('"+row.SP_ID+"');\">" + row.SP_NAME + "</button>";
			return format_v;
		}
		//时间
		function formatDataTime(value, row, index){
			var d = new Date(row.authTime);  
			if(d != null){
				var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-')   
	            + '  ' + [ d.getHours(), d.getMinutes(), d.getSeconds() ].join(':');  
	    		return dformat;
			}else{
				return"";
			}
		}
		//认证状态
		function formatStatus(value, row, index){
			var d = '';  formatType
		    if(row.authState == 0){ d = '未审核'; }
		    if(row.authState == 1){ d = '审核通过'; }
		    if(row.authState == 2){ d = '驳回处理'; }
		    if(row.authState == 3){ d = '其他'; }
		    return d;  
		}
		//认证类型
		function formatType(value, row, index){
			var d = '';  
		    if(row.authType == 'phone'){ d = '手机认证'; }
		    else if(row.authType == 'mail'){ d = '邮箱'; }
		    else if(row.authType == 'sesame'){ d = '芝麻分认证'; }
		    else if(row.authType == 'education'){ d = '学历认证'; }
		    else if(row.authType == 'profession'){ d = '职业认证'; }
		    else if(row.authType == 'cert'){ d = '实名认证'; }
		    else {d = row.authType;}
		    return d;  
		}
		// 格式化数据
		var formatData = function (data) {
			var l = [];
			for (var i = 0; i < data.list.length; i++) {
			          var m = data.list[i]
			var d = {
			'id': m.id,
			'authInfo': m.authInfo,
			}
			l.push(d)
			}
			return l
		};
		//查询
	    function queryParam(params){  
	        var param = {  
	                limit : this.limit, // 页面大小  
	                offset : this.offset, // 页码  
	                pageNumber : this.pageNumber,  
	                pageSize : this.pageSize,  
	                authInfo : $("#queryAuthInfo").val(),
	                authType: $("#authType").val(),
	                authState: $("#authState").val()
	        };  
	        return param;  
	    }
		//获取选中的值
		function getBstCheckedId(param){  
		     var value =""; 
		     alert($('input:checkbox[name="'+param+'"]:checked').val());
		       /* $('inputcheckbox[name="'+param+'"]:checked').each(function(){
		    	   alert($(this).val());
		    	   value+=$(this).val();
		      
		       }); */  
		     return value;
		    } 
	</script>
</body>
</html>