<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>循心</title>
  <!-- Favicon -->
<link rel="shortcut icon" href="assets/img/favicon.ico" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Day Spa Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<link href="assets/css/bootstrap.min.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<link rel="stylesheet" href="assets/css/flexslider.css" type="text/css" media="screen" />
<script src="assets/js/jquery-1.11.1.min.js"></script>
<!--/script-->
<script type="text/javascript">
			jQuery(document).ready(function($) {
			var num=1;
			setInterval(function(){
			if(num===1){
				$("#img1").attr("style","");
				$("#img2").attr("style","display:none;");
				$("#img3").attr("style","display:none;");
			}
			if(num===2){
				$("#img1").attr("style","display:none;");
				$("#img2").attr("style","");
				$("#img3").attr("style","display:none;");
			}
			if(num===3){
				$("#img1").attr("style","display:none;");
				$("#img2").attr("style","display:none;");
				$("#img3").attr("style","");
			}
			if(num===4){
				$("#img1").attr("style","");
				$("#img2").attr("style","display:none;");
				$("#img3").attr("style","display:none;");
				num=1;
			}
			num=num+1;
			},"5000");
			
			});
</script>
</head>
<body>
	<!--start-main-->
	<div class="banner" id="home">
		<div class="banner-slider">
			<ul class="slides">
				<li data-thumb="images/1.jpg"><img id="img1" src="assets/img/1.jpg"
					style="" /></li>
				<li data-thumb="images/2.jpg"><img id="img2" src="assets/img/2.jpg"
					style="display: none;" /></li>
				<li data-thumb="images/3.jpg"><img id="img3" src="assets/img/3.jpg"
					style="display: none;" /></li>
			</ul>
		</div>

	</div>
</body>
</html>
