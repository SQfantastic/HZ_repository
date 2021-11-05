package org.lhz.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lhz.entity.SysRole;
import org.lhz.entity.SysUser;
import org.lhz.service.SysLogInfoService;
import org.lhz.service.SysRoleService;
import org.lhz.service.SysUserService;
import org.lhz.service.impl.SysLogInfoServiceImpl;
import org.lhz.service.impl.SysRoleServiceImpl;
import org.lhz.service.impl.SysUserServiceImpl;
import org.lhz.vo.SysLogInfoVo;
import org.lhz.vo.SysRoleVo;
import org.lhz.vo.SysUserVo;
import utils.DataGridView;
import utils.SysTips;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/system/user")
public class SysUserController extends HttpServlet {

    //注入service对象
    private SysUserService sysUserService = new SysUserServiceImpl();
    private SysRoleService sysRoleService = new SysRoleServiceImpl();
    private SysLogInfoService sysLogInfoService = new SysLogInfoServiceImpl();
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
            case"logout":
                logout(req,resp);
                break;
            case"register":
                register(req,resp);
                break;
            case"loadAllUser":
                loadAllUser(req,resp);
                break;
            case"deleteUserById":
                deleteUserById(req,resp);
                break;
            case"resetUserPwd":
                resetUserPwd(req,resp);
                break;
            case"addUser":
                addUser(req,resp);
                break;
            case"updateUser":
                updateUser(req,resp);
                break;
            case"grantUserRole":
                grantUserRole(req,resp);
                break;
            case"loadUserRoleById":
                loadUserRoleById(req,resp);
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
            SysUser sysUser = new SysUser(loginname, pwd);
            SysUser user = sysUserService.findUserByUsernameAndPassword(sysUser);
            if (user != null) {
                //这里把user写入到session中，前台需要拿到这个数据进行展示，user.realname
                req.getSession().setAttribute("user",user);
                resp.sendRedirect(req.getContextPath()+"/index.jsp");
                //获取登录信息，写入数据库
                //记录登录日志
                SysLogInfoVo sysLogInfoVo = new SysLogInfoVo();
                sysLogInfoVo.setLogintime(new Date());
                sysLogInfoVo.setLoginname(user.getRealname()+"-"+user.getLoginname());
                //获取本机ip
                sysLogInfoVo.setLoginip(req.getRemoteAddr());
                sysLogInfoService.addLogInfo(sysLogInfoVo);
            }else{
                req.setAttribute("errorMsg",SysTips.USER_LOGIN_ERROR_MSG);
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }
        }else{
            req.setAttribute("errorMsg", SysTips.CHECK_CODE_ERROR_MSG);
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }

    /**
     * Infor: 退出登录
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/2 0:15
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        //清除session和参数
        req.getSession().invalidate();
        req.getSession().removeAttribute("user");
        //重定向到登录页面
        resp.sendRedirect(req.getContextPath()+"/login.jsp");

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

    /**
     * Infor: 查询所有用户
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 16:07
     */
    private void loadAllUser(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        //获取请求参数
        Integer page = Integer.valueOf(req.getParameter("page"));
        Integer limit = Integer.valueOf(req.getParameter("limit"));
        String realname = req.getParameter("realname");
        String loginname = req.getParameter("loginname");
        String identity = req.getParameter("identity");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String sexStr = req.getParameter("sex");
        Integer sex = null;
        if (sexStr!= null) {
            sex = Integer.valueOf(sexStr);
        }
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setLimit(limit);
        sysUserVo.setPage(page);
        sysUserVo.setAddress(address);
        sysUserVo.setIdentity(identity);
        sysUserVo.setRealname(realname);
        sysUserVo.setLoginname(loginname);
        sysUserVo.setPhone(phone);
        sysUserVo.setSex(sex);
        //调用service层方法
        List<SysUser> userList= sysUserService.findAllUserList(sysUserVo);
        //设置分页参数
        Long total = sysUserService.getTotal();
        DataGridView dataGridView = new DataGridView(total,userList);
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));

    }

    /**
     * Infor: 根据用户id删除用户
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 16:07
     */
    private void deleteUserById(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        //获取参数
        Integer userid = Integer.valueOf(req.getParameter("userid"));
        HashMap<String, Object> map = new HashMap<>();
        if (sysUserService.deleteUserById(userid)>0) {
            map.put("msg",SysTips.DELETE_SUCCESS);
        }else {
            map.put("msg",SysTips.DELETE_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }

    /**
     * Infor: 重置用户密码
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 18:35
     */
    private void resetUserPwd(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        Integer userid = Integer.valueOf(req.getParameter("userid"));
        HashMap<String, Object> map = new HashMap<>();
        if (sysUserService.resetUserPwd(userid)>0) {
            map.put("msg",SysTips.RESET_SUCCESS);
        }else {
            map.put("msg",SysTips.RESET_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }

    /**
     * Infor: 添加用户信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 16:09
     */
    private void addUser(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        //获取请求参数
        String realname = req.getParameter("realname");
        String loginname = req.getParameter("loginname");
        String identity = req.getParameter("identity");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        Integer sex = Integer.valueOf(req.getParameter("sex"));
        Integer available = Integer.valueOf(req.getParameter("available"));
        String position = req.getParameter("position");
        SysUser sysUser = new SysUser();
        sysUser.setAvailable(available);
        sysUser.setIdentity(identity);
        sysUser.setAddress(address);
        sysUser.setLoginname(loginname);
        sysUser.setRealname(realname);
        sysUser.setPhone(phone);
        sysUser.setSex(sex);
        sysUser.setPosition(position);
        //设置type默认为2
        sysUser.setType(2);
        sysUser.setPwd("123456");
        HashMap<String, Object> map = new HashMap<>();
        if (sysUserService.addUser(sysUser)>0) {
            map.put("msg", SysTips.ADD_SUCCESS);
        }else{
            map.put("msg",SysTips.ADD_ERROR );
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }

    /**
     * Infor: 更新用户信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 16:07
     */
    private void updateUser(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        //获取请求参数
        Integer userid = Integer.valueOf(req.getParameter("userid"));
        String realname = req.getParameter("realname");
        String loginname = req.getParameter("loginname");
        String identity = req.getParameter("identity");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        Integer sex = Integer.valueOf(req.getParameter("sex"));
        Integer available = Integer.valueOf(req.getParameter("available"));
        String position = req.getParameter("position");
        SysUser sysUser = new SysUser();
        sysUser.setUserid(userid);
        sysUser.setAvailable(available);
        sysUser.setIdentity(identity);
        sysUser.setAddress(address);
        sysUser.setLoginname(loginname);
        sysUser.setRealname(realname);
        sysUser.setPhone(phone);
        sysUser.setSex(sex);
        sysUser.setPosition(position);
        HashMap<String, Object> map = new HashMap<>();
        if (sysUserService.updateUser(sysUser)>0) {
            map.put("msg", SysTips.UPDATE_SUCCESS);
        }else{
            map.put("msg",SysTips.UPDATE_ERROR );
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }

    /**
     * Infor:给用户分配角色
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 16:16
     */
    private void grantUserRole(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        //获取请求参数
        Integer userid = Integer.valueOf(req.getParameter("userid"));
        String roleids = req.getParameter("rids");
        HashMap<String, Object> map = new HashMap<>();
        if (sysRoleService.insertRoleByUserId(userid,roleids)) {
            map.put("msg",SysTips.DISPATCH_SUCCESS);
        }else{
            map.put("msg",SysTips.DISPATCH_ERROR);
        }
        //输出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.close();
    }

    /**
     * Infor: 根据用户id查询到该用户所拥有的角色
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 16:51
     */
    private void loadUserRoleById(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        //获取请求参数
        Integer userid = Integer.valueOf(req.getParameter("userid"));
        //查询所有的角色信息
        SysRoleVo sysRoleVo = new SysRoleVo();
        List<SysRole> allRoleList = sysRoleService.findAllRoleList(sysRoleVo);
        //根据当前用户id查询该用户所有的权限
        List<SysRole> roleListByUserId = sysRoleService.findRoleListByUserId(userid);
        //这里存放到一个普通的集合中就行了
        ArrayList<Object> data = new ArrayList<>();
        for (SysRole allRole : allRoleList) {
            //这里的这个字段根layui要求的字段一致
            Boolean LAY_CHECKED = false;
            for (SysRole sysRole : roleListByUserId) {
                if (allRole.getRoleid()==sysRole.getRoleid()) {
                    LAY_CHECKED = true;
                }
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("roleid", allRole.getRoleid());
            map.put("rolename", allRole.getRolename());
            map.put("roledesc", allRole.getRoledesc());
            map.put("LAY_CHECKED",LAY_CHECKED);
            data.add(map);
        }
        //输出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(new DataGridView(data)));
    }



}
