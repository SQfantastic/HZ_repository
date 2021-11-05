package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lhz.entity.SysNews;
import org.lhz.entity.SysUser;
import org.lhz.service.SysNewsService;
import org.lhz.service.impl.SysNewsServiceImpl;
import org.lhz.vo.SysNewsVo;
import utils.DataGridView;
import utils.SysTips;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/system/news")
public class SysNewsController extends HttpServlet {
    //注入service层对象
    private SysNewsService sysNewsService = new SysNewsServiceImpl();
    /**
     * Infor:
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/2 15:08
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        //根据请求参数分别处理请求
        switch(method) {
            case "loadAllNews":
                loadAllNews(req, resp);
                break;
            case"loadNewsById":
                loadNewsById(req,resp);
                break;
            case "deleteNews":
                deleteNews(req, resp);
                break;
            case "addNews":
                addNews(req, resp);
                break;
            case "updateNews":
                updateNews(req, resp);
                break;
        }
    }

    /**
     * Infor: 查询所有的新闻信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/2 15:08
     */
    private void loadAllNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取请求参数
        Integer page = Integer.valueOf(req.getParameter("page"));
        Integer limit = Integer.valueOf(req.getParameter("limit"));
        String title = req.getParameter("title");
        String startTimeStr = req.getParameter("startTime");
        String endTimeStr = req.getParameter("endTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String content = req.getParameter("content");
        Date endTime=null;
        Date startTime =null;
        try {
            if (startTimeStr != null) {
                startTime = sdf.parse(startTimeStr.trim());
            }
            if (endTimeStr != null) {
                endTime = sdf.parse(endTimeStr.trim());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SysNewsVo sysNewsVo = new SysNewsVo();
        sysNewsVo.setLimit(limit);
        sysNewsVo.setPage(page);
        sysNewsVo.setTitle(title);
        sysNewsVo.setStartTime(startTime);
        sysNewsVo.setEndTime(endTime);
        sysNewsVo.setContent(content);
        //调用service层方法
        List<SysNews> newsList= sysNewsService.findAllNewsList(sysNewsVo);
        //设置分页参数
        Long total = sysNewsService.getTotal();
        DataGridView dataGridView = new DataGridView(total, newsList);
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));

    }

    /**
     * Infor: 根据新闻id查询到新闻信息，供后台首页斤进行展示
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/2 10:57
     */
    private void loadNewsById(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
        Integer id = Integer.valueOf(req.getParameter("id"));
        SysNews newsById = sysNewsService.findNewsById(id);
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(newsById));
    }


    /**
     * Infor: 根据新闻的id删除新闻信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/2 11:02
     */
    private void deleteNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取参数
        Integer id = Integer.valueOf(req.getParameter("id"));
        HashMap<String, Object> map = new HashMap<>();
        if (sysNewsService.deleteNewsById(id)>0) {
            map.put("msg", SysTips.DELETE_SUCCESS);
        }else {
            map.put("msg",SysTips.DELETE_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }

    /**
     * Infor: 添加新闻信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/2 15:09
     */
    private void addNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取请求参数
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        SysUser user = (SysUser)req.getSession().getAttribute("user");
        SysNews sysNews = new SysNews();
        sysNews.setTitle(title);
        sysNews.setContent(content);
        sysNews.setCreatetime(new Date());
        sysNews.setOpername(user.getRealname());
        HashMap<String, Object> map = new HashMap<>();
        if (sysNewsService.addNews(sysNews)>0) {
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
     * Infor: 更新新闻信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/2 15:09
     */
    private void updateNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取请求参数
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Integer id = Integer.valueOf(req.getParameter("id"));
        SysNews sysNews = new SysNews();
        sysNews.setTitle(title);
        sysNews.setContent(content);
        sysNews.setId(id);
        HashMap<String, Object> map = new HashMap<>();
        if (sysNewsService.updateNews(sysNews)>0) {
            map.put("msg", SysTips.UPDATE_SUCCESS);
        }else{
            map.put("msg",SysTips.UPDATE_ERROR );
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }
}
