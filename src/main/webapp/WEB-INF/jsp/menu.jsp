
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>菜单</title>
</head>
<body>
<%--显示菜单表--%>
<div>
    <table width="100%" align="center">
        <%--表头--%>
        <tr>
            <td align="center">菜单名称</td>
            <td align="center">url</td>
            <td align="center">操作</td>
        </tr>
        <c:forEach items="${menuList}" var="menu">
            <tr>
                <td align="center">${menu.name}</td>
                <td align="center">${menu.url}</td>
                <td>
                    <a>查看能操作此菜单的所有用户</a>
                    <a>修改</a>
                    <a>删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
