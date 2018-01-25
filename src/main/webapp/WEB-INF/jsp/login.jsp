<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<script src="${pageContext.request.contextPath}/jsl/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jsl/jquery.validate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/login.js" type="text/javascript"></script>
<script type="text/javascript">
    function reload() {
        /*这句代码的最后加？后面的代码一定不能少，因为浏览器是由缓存的，如果不加时间戳，在点击标签换图片时，验证码图片不会正常更换*/
        document.getElementById("imageCode").src="${pageContext.request.contextPath}/user/imageServlet?date="+new Date().getTime();
        $("#myCheckCode").val("");
    }
    //采用validate验证码校验
    $.validator.addMethod(
        "checkCode",
        function (value,element,params) {
            //定义一个标志
            var flag = false;
            $.ajax({
                async:false,
                url:"${pageContext.request.contextPath}/user/verificationCode",
                data:{"code":value},
                type:"post",
                dataType:"json",
                success:function (data) {
                    flag=data;
                }
            });
            return flag;
        });
    function fn1() {
        var jsonStr={"phone":"15086717544"};
        var jsonArrayFinal=JSON.stringify(jsonStr);
        $.ajax({
            url:"${pageContext.request.contextPath}/user/sendCode",
            data:{mydata:jsonArrayFinal},
            type:"post",
            dataType:"json",
            success:function (data) {
                document.getElementById("span1").innerHTML=data;
            }
        });
    }
</script>
<body>
<form action="${pageContext.request.contextPath}/user/login" method="post" id="loginForm">
    <h3>欢迎来到登录页面</h3>
    <div>
        <span>用户名：</span><input type="text" name="loginName" id="loginName">
    </div>
    <div>
        <span>密&emsp;码：</span><input type="password" name="password" id="password">
    </div>
  <%--  <div>
        <span>验证码：</span><input type="text" name="checkCode" id="myCheckCode">
    </div>
    <div>
        <img  src="${pageContext.request.contextPath}/user/imageServlet" alt="验证码" id="imageCode" />
    </div>
    <div>
        <a onclick="reload()"><label>换一张</label></a><br>
    </div>--%>
    <%--手机验证码,后台采用消息队列处理--%>
    <div>
        <span id="span1"></span><br>
        <span>短信验证码：</span><input type="text" name="permissionCode" id="permissionCode">
        <span><input type="button" onclick="fn1()">获取验证码</span>
    </div>
    <button type="submit">登&emsp;录</button>
</form>
</body>
</html>
