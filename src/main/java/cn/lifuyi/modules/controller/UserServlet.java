package cn.lifuyi.modules.controller;

import cn.lifuyi.modules.entity.LoginNameCheck;
import cn.lifuyi.modules.entity.Role;
import cn.lifuyi.modules.entity.User;
import cn.lifuyi.modules.service.RoleService;
import cn.lifuyi.modules.service.UserService;
import cn.lifuyi.utils.DateUtil;
import cn.lifuyi.utils.MD5Util;
import cn.lifuyi.utils.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by lifuyi on 2018/1/15 0015.
 */
@Controller
@RequestMapping("/user")
@SessionAttributes(value = {"admin","roleList"})
public class UserServlet {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    /**
    *@Author:lifuyi
    *@Descripution:登录请求
    */
    @RequestMapping("/login")
    public String login(User user, ModelMap modelMap){
        User admin=userService.login(user);
        if(admin!=null){
            //登录成功
            modelMap.addAttribute("admin",admin);
            List<Role> roleList=roleService.findAll();
            modelMap.addAttribute("roleList",roleList);
            return "main";
        }else {
            //登录失败
            return "loginFail";
        }
    }
    /**
    *@Author:lifuyi
    *@Descripution:查询用户
    */
    @RequestMapping("/query")
    public ModelAndView query(User user){
        ModelAndView modelAndView=new ModelAndView();
        if (user.getSex()==5){
            user.setSex(null);
        }
        List<User> userList=userService.findByParamVer(user);
        if(userList.size()!=0){
            modelAndView.addObject("allUserList",userList);
            modelAndView.setViewName("user");
        }else {
            Result result=new Result();
            result.setMessage("对不起，没有满足条件的用户");
            modelAndView.addObject("result",result);
            modelAndView.setViewName("usersucess");
        }
        return modelAndView;
    }
    /**
    *@Author:lifuyi
    *@Descripution:查询所有用户
    */
    @RequestMapping("/displayAll")
    public ModelAndView displayAll(HttpSession session){
        //考虑用缓存优化ehcahe,尽量采用拦截器
        ModelAndView modelAndView=new ModelAndView("user");
        User user= (User) session.getAttribute("admin");
        List<User> userList=userService.findAll();
        modelAndView.addObject("allUserList",userList);
        return modelAndView;
    }
    /**
    *@Author:lifuyi
    *@Descripution:激活用户
    */
    @RequestMapping("/active")
    public ModelAndView active(String id){
        ModelAndView modelAndView=new ModelAndView("usersucess");
        Result result=userService.active(id);
        modelAndView.addObject("result",result);
        return modelAndView;
    }
    /**
    *@Author:lifuyi
    *@Descripution:删除用户
    */
    @RequestMapping("/delete")
    public ModelAndView delete(String id){
        ModelAndView modelAndView=new ModelAndView("usersucess");
        Result result=userService.delete(id);
        modelAndView.addObject("result",result);
        return modelAndView;
    }
    /**
    *@Author:lifuyi
    *@Descripution:添加校验
    */
    @RequestMapping("/checkLoginName")
    public @ResponseBody boolean checkLoginName( @RequestBody String loginName){
        User user=new User();
        loginName=loginName.substring(loginName.indexOf("=")+1);
        user.setLoginName(loginName);
        boolean flag=true;
        if(userService.checkLoginName(user)){
            flag=false;
        }
        return flag;
    }
    /**
    *@Author:lifuyi
    *@Descripution:修改用户
    */
    @RequestMapping("/update")
    public ModelAndView update(User user){
        ModelAndView modelAndView=new ModelAndView("usersucess");
        User findUser=new User();
        findUser.setId(user.getId());
        List<User> userList=userService.findByParam(findUser);
        if(!user.getPassword().equals(userList.get(0).getPassword())){
            user.setPassword(MD5Util.GetMD5Code(user.getPassword()));
        }
        //修改用户
        Result result=userService.update(user);
        modelAndView.addObject("result",result);
        return modelAndView;
    }
    /**
    *@Author:lifuyi
    *@Descripution:修改时验证
    */
    @RequestMapping("/checkModifyLoginName")
    public @ResponseBody boolean checkModifyLoginName(String oldLoginName,String loginName){
        System.out.println(oldLoginName);
        boolean flag=false;
        if(!oldLoginName.equals(loginName)){
            User user=new User();
            user.setLoginName(loginName);
            if(!userService.checkLoginName(user)){
                //可以添加
                flag=true;
            }else {
                flag=false;
            }
        }else {
            flag=true;
        }
        return flag;
    }
    /**
    *@Author:lifuyi
    *@Descripution:添加用户
    */
    @RequestMapping("/addUser")
    public ModelAndView addUser(User user){
        ModelAndView modelAndView=new ModelAndView("usersucess");
        user.setPassword(MD5Util.GetMD5Code(user.getPassword()));
        user.setStatus(1);
        user.setRegistTime(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss"));
        String id = UUID.randomUUID().toString() + System.currentTimeMillis() + Math.random() * 10000;
        user.setId(id);
        Role role=roleService.findByName("普通用户");
        user.setRole(role);
        Result result=userService.addUser(user);
        modelAndView.addObject("result",result);
        return modelAndView;
    }
    /**
     * @Author:lifuyi
     * @Descripution:生成验证码
     */
    @RequestMapping("/imageServlet")
    public void imageServlet(HttpServletResponse response, HttpSession session) throws IOException {
        BufferedImage bfi = new BufferedImage(80, 25, BufferedImage.TYPE_INT_RGB);
        Graphics g = bfi.getGraphics();
        g.fillRect(0, 0, 80, 25);
        //验证码字符范围
        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        Random r = new Random();
        int index;
        StringBuffer sb = new StringBuffer(); //保存字符串
        for (int i = 0; i < 4; i++) {
            index = r.nextInt(ch.length);
            g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            Font font = new Font("宋体", 30, 20);
            g.setFont(font);
            g.drawString(ch[index] + "", (i * 20) + 2, 23);
            sb.append(ch[index]);
        }
        // 添加噪点
        int area = (int) (0.02 * 80 * 25);
        for (int i = 0; i < area; ++i) {
            int x = (int) (Math.random() * 80);
            int y = (int) (Math.random() * 25);
            bfi.setRGB(x, y, (int) (Math.random() * 255));
        }
        //设置验证码中的干扰线
        for (int i = 0; i < 6; i++) {
            //随机获取干扰线的起点和终点
            int xstart = (int) (Math.random() * 80);
            int ystart = (int) (Math.random() * 25);
            int xend = (int) (Math.random() * 80);
            int yend = (int) (Math.random() * 25);
            g.setColor(interLine(1, 255));
            g.drawLine(xstart, ystart, xend, yend);
        }
        session.setAttribute("verificationCode", sb.toString());
        System.out.println(sb.toString());
        response.setContentType("image/jpg");
        ImageIO.write(bfi, "JPG", response.getOutputStream());  //写到输出流
    }
    /**
     * @Author:lifuyi
     * @Descripution:随机产生干扰线颜色的方法
     */
    private static Color interLine(int Low, int High) {
        if (Low > 255)
            Low = 255;
        if (High > 255)
            High = 255;
        if (Low < 0)
            Low = 0;
        if (High < 0)
            High = 0;
        int interval = High - Low;
        int r = Low + (int) (Math.random() * interval);
        int g = Low + (int) (Math.random() * interval);
        int b = Low + (int) (Math.random() * interval);
        return new Color(r, g, b);
    }
    /**
    *@Author:lifuyi
    *@Descripution:校验验证码
    */
    @RequestMapping("/verificationCode")
    public @ResponseBody boolean verificationCode(HttpSession session, @RequestBody String code){
        String verificationCode= (String) session.getAttribute("verificationCode");
        code=code.substring(code.indexOf("=")+1);
        boolean flag;
        if(code.trim().equals(verificationCode.trim())){
            flag=true;
        }else {
            flag=false;
        }
        return flag;
    }
}
