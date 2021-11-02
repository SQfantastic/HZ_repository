<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">
    <title>菜单管理</title>

</head>
    <frameset cols="250,*" border="1">
    <frame src="${ctx}/system?method=toMenuLeft" name="left">
    <frame src="${ctx}/system?method=toMenuRight" name="right">
    </frameset>
</html>
