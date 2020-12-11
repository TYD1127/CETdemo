<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<title>CET四级成绩查询测试</title>
<head>
    <base href="${pageContext.request.contextPath}/">
    <link rel="stylesheet" type="text/css" href="resource/h-ui/static/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="resource/h-ui/static/h-ui.admin/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css" href="resource/h-ui/lib/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="resource/h-ui/static/h-ui.admin/css/style.css"/>
</head>
<body>
<div class="page-container">
    <h3 class="label-success radius text-c" style="word-wrap: normal">四六级成绩查询</h3>
    <div class="mt-25 ">
        <div class="row cl mt-20 col-xs-12 text-c">
            <div class="formControls col-xs-4 col-xs-offset-4">
                <input class="input-text radius size-L" id="ticketNumber" name="ticketNumber" type="text"
                       placeholder="准考证号："/>
            </div>
        </div>
        <div class="row cl  mt-20 col-xs-12">
            <div class="formControls col-xs-4 col-xs-offset-4  text-c">
                <input class="input-text radius size-L" id="name" name="name" type="text" placeholder="姓名："/>
            </div>
        </div>

        <div class="row cl">
            <div class="col-xs-8 col-xs-offset-2 text-c  mt-20">
                <button class="btn badge-success radius" onclick="btn_cx()">立即查询</button>
            </div>
        </div>
    </div>
</div>
<div class="page-container">
    <h3 class="label-success radius text-c" style="word-wrap: normal">教师资格证成绩查询</h3>
    <div class="mt-25 ">
        <div class="row cl mt-20 col-xs-12 text-c">
            <div class="formControls col-xs-4 col-xs-offset-4">
                <input class="input-text radius size-L" id="zjh" name="zjh" type="text"
                       placeholder="证件号码："/>
            </div>
        </div>
        <div class="row cl  mt-20 col-xs-12">
            <div class="formControls col-xs-4 col-xs-offset-4  text-c">
                <input class="input-text radius size-L" id="uname" name="uname" type="text" placeholder="姓名："/>
            </div>
        </div>

        <div class="row cl">
            <div class="col-xs-8 col-xs-offset-2 text-c  mt-20">
                <button class="btn badge-success radius" onclick="btn_jszgcx()">立即查询</button>
            </div>
        </div>
    </div>
</div>

<%--H-ui js 文件-----开始--%>
<script type="text/javascript" src="resource/h-ui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="resource/h-ui/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="resource/h-ui/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="resource/h-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="resource/h-ui/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="resource/h-ui/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="resource/h-ui/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="resource/h-ui/lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="resource/h-ui/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
<script type="text/javascript" src="resource/h-ui/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<%--H-ui js 文件-----结束--%>
<%--Script逻辑 ---开始--%>
<script type="text/javascript">
    function btn_cx() {
        var ticketNumber = $("#ticketNumber").val();
        var name = $("#name").val();
        var param = {
            "ticketNumber": ticketNumber,
            "name": name,
        };
        $.ajax({
            type: "post",
            url: "cet/cet4",
            async: false,
            data: param,
            success: function (data) {
                console.log(data);
                var html = '<table class="table table-border table-bordered table-bg">';
                    html += '<tbody>';
                    html += '<tr class="text-c">';
                    html += '<td>姓名：' + data.n + '</td>';
                    html += '<td>成绩：' + data.s + '</td>';
                    html += '<td>学校：' + data.x + '</td>';
                    html += '</tr>';
                    html += '</tbody>';
                    html += '</table>';
                layer.open({
                    type: 0,
                    area: ['33.3%', '33.3%'],
                    fix: false, //不固定
                    maxmin: true,
                    shade: 0.1,
                    title: '四六级成绩查询',
                    content: html,
                });

            }
        });
    }

    function btn_jszgcx() {
        var zjh = $("#zjh").val();
        var uname = $("#uname").val();
        var param = {
            "uname": uname,
            "zjh": zjh,
        };
        $.ajax({
            type: "post",
            url: "cet/ntce",
            async: false,
            data: param,
            success: function (data) {
                console.log(data);
                var content = '<table class="table table-border table-bordered table-bg">';
                content += '<tbody>';
                for (var i = 0; i < data.length; i++) {
                    content += '<tr class="text-c">';
                    content += '<td>科目：' + data[i].k + '</td>';
                    content += '<td>成绩：' + data[i].bgf + '</td>';
                    content += '<td>' + data[i].h + '</td>';
                    content += '</tr>';
                }
                content += '</tbody>';
                content += '</table>';
                layer.open({
                    type: 0,
                    area: ['33.3%', '33.3%'],
                    fix: false, //不固定
                    maxmin: true,
                    shade: 0.4,
                    title: '教师资格证成绩查询',
                    content: content
                });
            }
        });
    }
</script>
</body>
</html>
