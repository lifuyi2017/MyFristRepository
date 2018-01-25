<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户管理系统主页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css" type="text/css">
</head>
<body>
<div class="main">
    <div class="head">
        <span>欢迎来到用户权限管理系统，祝您玩得愉快</span>
        <a class="head_a" href="${pageContext.request.contextPath}/user/loginOut">注销</a>
    </div>
    <div class="left">
        <!-- 左侧导航 -->
        <c:forEach items="${sessionScope.admin.menuList}" var="menu">
            <a class="left_a" href="${pageContext.request.contextPath}/${menu.url}/displayAll" target="showMessage">${menu.name}</a>
        </c:forEach>
    </div>
    <div class="right">
        <iframe width="100%" height="100%" scrolling="yes" frameborder="0" name="showMessage" />
    </div>
</div>
</body>
</html>
