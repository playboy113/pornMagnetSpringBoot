<%--
  Created by IntelliJ IDEA.
  User: 10460
  Date: 2022/12/4
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
</head>
<body>
<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
<div class="form-group">
    <div class="input-group">
        <div class="input-group-addon">链接</div>
        <input class="form-control" type="text" id="query-url">
    </div>
    <div class="input-group">
        <div class="input-group-addon">页数</div>
        <input class="form-control" type="text" id="query-pages">
    </div>



</div>
    <button type="button" class="btn btn-default" id="querySearchBtn">爬！！</button>
</form>
<button type="button" id="frontPageBtn" onclick=window.location.href='index.do'>返回主页</button>
</body>
<script>
    $(function (){
        $("#querySearchBtn").click(function(){
            var magnetUrl = $("#query-url").val();
            var pages = $("#query-pages").val();
            $.ajax({
                url:'crawerMagnet.do',
                data:{
                    magnetUrl:magnetUrl,
                    pages:pages
                },
                type:'post',
                dataType:'json',
                success:function(data){
                    alert(data.result)
                }
            })
        })
        $("#")
    })
</script>
</html>
