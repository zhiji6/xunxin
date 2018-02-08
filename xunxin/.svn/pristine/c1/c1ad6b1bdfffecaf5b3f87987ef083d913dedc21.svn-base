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

<style type="text/css">
#top{
    margin: auto;margin-top:10px;
    border: 1px soild black;
}
#middle{
    border: 1px soild black;
}
#middle img{
    width: 100%;height: 99%;
    border-radius:10px 10px 0px 0px; 
    box-shadow:10px 10px 20px gold; 
    cursor: pointer;
}
body{
    background-color: #7fffd4;
}
pre{
    color: #a52a2a;
    font-size: 21px;font-family: "楷体";
}
#word1{
    width: 40px;height: 200px;
    float: left;
    margin-left: 20px;
}
#word2{
    width: 40px;height: 200px;
    float: left;
}
#pc1{
    width: 80px;height: 100px;
    margin-left: 40px; margin-top: 20px;
}
#border1{
    position: absolute;border:2px solid red;  
}
</style>


</head>
<body class="gray-bg">

    <div id="top">
        <div id="middle" class="col-sm-12">
        
        </div>
    </div>
    
    <!-- 全局js -->
    <system:jsFooter/>
    <!-- 自定义js -->
    <script type="text/javascript">
	(function() {
	        
	        $("").ready(function() {
	            var index=0;
	            var imgs=new Array();
	            
	                imgs.push("assets/img/1.jpg");
	                imgs.push("assets/img/2.jpg");
	                imgs.push("assets/img/3.jpg");
	        
	                $(imgs).each(function(idx,ob) {
	                    
	                    
	                    $("#bottom").append("<img index='"+idx+"' src='"+ob+"'>");
	                
	                    $("#bottom img:last").click(function() {
	                        
	                        $("#firstimg").stop();
	                        
	                        index=parseInt($(this).attr("index"));
	                        
	                        var nextX=$($("#bottom img").get(index)).offset().left;
	                        $("#border1").animate({"left":nextX+"px"},200);
	                        
	                        tm1= window.setTimeout(function(){
	                         
	                            showImg(); 
	                         }, 5000);
	                
	                    });
	                
	                
	                });
	                
	                $("#middle").append("<img id='firstimg' src='"+imgs[0]+"'>");
	                
	                showImg();
	                
	                function showImg() {
	                    
	                    $("#firstimg").fadeOut(2000,function(){
	                        index++
	                        if(index==imgs.length){
	                            index=0;
	                        }
	                        $("#firstimg").attr("src",imgs[index]);
	                        $("#firstimg").fadeIn(2000,function(){
	                            
	                            showImg();
	                            
	                        });
	                        
	                    });
	                    
	                    
	                }
	        
	        });
	        
	    })();
    </script>  
    
    
    
</body>
</html>