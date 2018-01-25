package cn.lifuyi.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Created by Administrator on 2017/12/13 0013.
 */
public class sendsmsUtil {
    //获取验证码
    public static  int getPermissionCode(String phone){
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod("http://106.ihuyi.cn/webservice/sms.php?method=Submit");
        client.getParams().setContentCharset("GBK");
        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");

        int mobile_code = (int)((Math.random()*9+1)*100000);
        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = {//提交短信
                new NameValuePair("account", "C66002327"),
                new NameValuePair("password", "7ab7bb47b3a1b75cbe2924761ab4a182"), //查看密码请登录用户中心->验证码、通知短信->帐户及签名设置->APIKEY
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),
        };
        method.setRequestBody(data);
        try {
            client.executeMethod(method);

            String SubmitResult =method.getResponseBodyAsString();

            Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();

            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");

            if("2".equals(code)){
                return mobile_code;
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
