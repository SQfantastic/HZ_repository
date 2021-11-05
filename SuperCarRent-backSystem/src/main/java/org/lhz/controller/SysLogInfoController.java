package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.lhz.entity.SysLogInfo;
import org.lhz.service.SysLogInfoService;
import org.lhz.service.impl.SysLogInfoServiceImpl;
import org.lhz.vo.SysLogInfoVo;
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

@WebServlet("/system/logInfo")
public class SysLogInfoController extends HttpServlet {
    //注入service层对象
    private SysLogInfoService sysLogInfoService =new SysLogInfoServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        //根据请求参数分别处理请求
        switch(method){
            case"loadAllLogInfo":
                loadAllLogInfo(req,resp);
                break;
            case"deleteLogInfo":
                deleteLogInfo(req,resp);
                break;
        }
    }
    /**
     * Infor: 查询所有的用户登录信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 19:38
     */
    private void loadAllLogInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取请求参数
        SysLogInfoVo sysLogInfoVo = new SysLogInfoVo();
        Integer page = Integer.valueOf(req.getParameter("page"));
        Integer limit = Integer.valueOf(req.getParameter("limit"));
        String loginname = req.getParameter("loginname");
        String loginip = req.getParameter("loginip");
        String startTimeStr = req.getParameter("startTime");
        String endTimeStr = req.getParameter("endTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

        sysLogInfoVo.setPage(page);
        sysLogInfoVo.setLimit(limit);
        sysLogInfoVo.setLoginip(loginip);
        sysLogInfoVo.setLoginname(loginname);
        sysLogInfoVo.setStartTime(startTime);
        sysLogInfoVo.setEndTime(endTime);
        //调用service层方法
        List<SysLogInfo> sysLogInfoListList= sysLogInfoService.findAllSysLoInfoList(sysLogInfoVo);
        //设置分页参数
        Long total = sysLogInfoService.getTotal();
        DataGridView dataGridView = new DataGridView(total, sysLogInfoListList);
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));

    }

    /**
     * Infor: 删除用户登录记录信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/1 19:37
     */
    private void deleteLogInfo(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException  {
        Integer id = Integer.valueOf(req.getParameter("id"));
        HashMap<String, Object> map = new HashMap<>();
        if (sysLogInfoService.deleteLogInfo(id)>0) {
            map.put("msg", SysTips.DELETE_SUCCESS);
        }else{
            map.put("msg",SysTips.DELETE_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
    }
}
