package cn.lifuyi.modules.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lifuyi on 2018/1/15 0015.
 */
public class LoginInterceptor implements HandlerInterceptor{

    //不拦截的请求
    public String[] allowUrls;

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }
    /**
     * @Author:lifuyi
     * @Descripution:该方法将在请求处理之前进行调用，只有该方法返回true，才会继续执行后续的Interceptor和Controller,当返回值为true, 时就会继续调用下一个Interceptor的preHandle 方法，
     * 如果已经是最后一个Interceptor的时候就会是调用当前请求的Controller方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        String requestType = request.getHeader("X-Requested-With");
        System.out.println("requestUrl:" + requestUrl);
        if (null != allowUrls && allowUrls.length >= 1) {
            for (String url : allowUrls) {
                if (requestUrl.contains(url)) {
                    return true;
                }
            }
        }
        if(requestUrl.indexOf("userLogin")>-1 || requestUrl.indexOf("sendEmail") > -1 || requestUrl.indexOf("register") > -1||
                requestUrl.indexOf("query") > -1
                ){
            return true;
        }
        //登录拦截
//        HttpSession session = request.getSession();
//        String adminUserName = (String) session.getAttribute("adminUserName");
//        if (StringUtils.isBlank(adminUserName)) {
//            response.sendRedirect(ServletInit.getAdminBaseUrl() + "adminLogin");
//            return false;
//        }
//        if (userService.findByUserName(adminUserName) == null) {
//            response.sendRedirect(ServletInit.getAdminBaseUrl() + "adminLogin");
//            return false;
//        }
        return false;
    }
    /**
     * @Author:lifuyi
     * @Descripution:该方法将在请求处理之后，DispatcherServlet进行视图返回渲染之前进行调用，可以在这个方法中对Controller处理之后的ModelAndView对象进行操作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
    /**
     * @Author:lifuyi
     * @Descripution:该方法将在整个请求结束之后，也就是在DispatcherServlet渲染了对应的视图之后执行。用于进行资源清理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
