/**
 * Created by Administrator on 2018/1/16 0016.
 */
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
/*$.validator.addMethod(
    "checkModifyLoginName",
    function (value, element, params) {
        var data1 = {
            "oldLoginName": function () {
                return $("#oldLoginName").val();
            }, "loginName": function () {
                return $("#loginName1").val();
            }
        };
        var flag = false;
        $.ajax({
            async: false,
            url: "${pageContext.request.contextPath}/user/checkModifyLoginName",
            data: JSON.stringify(data1),
            type: "post",
            dataType: "json",
            contentType:"application/json",
            success: function (data) {
                flag = data;
            }
        });
        return flag;
    });*/
$(function () {
    $("#addForm").validate({
        rules: {
            "loginName": {
                "required": true,
                "rangelength": [2, 10],
                "checkLoginName":true
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
                    data:JSON.stringify({
                        "oldLoginName":function () {
                            return $("#oldLoginName").val();
                        },
                        "loginName":function () {
                            return $("#loginName1").val();
                        }
                    }),
                    dataType:"html",
                    contentType:"application/json",
                    dataFilter:function(data){
                        var flag=data;
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
                remote: "账号已经被注册过了，请重新选择"
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