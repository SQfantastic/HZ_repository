package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lhz.entity.SysMenu;
import org.lhz.entity.SysRole;
import org.lhz.service.SysMenuService;
import org.lhz.service.SysRoleService;
import org.lhz.service.impl.SysMenuServiceImpl;
import org.lhz.service.impl.SysRoleServiceImpl;
import org.lhz.vo.SysMenuVo;
import org.lhz.vo.SysRoleVo;
import utils.DataGridView;
import utils.SysTips;
import utils.TreeNode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/system/role")
public class SysRoleController extends HttpServlet {
    //注入service层对象
    private SysRoleService sysRoleService = new SysRoleServiceImpl();
    private SysMenuService sysMenuService = new SysMenuServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        switch(method){
            case"loadAllRole":
                loadAllRole(req,resp);
                break;
            case"addRole":
                addRole(req,resp);
                break;
            case"updateRole":
                updateRole(req,resp);
                break;
            case"deleteRoleById":
                deleteRoleById(req,resp);
                break;
            case"grantRoleMenu":
                grantRoleMenu(req,resp);
                break;
            case"loadMenuDispatchTree":
                loadMenuDispatchTree(req,resp);
                break;
        }
    }
    /**初始化角色表格（包括表头模糊查询的）
     * Infor:
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/31 14:58
     */
    private void loadAllRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        Integer page = Integer.valueOf(req.getParameter("page"));
        Integer limit = Integer.valueOf(req.getParameter("limit"));
        String rolename = req.getParameter("rolename");
        String roledesc = req.getParameter("roledesc");
        String availableStr = req.getParameter("available");
        Integer available =null;
        if (availableStr!= null) {
           available =  Integer.valueOf(availableStr);
        }
        SysRoleVo sysRoleVo = new SysRoleVo();
        sysRoleVo.setPage(page);
        sysRoleVo.setLimit(limit);
        sysRoleVo.setRoledesc(roledesc);
        sysRoleVo.setRolename(rolename);
        sysRoleVo.setAvailable(available);
        //调用service层方法
        List<SysRole> roleList= sysRoleService.findAllRoleList(sysRoleVo);
        //设置分页参数
        Long total = sysRoleService.getTotal();
        DataGridView dataGridView = new DataGridView(total, roleList);
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));

    }
    /**
     * Infor: 添加角色
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/31 16:54
     */
    private void addRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        String rolename = req.getParameter("rolename");
        String roledesc = req.getParameter("roledesc");
        Integer available = Integer.valueOf(req.getParameter("available"));
        SysRole sysRole = new SysRole();
        sysRole.setRoledesc(roledesc);
        sysRole.setRolename(rolename);
        sysRole.setAvailable(available);
        HashMap<String, Object> map = new HashMap<>();
        if (sysRoleService.addRole(sysRole)>0) {
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
     * Infor: 删除角色，删除角色之前是需要先验证改角色下是否存在用户
     *        存在就不能删除
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/31 17:10
     */
    private void deleteRoleById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取参数
        Integer roleid = Integer.valueOf(req.getParameter("roleid"));
        HashMap<String, Object> map = new HashMap<>();
        if (sysRoleService.deleteRoleById(roleid)>0) {
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
     * Infor: 更新角色列表
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/31 23:33
     */
    private void updateRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        String rolename = req.getParameter("rolename");
        String roledesc = req.getParameter("roledesc");
        Integer available = Integer.valueOf(req.getParameter("available"));
        Integer roleid = Integer.valueOf(req.getParameter("roleid"));
        SysRole sysRole = new SysRole();
        sysRole.setRoledesc(roledesc);
        sysRole.setRolename(rolename);
        sysRole.setAvailable(available);
        sysRole.setRoleid(roleid);
        HashMap<String, Object> map = new HashMap<>();
        if (sysRoleService.updateRole(sysRole)>0) {
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
     * Infor: 加载菜单分配树，用于给角色分配菜单
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 0:04
     */
    private void loadMenuDispatchTree(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        Integer roleid = Integer.valueOf(req.getParameter("roleid"));
        SysMenuVo sysMenuVo = new SysMenuVo();
        //设置菜单为可用
        sysMenuVo.setAvailable(1);
        //查询所有的菜单信息
        List<SysMenu> allMenuList = sysMenuService.findAllMenuList(sysMenuVo);
        //根据roleid查询到该角色下的所有菜单信息
        List<SysMenu> menuListByRoleId = sysRoleService.findMenuListByRoleId(roleid);
        //创建一个TreeNode类型的集合用于存放数据
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        //循环比较将该角色下用的菜单放入到一个treeNode集合中，并且需要给checkArr这个属性复制为选中状态，
        //这是layui的复选框属性
        for (SysMenu sysMenu : allMenuList) {
            String checkArr ="0";
            for (SysMenu menu : menuListByRoleId) {
                if (sysMenu.getId() == menu.getId()) {
                    checkArr="1";
                    break;
                }
            }
            Integer id = sysMenu.getId();
            Integer pid = sysMenu.getPid();
            String title = sysMenu.getTitle();
            boolean spread = (sysMenu.getSpread()==1);
            treeNodes.add(new TreeNode(id,pid,title,spread,checkArr));
        }
        DataGridView dataGridView = new DataGridView(treeNodes);
        //输出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));
        writer.close();

    }

    private void grantRoleMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        Integer roleid = Integer.valueOf(req.getParameter("roleid"));
        String ids = req.getParameter("ids");
        HashMap<String, Object> map = new HashMap<>();
        if (sysMenuService.insertMenuByRoleId(roleid,ids)) {
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
}
