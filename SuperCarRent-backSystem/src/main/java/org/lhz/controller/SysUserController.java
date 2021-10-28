package org.lhz.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSON;
import org.lhz.entity.SysUser;
import org.lhz.service.SysUserService;
import org.lhz.service.impl.SysUserServiceImpl;
import utils.SysTips;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/user")
public class SysUserController extends HttpServlet {

    //注入service对象
    private SysUserService sysUserService = new SysUserServiceImpl();
    String code = null;
    /**
     * Infor:判断请求参数
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/25 22:51
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        //根据请求参数分别处理请求
        switch(method){
            case"login":
                login(req,resp);
                break;
            case"getCode":
                getCode(req,resp);
                break;
            case"checkUser":
                checkUser(req,resp);
                break;
            case"register":
                register(req,resp);
                break;
        }
    }

    /**
    * Infor:
    * @param req
    * @param resp
    * @return : void
    * @author : LHZ
    * @date : 2021/10/26 9:41
    */
    public void getCode(HttpServletRequest req, HttpServletResponse resp) {
        // 定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36, 4, 5);
        //获取验证码的值
        code = lineCaptcha.getCode();
        try {
            lineCaptcha.write(resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Infor: 异步用户名验重
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/27 9:41
     */
    protected void checkUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        System.out.println(username);
        SysUser users = sysUserService.findUserByUsername(username);
        String msg = null;
        Integer code =0;
        HashMap<String, Object> map = new HashMap<>();
        if (users != null) {
            map.put("msg","用户名重复，不可用！");
            map.put("code",500);
        }else {
            map.put("msg","用户名可用！");
            map.put("code",200);
        }
        resp.setContentType("application/json;charset=utf-8");
        String json = JSON.toJSONString(map);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
    }

    /**
     * Infor:验证登录
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/27 8:57
     */
    protected void login(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        String currentCode = req.getParameter("code");
        if (code.equals(currentCode)) {
            String loginname = req.getParameter("loginname");
            String pwd = req.getParameter("pwd");
            System.out.println(loginname+pwd);
            SysUser sysUser = new SysUser(loginname, pwd);
            SysUser user = sysUserService.findUserByUsernameAndPassword(sysUser);
            if (user != null) {
                //这里把user写入到session中，前台需要拿到这个数据进行展示，user.realname
                req.getSession().setAttribute("user",user);
                resp.sendRedirect(req.getContextPath()+"/index.jsp");
            }else{
                req.setAttribute("errorMsg",SysTips.USER_LOGIN_ERROR_MSG);
                req.getRequestDispatcher("login.jsp").forward(req,resp);
            }
        }else{
            req.setAttribute("errorMsg", SysTips.CHECK_CODE_ERROR_MSG);
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    /**
     * Infor: 用户注册
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/27 11:50
     */
    private void register(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        String username = req.getParameter("loginname");
        String pwd = req.getParameter("pwd");
        String currentCode = req.getParameter("code");
        SysUser sysUser = new SysUser(username, pwd);
        HashMap<String, Object> map = new HashMap<>();
        //验证验证码
        if (code.equals(currentCode)) {
            if (sysUserService.addUser(sysUser)>0) {
                //注册成功直接重定向到登录页面
                resp.sendRedirect(req.getContextPath()+"/login.jsp");
            }else{
                //注册失败继续停留在注册页面
                map.put("msg","注册失败！");
                req.getRequestDispatcher("register.jsp").forward(req,resp);
            }
        }else{
            //回到当前注册页面
            req.setAttribute("errorMsg", SysTips.CHECK_CODE_ERROR_MSG);
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

}
