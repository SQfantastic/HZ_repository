package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import org.lhz.entity.SysMenu;
import org.lhz.entity.SysUser;
import org.lhz.service.SysMenuService;
import org.lhz.service.impl.SysMenuServiceImpl;
import org.lhz.vo.SysMenuVo;
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
 * @param null
 * @return :
 * @author : LHZ
 * @date : 2021/10/27 20:32
 */
@WebServlet("/menu")
public class SysMenuController extends HttpServlet {
    //注入service层对象
    private SysMenuService sysMenuService = new SysMenuServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch(method){
            case "loadIndexLeftMenu":
                loadIndexLeftMenu(req,resp);
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
        HashMap<String, Object> map = new HashMap<>();
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
    }
}
