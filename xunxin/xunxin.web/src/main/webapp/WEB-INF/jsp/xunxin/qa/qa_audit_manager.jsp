﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
						<h5>系统平台信息</h5>
					</div>
					<div class="ibox-content">
						<div id="toolbar" class="btn-group">
							<div class="pull-left form-inline form-group">
							<input type="text" id="indexNo" name="indexNo" class="form-control" placeholder="时间序列号" />
							    <select class="form-control" name="sectionName" id="sectionName" 
                                    ajax--url="api/qa/full_qa_section_list" ajax--column="TYPE,TEXT" style="width:140px;">
                                    <option value="">--选择板块--</option>
                     			</select>
								<button type="button" class="btn btn-default btn-primary" onclick="bstQuery();">
							       	查询
							    </button>
							   <!--  <button type="button" class="btn btn-default btn-primary" onclick="toAdd();">
							       	新增
							    </button> -->
							    <button type="button" class="btn btn-default btn-primary" onclick="toEdit();">
									审核
							    </button>
							   <!--  <button type="button" class="btn btn-default btn-danger" onclick="toDel();">
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
				url: 'api/qa/qa_audit_manager_list?tm=' + new Date().getTime(),
				toolbar:'#toolbar',
				method:'post', 
				contentType:"application/x-www-form-urlencoded",
				dataType:	"json",
	 			sidePagination:"server",
	 			undefinedText:'',
	 			idField:'id',
				pagination:"true",
				queryParams:queryParam,
			columns: [{
		        field: 'id',
		        visible: false,
		        halign: 'center'
		    }, {
		        field: 'indexNo',
		        title: 'QA序号',
		        align: 'center',
		        halign: 'center',
		        width: '10%',
		    },{
		        field: 'name',
		        title: '问题名称',
		        align: 'center',
		        halign: 'center',
		        width: '25%',
		        formatter: formatNAMEFun
		    }, {
		        field: 'typeName',
		        title: '板块',
		        align: 'center',
		        halign: 'center',
		        width: '10%',
		    },{
		        field: 'type',
		        visible: false,
		        halign: 'center'
		    }, {
		        field: 'userID',
		        visible: false,
		        halign: 'center'
		    },{
		        field: 'nickName',
		        title: '发布人',
		        align: 'center',
		        halign: 'center',
		        width: '10%'
		    },{
		        field: 'createTime',
		        title: '创建时间',
		        //visible: false,
		        align: 'center',
		        halign: 'center',
		        width: '15%',
		        formatter:formatDataTime
		    }, {
		        field: 'remark',
		        title: '备注',
		        align: 'center',
		        halign: 'center',
		        width: '25%'
		    }, {
		        field: 'status',
		        title: '话题状态',
		        //visible: false,
		        align: 'center',
		        halign: 'center',
		        width: '5%',
		        formatter:formatStatus
		    }]
		};
		$(document).ready(function (){
		 /* 	var rows = "${rows}";
			console.info(rows);
			if(rows == null && rows == []){
					layer.msg("没有数据");
			}  */
			//初始化下拉菜单
			$("#sectionName").createOption();
			//初始化及查询生成表格内容
			table = $(tableId).bootstrapTable(tableColumns);
		});
		//查询
	    function queryParam(params){
			var type = $("#sectionName option:selected").val();
			if(type=='undefined'){
		        var param = {  
		                limit : this.limit, // 页面大小  
		                offset : this.offset, // 页码  
		                pageNumber : this.pageNumber,  
		                pageSize : this.pageSize 
		        };
		        return param; 
			}else{
				var param = {  
		                limit : this.limit, // 页面大小  
		                offset : this.offset, // 页码  
		                pageNumber : this.pageNumber,  
		                pageSize : this.pageSize,  
		                typeQuery : type,
		                indexNo : $("#indexNo").val()
		        };
		        return param;
			}
  
	        
	    }
		//查询刷新表格
		function searchRefreshTable(){
			//销毁表格
			$(tableId).bootstrapTable('destroy');
			$(tableId).bootstrapTable(tableColumns);
		}

		//跳转到新增页面
		function toAdd(){
			window.location.href = "<%=basePath%>api/qa/qa_add";
		}
		//跳转到编辑页面
		function toEdit(){
			var obj = getBstCheckedId('btSelectItem');
			var ids = obj.split(",");
			if(ids == '' || ids.length > 2){
				layer.msg('请只选中一条信息再进行编辑。');
				return false;
			}
			window.location.href = "<%=basePath%>api/qa/qa_audit_manager_to_examine?id="+ids[0];
		}
		//跳转到编辑页面
		function toView(id){
			if(id == null && id == '' ){
				layer.msg('数据有误。');
				return false;
			}
			window.location.href = "<%=basePath%>api/qa/qa_audit_manager_to_examine?id="+id;
		}


		//时间
		function formatDataTime(value, row, index){
			var d = new Date(row.createTime);  
		    var dformat = [ d.getFullYear(), d.getMonth() + 1, d.getDate() ].join('-')   
		            + '  ' + [ d.getHours(), d.getMinutes(), d.getSeconds() ].join(':');  
		    return dformat;  
		}
		//话题状态 	/**	 * 问题状态: * 	0:未审核  1:审核通过 2:驳回处理 3:其他  	 */
		function formatStatus(value, row, index){
			var d = '';  
		    if(row.status == 0){ d = '未审核'; }
		    if(row.status == 1){ d = '审核通过'; }
		    if(row.status == 2){ d = '驳回处理'; }
		    if(row.status == 3){ d = '其他'; }
		    return d;  
		}
		
		//操作
		function formatNAMEFun(value, row, index){
			var format_v = "<button type=\"button\" class=\"btn btn-link\" onclick=\"toView('"+row.id+"');\">" + row.name + "</button>";
			return format_v;
		}
		function formatType(value, row, index){
			var d = '';  
		    if(row.type == 1){ d = '科技'; }
		    if(row.type == 2){ d = '审核通过'; }
		    if(row.type == 3){ d = '娱乐'; }
		    if(row.type == 4){ d = '体育'; }
		    if(row.type == 5){ d = '其他'; }
		    if(row.type == 6){ d = '其他'; }
		    if(row.type == 7){ d = '其他'; }
		    if(row.type == 8){ d = '其他'; }
		    if(row.type == 9){ d = '生活'; }
		    if(row.type == 10){ d = '音乐'; }
		    if(row.type == 11){ d = '人文'; }
		    if(row.type == 12){ d = '自然'; }
		    if(row.type == 13){ d = '军事'; }
		    if(row.type == 14){ d = '影视'; }
		    return d;  
		}
		//获取选中的值
		function getBstCheckedId(param){  
		     var value =""; 
		        $('input:checkbox[name="'+param+'"]:checked').each(function(){
		    	   value+=$(this).val()+",";
		       });
		     return value;
		    } 
	</script>
</body>
</html>