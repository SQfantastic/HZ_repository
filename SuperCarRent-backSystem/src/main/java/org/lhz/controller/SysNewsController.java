package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lhz.entity.SysNews;
import org.lhz.service.SysNewsService;
import org.lhz.service.impl.SysNewsServiceImpl;
import org.lhz.vo.SysNewsVo;
import utils.DataGridView;

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
import java.util.List;

@WebServlet("/system/news")
public class SysNewsController extends HttpServlet {
    //注入service层对象
    private SysNewsService sysNewsService = new SysNewsServiceImpl();
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
        PageHelper.startPage(sysNewsVo.getPage(), sysNewsVo.getLimit());
        PageInfo<SysNews> pageInfo = new PageInfo<>(newsList);
        DataGridView dataGridView = new DataGridView(pageInfo.getTotal(), pageInfo.getList());
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));

    }

    private void loadNewsById(HttpServletRequest req, HttpServletResponse resp) {
    }


    private void deleteNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }

    private void addNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }

    private void updateNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

    }
}
