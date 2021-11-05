package org.lhz.controller;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import org.lhz.entity.SysUser;
import org.lhz.service.SysProfileAndPasswordService;
import org.lhz.service.impl.SysProfileAndPasswordServiceImpl;
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

@WebServlet("/system/change")
public class SysProfileAndPasswordChange extends HttpServlet  {

    //注入service层对象
    private SysProfileAndPasswordService sysProfileAndPasswordService = new SysProfileAndPasswordServiceImpl();
    private SysUserService sysUserService = new SysUserServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        switch (method) {
            case "changePassword":
                changePassword(req, resp);
                break;
            case "loadProfile":
                loadProfile(req, resp);
                break;
            case "updateProfile":
                updateProfile(req, resp);
                break;
        }
    }
    /**
         * Infor: 右上角更改用户密码
         * @param req
         * @param resp
         * @return : void
         * @author : LHZ
         * @date : 2021/11/3 22:57
         */
        private void changePassword(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException  {
            //获取参数
            String oldPassword = req.getParameter("oldPassword");
            String newPassword = req.getParameter("newPassword");
            SysUser user = (SysUser)req.getSession().getAttribute("user");
            //获取输出流
            HashMap<Object, Object> map = new HashMap<>();
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter writer = resp.getWriter();
            if (user.getPwd().equals(SecureUtil.md5(oldPassword))) {
                SysUser sysUser = new SysUser();
                sysUser.setUserid(user.getUserid());
                sysUser.setPwd(SecureUtil.md5(newPassword));
                if (sysProfileAndPasswordService.updatePassword(sysUser)>0) {
                    map.put("msg", SysTips.UPDATE_SUCCESS);
                }else{
                    map.put("msg",SysTips.UPDATE_ERROR);
                }

            }else{
                //旧密码错误
                map.put("msg",SysTips.WRONG_OLD_PWD);
            }
            writer.write(JSON.toJSONString(map));
            writer.close();
        }
        /**
         * Infor: 查询用户信息,用于修改信息回显
         * @param req
         * @param resp
         * @return : void
         * @author : LHZ
         * @date : 2021/11/3 23:35
         */
        private void loadProfile(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException  {
            //获取参数
            SysUser user = (SysUser)req.getSession().getAttribute("user");
            //这里必须要再查一次,不然回显是上一次的数据
            Integer userid = user.getUserid();
            sysUserService.findUserByUserId (userid);
            //写出数据
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter writer = resp.getWriter();
            writer.write(JSON.toJSONString(user));
            writer.close();

        }

        private void updateProfile(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException  {
            //获取用户信息
            String realname = req.getParameter("realname");
            String loginname = req.getParameter("loginname");
            String identity = req.getParameter("identity");
            String address = req.getParameter("address");
            String phone = req.getParameter("phone");
            String sexStr = req.getParameter("sex");
            SysUser user =(SysUser) req.getSession().getAttribute("user");
            Integer sex =null;
            if (sexStr != null) {
                sex = Integer.valueOf(sexStr);
            }
            SysUser sysUser = new SysUser();
            sysUser.setRealname(realname);
            sysUser.setIdentity(identity);
            sysUser.setLoginname(loginname);
            sysUser.setAddress(address);
            sysUser.setPhone(phone);
            sysUser.setUserid(user.getUserid());
            HashMap<Object, Object> map = new HashMap<>();
            if (sysProfileAndPasswordService.updateProfile(sysUser)>0) {
                map.put("msg", SysTips.UPDATE_SUCCESS);
            }else{
                map.put("msg",SysTips.UPDATE_ERROR);
            }
            //写出数据
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter writer = resp.getWriter();
            writer.write(JSON.toJSONString(map));
            writer.close();


        }

}
