<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html lang="en">

<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .el-table .warning-row {
            background: oldlace;
        }

        .el-table .success-row {
            background: #f0f9eb;
        }
    </style>
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="query-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="query-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control" type="text" id="query-startDate" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control" type="text" id="query-endDate">
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="queryActivityBtn">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="createActivityBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
                <button type="button" class="btn btn-default" id="editActivityBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
                <button type="button" class="btn btn-danger" id="deleteActivityBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
            </div>
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
            </div>
        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="chckAll"/></td>
                    <td>番号</td>
                    <td>标题</td>
                    <td>字幕</td>
                    <td>高清</td>
                    <td>女优</td>
                    <td>类别</td>
                    <td>日期</td>
                </tr>
                </thead>
                <tbody id="tBody">

                </tbody>
				</table>
				<div id="demo_pag1"></div>
    </div>



</div>

</div>
</body>

<script src="js/vue.js"></script>
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<!-- 引入组件库 -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script src="js/axios-0.18.0.js"></script>

<script>
    $(function(){
        queryActivityByConditionForPage();
    });
    function queryActivityByConditionForPage() {
        //收集参数
        var name=$("#query-name").val();
        var owner=$("#query-owner").val();
        var startDate=$("#query-startDate").val();
        var endDate=$("#query-endDate").val();
        //var pageNo=1;
        //var pageSize=10;
        //发送请求
        $.ajax({

            url:'porndo_magnet_war_exploded/porndos.do',
            data:{
                name:name,
                owner:owner,
                startDate:startDate,
                endDate:endDate,

            },
            type:'post',
            dataType:'json',
            success:function (data) {
                alert("牛的比")
                //显示总条数
                //$("#totalRowsB").text(data.totalRows);
                //显示市场活动的列表
                //遍历activityList，拼接所有行数据
                var htmlStr="";
                $.each(data.magnet_models,function (index,obj) {
                    htmlStr+="<tr class=\"active\">";
                    htmlStr+="<td><input type=\"checkbox\" value=\""+obj.num+"\"/></td>";
                    htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.num+"'\">"+obj.num+"</a></td>";
                    htmlStr+="<td>"+obj.title+"</td>";
                    htmlStr+="<td>"+obj.subline+"</td>";
                    htmlStr+="<td>"+obj.HD+"</td>";
                    htmlStr+="<td>"+obj.actress+"</td>";
                    htmlStr+="<td>"+obj.types+"</td>";
                    htmlStr+="<td>"+obj.date+"</td>";
                    htmlStr+="</tr>";


                });
                $("#tBody").html(htmlStr);
            }
        });
    }

</script>


</html>