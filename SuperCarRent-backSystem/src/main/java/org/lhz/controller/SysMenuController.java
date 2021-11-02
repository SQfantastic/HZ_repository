package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lhz.entity.SysMenu;
import org.lhz.entity.SysUser;
import org.lhz.service.SysMenuService;
import org.lhz.service.impl.SysMenuServiceImpl;
import org.lhz.vo.SysMenuVo;
import utils.DataGridView;
import utils.SysTips;
import utils.TreeBuilder;
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

/**
 * Infor: 主页面菜单管理
 * @param
 * @return :
 * @author : LHZ
 * @date : 2021/10/27 20:32
 */
@WebServlet("/system/menu")
public class SysMenuController extends HttpServlet {
    //注入service层对象
    private SysMenuService sysMenuService = new SysMenuServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        switch(method){
            case "loadIndexLeftMenu":
                loadIndexLeftMenu(req,resp);
                break;
            case"loadMenuManagerLeftTree":
                loadMenuManagerLeftTree(req,resp);
                break;
            case"loadAllMenu":
                loadMenuMenu(req,resp);
                break;
            case"checkMenuHasChildren":
                checkMenuHasChildren(req,resp);
                break;
            case"deleteMenuById":
                deleteMenuById(req,resp);
                break;
            case"addMenu":
                addMenu(req,resp);
                break;
            case"updateMenu":
                updateMenu(req,resp);
                break;
        }
    }


    /**
     * Infor: 查询首页的所有菜单信息
     *          根据用户的权限类型不同查询的数据也不同
     * @param req
     * @param resp
     * @return : java.util.List<utils.TreeNode>
     * @author : LHZ
     * @date : 2021/10/28 8:40
     */
    private void loadIndexLeftMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取到登录页面的用户姓名在首页上展示
        SysUser user = (SysUser)req.getSession().getAttribute("user");
        SysMenuVo sysMenuVo = new SysMenuVo();
        List<SysMenu> menuList = null;
        //设置菜单为可用的，1为可用，0为不可用
        sysMenuVo.setAvailable(1);
        //根据不同的用户角色类型展示不同的首页信息，默认的1可以查询到所有的菜单信息
        //其他的用户根据id查询到对应的权限才是你信息
        if (user.getType()==1) {
            menuList = sysMenuService.findAllMenuList(sysMenuVo);
        }else {
            menuList = sysMenuService.findMenuByUserId(user.getUserid());
        }
        //新建一个类型为TreeNode的集合遍历查询出来的菜单信息并将数据写入到该集合中
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (SysMenu menu : menuList) {
            Integer id = menu.getId();
            Integer pid = menu.getPid();
            String title = menu.getTitle();
            String icon = menu.getIcon();
            String href = menu.getHref();
            Boolean spread = (menu.getSpread()==1);

            //将查询到的数据放入到treeNode集合中去
            treeNodes.add(new TreeNode(id,pid,icon,title,href,spread));
        }
        //现在各个菜单之间是没有层级关系的，所以需要借助TreeBuilder工具类
        //将菜单转化成具有层级关系的json数据，需要指定父菜单的id值
        Integer pid=1;
        List<TreeNode> list = TreeBuilder.build(treeNodes, pid);
        //将数据转为json
        String json = JSON.toJSONString(list);
        //将数据以json的形式写出去
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.close();
    }

    /**
     * Infor:显示菜单管理页面左边的菜单树
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/29 17:18
     */
    private void loadMenuManagerLeftTree(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //查询所有的菜单列表(不需要分页)
        List<SysMenu> allMenuList = sysMenuService.findAllMenuList(null);
        //创建一个TreeNode类型的集合存储
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();
        for (SysMenu result : allMenuList) {
            //设置参数
            Integer id = result.getId();
            Integer pid = result.getPid();
            String title = result.getTitle();
            boolean spread = result.getSpread() == 1;
            //写进treenode集合中
            list.add(new TreeNode(id, pid,  title, spread));
        }
        DataGridView dataGridView = new DataGridView(list);
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));
        writer.close();
    }

    /**
     * Infor: 查询右侧表格中所有的菜单数据，包括表头中的模糊查询
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/30 11:38
     */
    private void loadMenuMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取请求参数
        SysMenuVo sysMenuVo = new SysMenuVo();
        sysMenuVo.setLimit(Integer.valueOf(req.getParameter("limit")));
        sysMenuVo.setPage(Integer.valueOf(req.getParameter("page")));
        sysMenuVo.setTitle(req.getParameter("title"));
        //判断id是否为空值
        String idStr = req.getParameter("id");
        Integer id =null;
        if (idStr != null) {
            id = Integer.valueOf(idStr);
        }
        sysMenuVo.setId(id);
        //设置分页参数
        PageHelper.startPage(sysMenuVo.getPage(),sysMenuVo.getLimit());
        //查询所有的菜单数据，包括标头的模糊查询
        List<SysMenu> sysMenuList = sysMenuService.findAllMenu(sysMenuVo);
        //创建一个pageInfo对象
        PageInfo<SysMenu> pageInfo = new PageInfo<SysMenu>(sysMenuList);
        //封装成layui对应的格式数据
        DataGridView dataGridView = new DataGridView(pageInfo.getTotal(), pageInfo.getList());
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));
        writer.close();

    }
    /**
     * Infor: 通过菜单id查询该菜单下事发后存在子菜单，存在的话就不能删除
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/30 21:53
     */
    private void checkMenuHasChildren(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        //获取请求参数
        Integer id = Integer.valueOf(req.getParameter("id"));
        HashMap<String,Object> map = new HashMap<>();
        //验证该菜单是否有子菜单
        if (sysMenuService.checkMenuById(id)>0) {
            map.put("code",0);
        }else{
            map.put("code",-1);
        }
        //以json格式写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.close();
    }
    /**
     * Infor: 根据菜单id删除菜单那，前提是已经验证过了该菜单下没有子菜单
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/30 21:55
     */
    private void deleteMenuById(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        //获取请求参数
        Integer id = Integer.valueOf(req.getParameter("id"));
        HashMap<Object, Object> map = new HashMap<>();
        //判断是否删除成功
        if (sysMenuService.deleteMenuById(id)>0) {
            map.put("msg", SysTips.DELETE_SUCCESS);
        }else{
            map.put("msg",SysTips.DELETE_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }

    /**
     * Infor:
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/31 8:24
     */
    private void addMenu(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        //获取请求参数
        Integer pid = Integer.valueOf(req.getParameter("pid"));
        String title = req.getParameter("title");
        String href = req.getParameter("href");
        String icon = req.getParameter("icon");
        String target = req.getParameter("target");
        Integer spread = Integer.valueOf(req.getParameter("spread"));
        Integer available = Integer.valueOf(req.getParameter("available"));
        SysMenu sysMenu = new SysMenu(null, pid, title, href, spread, target, icon, available);
        HashMap<String, Object> map = new HashMap<>();
        if (sysMenuService.addMenu(sysMenu)>0) {
            map.put("msg",SysTips.ADD_SUCCESS);
        }else{
            map.put("msg",SysTips.ADD_ERROR );
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }

    /**
     * Infor:
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/10/31 11:57
     */
    private void updateMenu(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        //获取请求参数
        Integer id = Integer.valueOf(req.getParameter("id"));
        Integer pid = Integer.valueOf(req.getParameter("pid"));
        String title = req.getParameter("title");
        String href = req.getParameter("href");
        String icon = req.getParameter("icon");
        String target = req.getParameter("target");
        Integer spread = Integer.valueOf(req.getParameter("spread"));
        Integer available = Integer.valueOf(req.getParameter("available"));
        SysMenu sysMenu = new SysMenu(id, pid, title, href, spread, target, icon, available);
        HashMap<String, Object> map = new HashMap<>();
        //调用service层的方法
        if (sysMenuService.updateMenu(sysMenu)>0) {
            map.put("msg",SysTips.UPDATE_SUCCESS);
        }else{
            map.put("msg",SysTips.UPDATE_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }
    /**
     * Infor: 通过反射机制将json数据转换成对应的实体类对象
     * @param req
     * @param clazz
     * @return : java.lang.Object
     * @author : LHZ
     * @date : 2021/10/30 14:56
     */
//    private Object parseJSON(HttpServletRequest req, Class<?> clazz) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
//        String line = null;
//        StringBuilder sb = new StringBuilder();
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        return mapper.readValue(sb.toString().substring(0, (sb.toString()).length() - 1), clazz);
//    }

}
