<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#allmap {width:100%;height:100%;font-family:"微软雅黑";}
#showAnnouncement{box-shadow: 10px 10px 5px #888888;top:15%;left:15%;position:fixed;z-index:111;width:70%;height:70%;display: none;border:1px solid #0080ff;background:#fff;}
</style>

	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ab6TdFaqCmUCKAL0oTCLc1te0keLaIOX"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils_min.js"></script>

<link href="lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />

<title>我的桌面</title>
</head>
<body>


	<div class="pd-20"  style="width: 60%;">
	    <table class="table table-border table-bordered table-bg table-hover">
	        <thead>
	            <tr>
	                <th>
		                <span class="f-l">&nbsp;<i style="font-size: 18px;" class="Hui-iconfont">&#xe671;</i>&nbsp;首页总览</span>
		                <span class="f-r"><a href="bikesMap.do" href="javascript:void(0)">进入»»</a></span>
	                </th>
	            </tr>
	        </thead>
	        <tbody>
	                <tr>
	                    <td style="height: 512px;margin: 0;padding: 0;">    
		                    <!-- 百度地图 -->
							<div id="allmap">
							
							
							</div>
							
						</td>                   
					</tr>
	        </tbody>
	    </table>
	</div>




	<div class="" style="width: 36%;height: 573px;position: fixed;right: 20px;top: 20px;">
	    <div class="">	        
	        <table class="table table-border table-bordered table-bg table-hover">
		        <thead>
		            <tr>
		                <th>
			                <span class="f-l">&nbsp;<i style="font-size: 18px;" class="Hui-iconfont">&#xe63b;</i>&nbsp;待办事项</span>
			                <span class="f-r"><a href="bikesMap.do" href="javascript:void(0)">更多»»</a></span>
		                </th>
		            </tr>
		        </thead>
	        </table>

	        <div style="border: 1px #ddd solid;height: 220px;">	                                    	
				<div style="margin: 20px;margin-top: 30px;">
	
				</div>
				<div style="margin: 20px;">
				<!--  公告 -->
				
				</div>
			</div>
	    </div>

	    <div class="" style="margin-top: 8px;">
	        <table class="table table-border table-bordered table-bg table-hover">
		        <thead>
		            <tr>
		                <th>
			                <span class="f-l">&nbsp;<i style="font-size: 18px;" class="Hui-iconfont">&#xe62f;</i>&nbsp;公告通知</span>
			                <span class="f-r"><a href="announcementList.do" href="javascript:void(0)">更多»»</a></span>
		                </th>
		            </tr>
		        </thead>
	        </table>
	        <div style="border: 1px #ddd solid;height: 232px;padding-top: 10px;">	        	                                    	
				<c:forEach var="announcements" items="${announcements }">
					<div>
						<a href="javascript:void(0);" onclick="showAnnouncement(${announcements.id })" style="margin-left: 10px;">${announcements.title.length()>25?announcements.title.substring(0,25):announcements.title }</a>
						<span class="f-r"  style="margin-right:10px;"><fmt:formatDate value="${announcements.createDatetime }" type="both" pattern="yyyy-MM-dd"/></span>
					</div>
            	</c:forEach>						
	        </div>
	    </div>
	</div>

<div id="showAnnouncement">
        <div class="row cl" >        
            <div class="formControls col-5"  id="announcementValue" style="width:99.5%;overflow-y:scroll;height:450px;margin: 5px;">            	       
			</div>
        </div>
        <div align="center" ><button class="btn btn-default radius" onclick="closer()">关闭</button></div>
</div>		


    <footer class="footer mt-20">
    	<div class="container-fluid">
    		<nav> <a href="#" target="_blank">关于我们</a> <span class="pipe">|</span> <a href="#" target="_blank">联系我们</a> <span class="pipe">|</span> <a href="#" target="_blank">法律声明</a> </nav>
    		<p>Copyright &copy;2016 H-ui.net All Rights Reserved. <br>
    			<a href="http://www.miitbeian.gov.cn/" target="_blank" rel="nofollow">京ICP备1000000号</a><br>
    		</p>
    	</div>
    </footer>


<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script>
<script type="text/javascript" src="js/bikesMap.js"></script>
<script type="text/javascript">
function showAnnouncement(value){	
	jQuery.post("showAnnouncement.do",{id:value},function(data) {
		jQuery("#showAnnouncement").css({"display":"block"});
		jQuery("#announcementValue").html(data);
		});
}

function closer(){
		jQuery("#showAnnouncement").css({"display":"none"});
	}
</script>
</body>
</html>