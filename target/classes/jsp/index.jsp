<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%
	String root= "http://localhost:8080/"+request.getContextPath()+"/";
%>



<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>主页</title>
	<link rel="stylesheet" href="<%=root%>asset/css/ht-sty.css">
	<link rel="stylesheet" href="<%=root%>asset/js/dateSelect.css">
	<link rel="stylesheet" href="<%=root%>asset/layui/css/layui.css"  media="all">
</head>

<body class="bodybg4">
<div class="hearer-i clearfloat">
	<div class="nbox">
		<div class="logo"><!-- <img src="images/logo.png"> -->豆度</div>
		<!--  
		<div class="right-con">
			<a href="#"><img src="images/top-ico1.png">自定义按钮</a>
		</div>
		-->
	</div>
</div>
<!--内容页-->
<div class="liuc-con">

	<div class="search_box">
		<div class="juzhongbox">
			<div class="ss_con">
				<input type="text" class="s_txt" placeholder="输入信息搜索" value="" id="search_str"/>
				<button class='s_btn' onclick="search()">查询</button>
			</div>
		</div>
	</div>
	<div class="liuc_tabbox">
		<div class="tab_top">
			<a href="javascript:showUl('one11','1')" id="one1" class="ved">电影</a>
			<a href="javascript:showUl('one22','2')" id="one2">演员</a>
		</div>
		<input type="hidden" id="search_type" value="1">
		<div class="tab_bom" id="search_result_content">
		</div>
		<div id="search_result_page"></div>
	
	</div>
	
</div>

<div class="foot">一个木有感情的搜索引擎</div>
</body>
</html>
<script src="<%=root%>asset/js/jquery-1.8.3.min.js"></script> 
<script type="text/javascript" src="<%=root%>asset/js/dateSelect.js"></script>	
<script type="text/javascript">
		$("#date").dateSelect();
		$("#dateend").dateSelect();
		function showUl(ulid,type){//tab点击处理
			activepage=1;
			var tabid=ulid.substring(0,4);//当期击中的tabid
			var tbidsub03=ulid.substring(0,3);//tabid相同的前3位
			ulidinit=ulid;
			for(var i=1;i<3;i++){
				if(tbidsub03+i!=tabid){//未击中去除样式，隐藏内容
					$("#"+tbidsub03+i).attr("class","");
				}else{//击中添加样式，显示内容
					$("#"+tbidsub03+i).attr("class","ved");
				}
			}
			$("#search_type").val(type);
			search();
		}
	
</script>



<script src="<%=root%>asset/layui/layui.all.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
function search(){
	var s=$("#search_str").val().trim();
	if(s==""){
		return;
	}
	var type=$("#search_type").val();
	var url="<%=root%>/film/searchFilm";
	if(type=='2')url="<%=root%>/film/searchActor";
	layui.use(['layer', 'form', 'laypage'], function () {
		var layer = layui.layer
		var form = layui.form;
		var laypage = layui.laypage;
			$.ajax({
				url : url,
				type : "post",
				data:{search_str:s,start:0,limits:15},
				dataType: 'json',
				success : function(res) {
					if(res.count==0){
						$('#search_result_content').html("");
					}
					laypage.render({
						elem : 'search_result_page',
						count : parseInt(res.count),
						limit : 15,
						jump : function(obj) {
							var pageIndex = obj.curr;
							load_page_data(pageIndex);
						}
					});
				},

				error:function(xhr,state,errorThrown){
					alert(xhr.responseText);
				}
			});
		});
}

	function load_page_data(pageIndex) {
		var s=$("#search_str").val();
		var start=15*(pageIndex-1);
		var type=$("#search_type").val();
		var url="<%=root%>/film/searchFilm";
		if(type=='2')url="<%=root%>/film/searchActor";
		$.ajax({
			url : url,
			type : "post",
			data:{search_str:s,start:start,limits:15},
			dataType: 'json',
			success : function(res) {
						var html = "<ul id=\"one11\">";
						if(type=='1'){
							$.each(res.list,function(i, film) {
								html+="<li>"
								html+="<h3>"+film.film_name+"  | 导演："+film.director+"</h3>";
								html+="<p>"+film.film_summary+"</p> <span>上映日期 ："+film.film_date;
								html+="	｜ 评分 ："+film.score+" ｜ 类型："+film.film_type+" | 语言："+film.lang+" </span>"
								html+="</li>"
							})
						}else{
							$.each(res.list,function(i, actor) {
								html+="<li>"
								html+="<h3>"+actor.actor_name+"  | 职业："+actor.occupation+"</h3>";
								html+="<p> 代表作："+actor.main_works+"</p> <span>性别 ："+actor.gender;
								html+="	｜ 星座 ："+actor.xingzuo+" ｜ 生日："+actor.birthday+" | 地区："+actor.birtharea+" </span>"
								html+="</li>"
							})							
						}
						html+="</ul>";
						$('#search_result_content').html(html);
						},

			error:function(xhr,state,errorThrown){
				alert(xhr.responseText);
			}
		});
	}
</script>