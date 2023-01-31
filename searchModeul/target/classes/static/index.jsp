<%--
  Created by IntelliJ IDEA.
  User: 张君哲
  Date: 2022/11/22
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>

  <body>
  <form id="formId">
    姓名:<input type="text" name="name" id="name"><br/>
    年龄:<input type="password" name="pass" id="pass"><br/>


    <input type="button" value="提交" id="sendTo">
  </form>
  </body>
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
    $("#sendTo").click(function () {
      //获取值
      var name = $("#name").val();
      var age = $("#age").val();
      // var sex = $("input[type='radio']").val();
      // var hobby = $("input[name='hobby']:checked").serialize();    //此处为复选框,用序列化的方式传递
      // var address = $("#address").val();
      $.ajax({
        url:"toServer.do",
        type:"post",
        //注意序列化的值一定要放在最前面,并且不需要头部变量,不然获取的值得格式会有问题
        data:{"name":name,"age":age},
        dataType:"json",
        success:function (data) {
          // alert(data.result);
          alert(data.result);
        }
      })
    })
  </script>
</html>
