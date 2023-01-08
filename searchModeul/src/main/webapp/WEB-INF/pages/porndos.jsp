<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%--样式属性--%>
<div id="outerdiv" style="position: fixed; top: 0; left: 0; background: rgba(0,0,0,0.7); z-index: 2; width: 100%; height: 100%; display: none;">
    <div id="innerdiv" style="position: absolute;">
        <img id="bigimg" style="border: 5px solid #fff;" src="" />
    </div>
</div>

<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">番号</div>
                        <input class="form-control" type="text" id="query-num">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">标题</div>
                        <input class="form-control" type="text" id="query-title">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">女优</div>
                        <input class="form-control" type="text" id="query-actress">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">HD</div>
                        <input class="form-control" type="text" id="query-HD" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">中文字幕</div>
                        <input class="form-control" type="text" id="query-subline">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">类别</div>
                        <input class="form-control" type="text" id="query-types">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">片商</div>
                        <input class="form-control" type="text" id="query-producer">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">日期</div>
                        <input class="form-control" type="text" id="query-date">
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
                <button id="copyMagnetsAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 批量复制</button>
                <button id="searchPageBtn" type="button" class="btn btn-default" onclick=window.location.href='searchPage.do'><span class="glyphicon glyphicon-export"></span> 跳转搜索页</button>
            </div>
        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="chckAll"/></td>
                    <td>缩略图</td>
                    <td>番号</td>
                    <td>标题</td>
                    <td>字幕</td>
                    <td>高清</td>
                    <td>女优</td>
                    <td>类别</td>
                    <td>片商</td>
                    <td>日期</td>


                    <td>磁链</td>

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
        queryActivityByConditionForPage(1,10);



        $("#queryActivityBtn").click(function () {
            //查询所有符合条件数据的第一页以及所有符合条件数据的总条数;
            queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
        });
        $("#chckAll").click(function () {
            //如果"全选"按钮是选中状态，则列表中所有checkbox都选中
            /*if(this.checked==true){
                $("#tBody input[type='checkbox']").prop("checked",true);
            }else{
                $("#tBody input[type='checkbox']").prop("checked",false);
            }*/

            $("#tBody input[type='checkbox']").prop("checked",this.checked);
        });

        $("#copyMagnetsAllBtn").click(function (){
            var checkedNums = $("#tBody input[type='checkbox']:checked");
            if (checkedNums.size()==0){
                alert("请选择要复制的番号！！");
                return;
            }
            var nums = "";
            $.each(checkedNums,function (){
                nums +=this.value+"&";
            });

            nums =nums.substr(0,nums.length-1);

            $.ajax({
                url:'queryMagnetsByNums.do',
                data:{
                    nums:nums
                },
                type:'post',
                dataType:'json',
                success:function (data){
                    const clipboardObj = navigator.clipboard;
                    clipboardObj.writeText(data.retStr);
                    alert("复制成功")
                }
            })

        })

    });
    function queryActivityByConditionForPage(pageNo,pageSize) {
        //收集参数
        var title=$("#query-title").val();
        var actress=$("#query-actress").val();
        var subline=$("#query-subline").val();
        var HD=$("#query-HD").val();
        var num=$("#query-num").val();
        var types=$("#query-types").val();
        var date=$("#query-date").val();
        var producer=$("#query-producer").val();

        //var pageNo=1;
        //var pageSize=10;
        //发送请求
        $.ajax({
            url:'porndos.do',
            data:{
                title:title,
                actress:actress,
                subline:subline,
                HD:HD,
                num:num,
                types:types,
                date:date,
                producer:producer,
                pageNo:pageNo,
                pageSize:pageSize

            },
            type:'post',
            dataType:'json',
            success:function (data) {

                //显示总条数
                //$("#totalRowsB").text(data.totalRows);
                //显示市场活动的列表
                //遍历activityList，拼接所有行数据
                var htmlStr="";
                $.each(data.magnet_models,function (index,obj) {
                    htmlStr+="<tr class=\"active\">";
                    htmlStr+="<td><input type=\"checkbox\" value=\""+obj.num+"\"/></td>";
                    htmlStr+="<td> <img src='./images/"+obj.num+".jpg' width='100' height='75' onclick='Big(this)'></td>";
                    htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.num+"'\">"+obj.num+"</a></td>";
                    htmlStr+="<td>"+obj.title+"</td>";
                    htmlStr+="<td>"+obj.subline+"</td>";
                    htmlStr+="<td>"+obj.HD+"</td>";
                    htmlStr+="<td>"+obj.actress+"</td>";
                    htmlStr+="<td>"+obj.types+"</td>";
                    htmlStr+="<td>"+obj.producer+"</td>";
                    htmlStr+="<td>"+obj.date+"</td>";

                    // htmlStr+="<td> <button type='button' onclick=\"window.location.href='copyMagnet.do?num=ekw-077'\">磁链</td>";
                    htmlStr+="<td> <button type='button' onclick='copyMagnet(this)' value=\""+obj.num+"\">磁链</td>";
                    htmlStr+="</tr>";



                });
                $("#tBody").html(htmlStr);
                //计算总页数
                var totalPages=1;
                if(data.totalRows%pageSize==0){
                    totalPages=data.totalRows/pageSize;
                }else{
                    totalPages=parseInt(data.totalRows/pageSize)+1;
                }

                //对容器调用bs_pagination工具函数，显示翻页信息
                $("#demo_pag1").bs_pagination({
                    currentPage:pageNo,//当前页号,相当于pageNo

                    rowsPerPage:pageSize,//每页显示条数,相当于pageSize
                    totalRows:data.totalRows,//总条数
                    totalPages: totalPages,  //总页数,必填参数.

                    visiblePageLinks:5,//最多可以显示的卡片数

                    showGoToPage:true,//是否显示"跳转到"部分,默认true--显示
                    showRowsPerPage:true,//是否显示"每页显示条数"部分。默认true--显示
                    showRowsInfo:true,//是否显示记录的信息，默认true--显示

                    //用户每次切换页号，都自动触发本函数;
                    //每次返回切换页号之后的pageNo和pageSize
                    onChangePage: function(event,pageObj) { // returns page_num and rows_per_page after a link has clicked
                        //js代码
                        //alert(pageObj.currentPage);
                        //alert(pageObj.rowsPerPage);
                        queryActivityByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
                    }
                });
            }
        });
    }
    //放大图片的函数
    function Big(obj) {
        imgShow("#outerdiv", "#innerdiv", "#bigimg", obj);
    }
    function copyMagnet(_this){
        var num = _this.value;
        $.ajax({
            url:'copyMagnet.do',
            data:{
                num:num
            },
            type:'post',
            dataType:'json',
            success:function(data){

                const clipboardObj = navigator.clipboard;


                if (data.magnet != null){
                    clipboardObj.writeText(data.magnet);

                }else{
                    alert("没有磁链");
                }


            }
        })
    }


    function imgShow(outerdiv, innerdiv, bigimg, _this) {
        var src = _this.src;//获取当前点击的pimg元素中的src属性
        $(bigimg).attr("src", src);//设置#bigimg元素的src属性

        /*获取当前点击图片的真实大小，并显示弹出层及大图*/
        $("<img/>").attr("src", src).load(function () {
            var windowW = $(window).width();//获取当前窗口宽度
            var windowH = $(window).height();//获取当前窗口高度
            var realWidth = this.width;//获取图片真实宽度
            var realHeight = this.height;//获取图片真实高度
            var imgWidth, imgHeight;
            var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

            if (realHeight > windowH * scale) {//判断图片高度
                imgHeight = windowH * scale;//如大于窗口高度，图片高度进行缩放
                imgWidth = imgHeight / realHeight * realWidth;//等比例缩放宽度
                if (imgWidth > windowW * scale) {//如宽度扔大于窗口宽度
                    imgWidth = windowW * scale;//再对宽度进行缩放
                }
            } else if (realWidth > windowW * scale) {//如图片高度合适，判断图片宽度
                imgWidth = windowW * scale;//如大于窗口宽度，图片宽度进行缩放
                imgHeight = imgWidth / realWidth * realHeight;//等比例缩放高度
            } else {//如果图片真实高度和宽度都符合要求，高宽不变
                imgWidth = realWidth;
                imgHeight = realHeight;
            }
            $(bigimg).css("width", imgWidth);//以最终的宽度对图片缩放

            var w = (windowW - imgWidth) / 2;//计算图片与窗口左边距
            var h = (windowH - imgHeight) / 2;//计算图片与窗口上边距
            $(innerdiv).css({ "top": h, "left": w });//设置#innerdiv的top和left属性
            $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
        });

        $(outerdiv).click(function () {//再次点击淡出消失弹出层
            $(this).fadeOut("fast");
        });
    };





</script>


</html>