<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css" type="text/css">
</head>
<%--jquery-1.11.3.min.js一定要在jquery.validate.min.js前引入--%>
<script src="${pageContext.request.contextPath}/jsl/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jsl/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript">
    function show(id) {
        document.getElementById(id).style.display = "block";
    }
    function getRowValue(element) {
        document.getElementById("modify").style.display = "block";
        var node = element.parentNode.parentNode;
        document.getElementById("id1").value = node.children[7].innerHTML;
        document.getElementById("loginName1").value = node.children[0].innerHTML;
        document.getElementById("oldLoginName").value = node.children[0].innerHTML;
        document.getElementById("realName1").value = node.children[1].innerHTML;
        document.getElementById("password1").value = node.children[8].innerHTML;
        document.getElementById("repassword1").value = node.children[8].innerHTML;
        document.getElementById("sex1").value = node.children[9].innerHTML;
    }
    function hidden(id) {
        document.getElementById(id).style.display = "none";
    }
    $.validator.addMethod(
        "checkLoginName",
        function (value, element, params) {
            var flag = false;
            $.ajax({
                async: false,
                url: "${pageContext.request.contextPath}/user/checkLoginName",
                data: {"loginName": value},
                type: "post",
                dataType: "json",
                success: function (data) {
                    flag = data;
                }
            });
            return flag;
        });
    $(function () {
        $("#addForm").validate({
            rules: {
                "loginName": {
                    "required": true,
                    "rangelength": [2, 10],
                    "checkLoginName": true
                },
                "realName": {
                    "required": true,
                    "rangelength": [2, 10]
                },
                "password": {
                    "required": true,
                    "rangelength": [6, 12]
                },
                "repassword": {
                    "required": true,
                    "rangelength": [6, 12],
                    "equalTo": "#password"
                }
            },
            messages: {
                "loginName": {
                    "required": "登录名不能为空",
                    "rangelength": "输入不合法，请输入长度位2-10的字符串",
                    "checkLoginName": "该用户名已经被注册，请重新选择"
                },
                "realName": {
                    "required": "登录名不能为空",
                    "rangelength": "输入不合法，请输入长度位2-10的字符串"
                },
                "password": {
                    "required": "密码不能为空",
                    "rangelength": "密码长度6-12位之间"
                },
                "repassword": {
                    "required": "密码不能为空",
                    "rangelength": "密码长度6-12位之间",
                    "equalTo": "两次密码不一致"
                }
            }
        });
    });
    $(function () {
        $("#modifyForm").validate({
            rules: {
                "loginName": {
                    "required": true,
                    "rangelength": [2, 10],
                    remote:{
                        type:"post",
                        url:"${pageContext.request.contextPath}/user/checkModifyLoginName",
                        data:{
                            "oldLoginName":function () {
                                return $("#oldLoginName").val();
                            },
                            "loginName":function () {
                                return $("#loginName1").val();
                            }
                        },
                        dataType:"json",
                        dataFilter:function(data){
                            return data;
                        }
                    }
                },
                "realName": {
                    "required": true,
                    "rangelength": [2, 10]
                },
                "password": {
                    "required": true
                },
                "repassword": {
                    "required": true,
                    "equalTo": "#password1"
                }
            },
            messages: {
                "loginName": {
                    "required": "登录名不能为空",
                    "rangelength": "输入不合法，请输入长度位2-10的字符串",
                    remote:"账号已经被注册过了，请重新选择"
                },
                "realName": {
                    "required": "登录名不能为空",
                    "rangelength": "输入不合法，请输入长度位2-10的字符串"
                },
                "password": {
                    "required": "密码不能为空"
                },
                "repassword": {
                    "required": "密码不能为空",
                    "equalTo": "两次密码不一致"
                }
            }
        });
    });
</script>
<body>
<div align="center">
    <button onclick="show('add')">添加用户</button>
</div>
<%--   查询用户表单--%>
<div align="center">
    <form action="${pageContext.request.contextPath}/user/query" method="post">
        用户名:<input name="loginName" type="text">
        真实姓名：<input name="realName" type="text">
        性别：<select name="sex">
        <option value="5">请选择</option>
        <option value="0">男</option>
        <option value="1">女</option>
    </select>
        角色：<select name="roleId">
        <option value="">请选择</option>
        <c:forEach items="${sessionScope.roleList}" var="role">
            <option value="${role.id}">${role.name}</option>
        </c:forEach>
    </select>
        <input type="submit" value="查询">
    </form>
</div>
<%--   添加用户窗口，默认隐藏--%>
<div id="add" class="add">
    <div align="center" style="margin-top: 10%;background-color: #58F5F5;width: 40%;margin-left: 30%">
        <form action="${pageContext.request.contextPath}/user/addUser" method="post" id="addForm">
            <div>用&emsp;户名:<input type="text" name="loginName"></div>
            <div>真实姓名:<input type="text" name="realName"></div>
            <div>密&emsp;&emsp;码:<input type="password" name="password" id="password"></div>
            <div>确认密码:<input type="password" name="repassword"></div>
            <div>性别:
                &emsp; &emsp;男<input type="radio" name="sex" value="0" checked>
                &emsp; &emsp;女<input type="radio" name="sex" value="1">
            </div>
            <div>
                <button type="submit">添加</button>&emsp; &emsp;&emsp; &emsp;
                <%--<button type="button" onclick="hidden('add')">取消</button>--%>
            </div>
        </form>
    </div>
</div>
<%--   修改用户窗口，默认隐藏--%>
<div id="modify" class="add">
    <div align="center" style="margin-top: 10%;background-color: #58F5F5;width: 40%;margin-left: 30%">
        <form action="${pageContext.request.contextPath}/user/update" method="post" id="modifyForm">
            <input type="hidden" name="id" id="id1">
            <input type="hidden" name="oldLoginName" id="oldLoginName">
            <div>用&emsp;户名:<input type="text" name="loginName" id="loginName1"></div>
            <div>真实姓名:<input type="text" name="realName" id="realName1"></div>
            <div>密&emsp;&emsp;码:<input type="password" name="password" id="password1"></div>
            <div>确认密码:<input type="password" name="repassword" id="repassword1"></div>
            <div>性别:
                <select name="sex" id="sex1">
                    <option value="0">男</option>
                    <option value="1">女</option>
                </select>
            </div>
            <div>
                <button type="submit">修改</button>&emsp; &emsp;&emsp; &emsp;
                <%--<button type="button" onclick="hidden('add')">取消</button>--%>
            </div>
        </form>
    </div>
</div>
<%--   用户信息显示--%>
<div>
    <table width="100%" align="center">
        <%--表头--%>
        <tr>
            <td align="center">用户名</td>
            <td align="center">真实姓名</td>
            <td align="center">性别</td>
            <td align="center">登录时间</td>
            <td align="center">角色</td>
            <td align="center">注册时间</td>
            <td align="center">是否激活</td>
            <td align="center">操作</td>
        </tr>
        <c:forEach items="${allUserList}" var="user">
            <tr>
                <td align="center">${user.loginName}</td>
                <td align="center">${user.realName}</td>
                <td align="center">
                    <c:if test="${user.sex==0}">男</c:if>
                    <c:if test="${user.sex==1}">女</c:if>
                </td>
                <td align="center">
                    <c:choose>
                        <c:when test="${user.loginTime==null}">
                        </c:when>
                        <c:otherwise>
                            <fmt:formatDate value="${user.loginTime}" type="date"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td align="center">${user.role.name}</td>
                <td align="center">
                    <c:choose>
                        <c:when test="${user.registTime==null}">
                        </c:when>
                        <c:otherwise>
                            <fmt:formatDate value="${user.registTime}" type="both"/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td align="center">
                    <c:if test="${user.status==0}">否</c:if>
                    <c:if test="${user.status==1}">是</c:if>
                </td>
                <td style="display:none">${user.id}</td>
                <td style="display:none">${user.password}</td>
                <td style="display:none">${user.sex}</td>
                <td align="center">
                    <c:if test="${user.status==0}">
                        <a href="${pageContext.request.contextPath}/user/active?id=${user.id}">激活</a>
                    </c:if>
                    <c:if test="${user.status==1}">
                        <a onclick="getRowValue(this)" style="color: blue">修改</a>
                        <a href="${pageContext.request.contextPath}/user/delete?id=${user.id}">删除</a>
                    </c:if>
                </td>

            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
