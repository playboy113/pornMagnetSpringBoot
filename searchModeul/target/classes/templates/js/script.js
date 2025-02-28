$(function(){






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
            url:'pornMagnet/queryMagnetsByNums.do',
            data:{
                nums:nums
            },
            type:'post',
            dataType:'json',
            success:function (data){



                // //alert(data.retStr)
                // //alert(data.magnetList)
                // const cInput = document.createElement('input')
                // cInput.value = data.retStr;
                //
                //
                // document.body.appendChild(cInput)
                // cInput.select() // 选取文本域内容;
                // // 执行浏览器复制命令
                // // 复制命令会将当前选中的内容复制到剪切板中（这里就是创建的input标签）
                // // Input要在正常的编辑状态下原生复制方法才会生效
                // document.execCommand('Copy')
                // alert("复制成功")
                // //alert("复制成功")
                // //this.$notifice('success', '复制成功') // antd框架封装的通知,如使用别的UI框架，换掉这句
                //
                // /// 复制成功后再将构造的标签 移除
                // cInput.remove()
                const copyToClipboard = (text) => {
                    // 创建一个 <textarea> 元素
                    const textArea = document.createElement('textarea');
                    textArea.value = text; // 设置文本内容
                    textArea.style.position = 'fixed'; // 避免滚动到底部
                    textArea.style.top = '0';
                    textArea.style.left = '0';
                    textArea.style.width = '1px'; // 设置为不可见
                    textArea.style.height = '1px';
                    textArea.style.padding = '0';
                    textArea.style.border = 'none';
                    textArea.style.outline = 'none';
                    textArea.style.boxShadow = 'none';
                    textArea.style.background = 'transparent';

                    // 将 <textarea> 添加到 DOM 中
                    document.body.appendChild(textArea);

                    // 选中文本
                    textArea.select();

                    try {
                        // 使用 Clipboard API 复制文本
                        if (navigator.clipboard) {
                            navigator.clipboard.writeText(textArea.value).then(() => {
                                alert("复制成功");
                            });
                        } else {
                            // 回退到旧方法
                            document.execCommand('Copy');
                            alert("复制成功");
                        }
                    } catch (err) {
                        console.error("复制失败: ", err);
                        alert("复制失败，请手动复制");
                    } finally {
                        // 移除 <textarea>
                        document.body.removeChild(textArea);
                    }
                };

// 调用函数
                copyToClipboard(data.retStr);

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
    var series=$("#query-series").val();

    //var pageNo=1;
    //var pageSize=10;
    //发送请求
    $.ajax({
        url:'pornMagnet/porndos.do',
        data:{
            title:title,
            actress:actress,
            subline:subline,
            HD:HD,
            num:num,
            types:types,
            date:date,
            producer:producer,
            series:series,
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
                //将types中的链接分割
                const typesElement = obj.types.split(' ');



                htmlStr+="<tr class=\"active\">";
                htmlStr+="<td><input type=\"checkbox\" value=\""+obj.num+"\"/></td>";
                htmlStr+="<td> <img src='/images/"+obj.num+".jpg' width='100' height='75' onclick='Big(this)'></td>";
                //htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.num+"'\">"+obj.num+"</a></td>";
                htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='https://missav.ws/cn/ftht-261-uncensored-leak'\">"+obj.num+"</a></td>";

                htmlStr+="<td>"+obj.title+"</td>";
                htmlStr+="<td>"+obj.subline+"</td>";
                htmlStr+="<td>"+obj.HD+"</td>";
                htmlStr+="<td>"+obj.actress+"</td>";
                htmlStr+="<td>"+obj.types+"</td>";
                htmlStr+="<td>"+obj.producer+"</td>";
                htmlStr+="<td>"+obj.date+"</td>";
                htmlStr+="<td>"+obj.series+"</td>";

                // htmlStr+="<td> <button type='button' onclick=\"window.location.href='copyMagnet.do?num=ekw-077'\">磁链</td>";
                if (obj.selected ===1){
                    htmlStr+="<td> <button type='button' class='btn-danger'  onclick='copyMagnet(this)' value=\""+obj.num+"\">磁链</td>";
                }else{
                    htmlStr+="<td> <button type='button'  onclick='copyMagnet(this)' value=\""+obj.num+"\">磁链</td>";
                }
                htmlStr+="<td> <button type='button'  onclick='playVideo(this)' value=\""+obj.num+"\">播放</td>";

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

            //对容器调用bs_pagination工具函数,显示翻页信息
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

//播放按钮
function playVideo(_this){
    var num = _this.value;

    $.ajax({
        url:'pornMagnet/playVideo.do',
        data:{
            num:num
        },
        type:'post',
        dataType:'json',
        success:function(data){



            window.open(data.url1,"_blank");

        }

    })
}



function copyMagnet(_this){
    var num = _this.value;
    $.ajax({
        url:'pornMagnet/copyMagnet.do',
        data:{
            num:num
        },
        type:'post',
        dataType:'json',
        success:function(data){
            //alert(data.magnet)

            const cInput = document.createElement('input')
            cInput.value = data.magnet
            document.body.appendChild(cInput)
            cInput.select() // 选取文本域内容;
            // 执行浏览器复制命令
            // 复制命令会将当前选中的内容复制到剪切板中（这里就是创建的input标签）
            // Input要在正常的编辑状态下原生复制方法才会生效
            document.execCommand('Copy')
            //this.$notifice('success', '复制成功') // antd框架封装的通知,如使用别的UI框架，换掉这句
            //alert("复制成功")
            /// 复制成功后再将构造的标签 移除
            cInput.remove()



            // const clipboardObj = navigator.clipboard;
            // if (data.magnet != null){
            //     clipboardObj.writeText(data.magnet);
            //
            //     alert("复制成功")
            // }else{
            //     alert("没有磁链");
            // }


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

//    获取类别
function queryTypes(indexName,aggName,fileName) {

    $.ajax({
        url:'pornMagnet/aggRequest.do?indexName='+indexName+'&aggName='+aggName+'&fileName='+fileName,
        data:{


        },
        type:'post',
        dataType:'json',
        success:function(data){
            var typesList = data.aggList;
            //alert(data.aggList.length)
            var typesArr = new Array(data.aggList.length);



            //alert(typesArr)
            var htmlStr ="";
            $.each(data.aggList,function (index,obj) {
                typesArr[index] = obj;



                htmlStr+="<label class=\"btn btn-default\"  onclick='SupendButtonClick(this);' ><input type=\"checkbox\" name=\"typeName\" value="+obj+">"+obj+"</label>";


                // htmlStr+="<tr class=\"active\">";
                // htmlStr+="<td><input type=\"checkbox\" value=\""+obj.num+"\"/></td>";
                // htmlStr+="<td> <img src='./images/"+obj.num+".jpg' width='100' height='75' onclick='Big(this)'></td>";
                // htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.num+"'\">"+obj.num+"</a></td>";
                // htmlStr+="<td>"+obj.title+"</td>";
                // htmlStr+="<td>"+obj.subline+"</td>";
                // htmlStr+="<td>"+obj.HD+"</td>";
                // htmlStr+="<td>"+obj.actress+"</td>";
                // htmlStr+="<td>"+obj.types+"</td>";
                // htmlStr+="<td>"+obj.producer+"</td>";
                // htmlStr+="<td>"+obj.date+"</td>";
                //
                // // htmlStr+="<td> <button type='button' onclick=\"window.location.href='copyMagnet.do?num=ekw-077'\">磁链</td>";
                // htmlStr+="<td> <button type='button' onclick='copyMagnet(this)' value=\""+obj.num+"\">磁链</td>";
                // htmlStr+="</tr>";



            });
            $("#typesBody").html(htmlStr);
            //queryBySelectTypes(typesArr,1,50);




        }
    })

}
//   点击选中
function SupendButtonClick(obj) {
    var checkbox = $(obj);

    var unchecked = checkbox.prop("checked");
    // alert(unchecked)




    // if (obj.getAttribute('class')==="btn btn-default"|| obj.getAttribute('class')==="btn btn-default active"){
    //     obj.setAttribute('class',"btn btn-primary")
    //
    //
    // }else{
    //    obj.setAttribute('class',"btn btn-default")
    //
    //
    // }
}
//获取类别信息
function getVals(){
    var res = getRadioRes('typeName');
    if (res.length==0){
        queryActivityByConditionForPage(1,50);
    }else{
        queryBySelectTypes(res,1,50)
    }
    //queryBySelectTypes(res,1,10)

    //queryBySelectTypes(res,1,10)
    if(res.length < 1){
        mui.toast('请选择');
        return;
    }
    mui.toast(res);
}
function getRadioRes(className){
    var rdsObj = document.getElementsByName(className);/*获取值*/
    //alert(rdsObj.length);
    //queryBySelectTypes(selectTypes,pageNo,pageSize)
    var checkVal = new Array();
    var k = 0;
    for(i = 0; i< rdsObj.length; i++){
        if(rdsObj[i].checked){
            checkVal[k] = rdsObj[i].value;
            k++;
        }
    }
    return checkVal;
}
//根据类别信息查找
function queryBySelectTypes(selectTypes,pageNo,pageSize){
    var title=$("#query-title").val();
    var actress=$("#query-actress").val();
    var subline=$("#query-subline").val();
    var HD=$("#query-HD").val();
    var num=$("#query-num").val();
    var types = JSON.stringify(selectTypes);
    var date=$("#query-date").val();
    var producer=$("#query-producer").val();
    var series=$("#query-series").val();

    $.ajax({
        url:'pornMagnet/queryMagnetsByTypes.do',
        data:{
            title:title,
            actress:actress,
            subline:subline,
            HD:HD,
            num:num,
            types:types,
            date:date,
            producer:producer,
            series:series,
            pageNo: pageNo,
            pageSize:pageSize



        },
        type:'post',
        dataType:'json',
        success:function(data){


            var htmlStr = "";
            $.each(data.magnet_models,function (index,obj) {

                htmlStr+="<tr class=\"active\">";
                htmlStr+="<td><input type=\"checkbox\" value=\""+obj.num+"\"/></td>";
                htmlStr+="<td> <img src='/images/"+obj.num+".jpg' width='100' height='75' onclick='Big(this)'></td>";
                htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='https://missav.ws/cn/ftht-261-uncensored-leak'\">"+obj.num+"</a></td>";
                htmlStr+="<td>"+obj.title+"</td>";
                htmlStr+="<td>"+obj.subline+"</td>";
                htmlStr+="<td>"+obj.HD+"</td>";
                htmlStr+="<td>"+obj.actress+"</td>";
                htmlStr+="<td>"+obj.types+"</td>";
                htmlStr+="<td>"+obj.producer+"</td>";
                htmlStr+="<td>"+obj.date+"</td>";
                htmlStr+="<td>"+obj.series+"</td>";

                // htmlStr+="<td> <button type='button' onclick=\"window.location.href='copyMagnet.do?num=ekw-077'\">磁链</td>";
                if (obj.selected ===1){
                    htmlStr+="<td> <button type='button' class='btn-danger'  onclick='copyMagnet(this)' value=\""+obj.num+"\">磁链</td>";
                }else{
                    htmlStr+="<td> <button type='button'  onclick='copyMagnet(this)' value=\""+obj.num+"\">磁链</td>";
                }
                htmlStr+="<td> <button type='button'  onclick='playVideo(this)' value=\""+obj.num+"\">播放</td>";
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

            //对容器调用bs_pagination工具函数,显示翻页信息
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
                    //queryActivityByConditionForPage(pageObj.currentPage,pageObj.rowsPerPage);
                    queryBySelectTypes(selectTypes,pageObj.currentPage,pageObj.rowsPerPage)
                }
            });


        }
    })




}
function queryTypesArr(indexName,aggName,fileName){

    var typesArr = new Array();
    $.ajax({
        url:'pornMagnet/aggRequest.do?indexName='+indexName+'&aggName='+aggName+'&fileName='+fileName,
        data:{
        },
        type:'post',
        dataType:'json',
        success:function(data){
            var typesList = data.aggList;
            //alert(data.aggList.length)
            //alert(typesArr)
            var htmlStr ="";
            $.each(data.aggList,function (index,obj) {
                typesArr[index] = obj;
                //htmlStr+="<label class=\"btn btn-default\"  onclick='SupendButtonClick(this);' ><input type=\"checkbox\" name=\"typeName\" value="+obj+">"+obj+"</label>";


                // htmlStr+="<tr class=\"active\">";
                // htmlStr+="<td><input type=\"checkbox\" value=\""+obj.num+"\"/></td>";
                // htmlStr+="<td> <img src='./images/"+obj.num+".jpg' width='100' height='75' onclick='Big(this)'></td>";
                // htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.num+"'\">"+obj.num+"</a></td>";
                // htmlStr+="<td>"+obj.title+"</td>";
                // htmlStr+="<td>"+obj.subline+"</td>";
                // htmlStr+="<td>"+obj.HD+"</td>";
                // htmlStr+="<td>"+obj.actress+"</td>";
                // htmlStr+="<td>"+obj.types+"</td>";
                // htmlStr+="<td>"+obj.producer+"</td>";
                // htmlStr+="<td>"+obj.date+"</td>";
                //
                // // htmlStr+="<td> <button type='button' onclick=\"window.location.href='copyMagnet.do?num=ekw-077'\">磁链</td>";
                // htmlStr+="<td> <button type='button' onclick='copyMagnet(this)' value=\""+obj.num+"\">磁链</td>";
                // htmlStr+="</tr>";

            });
            //$("#typesBody").html(htmlStr);
            //alert(typesArr)
            //return typesArr;
        }
    })
    //alert(typesArr);



}