<%--
  Created by IntelliJ IDEA.
  User: 25760
  Date: 2019/12/6
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html class="loginHtml">
<head>
    <meta charset="utf-8">
    <title>注册--汽车出租系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="${ctx}/resources/favicon.ico">
    <link rel="stylesheet" href="${ctx}/resources/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="${ctx}/resources/css/public.css" media="all"/>
</head>
<h2 style="color: #009688;text-align: center;font-size: 55px;font-weight: bold;padding-top: 50px">SuperCarRent汽车租赁系统</h2>
<body class="loginBody">
<form class="layui-form" id="loginForm" action="${ctx}/system/user?method=register" method="post">
    <div class="login_face"><img src="${ctx}/resources/images/face.jpg" class="userAvatar"></div>
    <div class="layui-form-item input-item">
        <label for="loginname">用户名</label>
        <input type="text" placeholder="请输入用户名" autocomplete="off" id="loginname" name="loginname" class="layui-input"
               lay-verify="required">
    </div>
    <div class="layui-form-item input-item">
        <label for="pwd">密码</label>
        <input type="password" placeholder="请输入密码" autocomplete="off" id="pwd" name="pwd" class="layui-input"
               lay-verify="required">
    </div>
    <div class="layui-form-item input-item" id="imgCode">
        <label for="code">验证码</label>
        <input type="text" placeholder="请输入验证码" autocomplete="off" name="code" id="code" class="layui-input">
        <img src="${ctx}/user?method=getCode&" onclick="javascript:this.src+=<%=Math.random()%>">
    </div>
    <div class="layui-form-item">
        <button class="layui-btn layui-block" lay-filter="login" lay-submit>注册</button>
    </div>
    <div class="layui-form-item layui-row" style="text-align: center;color: red;">
        <span style="color: red" id="msg">${errorMsg}</span>
    </div>
</form>
<script type="text/javascript" src="${ctx}/resources/layui/layui.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/cache.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-3.4.1.js"></script>
<script type="text/javascript">
    //ajax异步验证用户名是否重复
    $(function () {
        $("[name=loginname]").blur(function () {
            let username = $(this).val();
            console.log(username);
            if (username.trim()!=null&& username.trim()!=""){
                $.ajax({
                    url:"${ctx}/system/user?method=checkUser",
                    data:{username:username},
                    dataType:"json",
                    type:"post",
                    success:function (resp) {
                        var msg = resp.msg;
                        console.log(resp)
                        if (resp.code==200){
                            $("#msg").text(msg).css("color","green")
                            $(".layui-btn").prop("disabled",false);
                        }else {
                            $("#msg").text(msg).css("color","red")
                            $(".layui-btn").prop("disabled",true);
                        }
                    },
                    error:function (resp) {
                        alert("请求失败")
                    }
                })
            }else {
                $("#msg").text("用户名不能为空！").css("color","red")
                $(".layui-bt").prop("disabled",true);
            }
        })
    })

    layui.use(['form', 'layer', 'jquery'], function () {
        var form = layui.form,
            layer = parent.layer === undefined ? layui.layer : top.layer,
            $ = layui.jquery;

        //解决重定向嵌套问题
        if (window.top.location.href !== location.href) {
            window.top.location.href = location.href;
        }
        //注册按钮
        form.on("submit(login)", function (data) {
            $(this).text("注册中...").attr("disabled", "disabled").addClass("layui-disabled");
            setTimeout(function () {
                $("#loginForm").submit();
            }, 1000);
            return false;
        });

        //表单输入效果
        $(".loginBody .input-item").click(function (e) {
            e.stopPropagation();
            $(this).addClass("layui-input-focus").find(".layui-input").focus();
        });
        $(".loginBody .layui-form-item .layui-input").focus(function () {
            $(this).parent().addClass("layui-input-focus");
        });
        $(".loginBody .layui-form-item .layui-input").blur(function () {
            $(this).parent().removeClass("layui-input-focus");
            if ($(this).val() != '') {
                $(this).parent().addClass("layui-input-active");
            } else {
                $(this).parent().removeClass("layui-input-active");
            }
        })
    })

</script>
</body>
</html>