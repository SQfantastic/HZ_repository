package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import org.lhz.entity.BunsBusChecks;
import org.lhz.entity.BunsBusRent;
import org.lhz.entity.SysUser;
import org.lhz.service.BunsCheckCarService;
import org.lhz.service.impl.BunsCheckCarServiceImpl;
import org.lhz.vo.BunsBusChecksVo;
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
import java.util.Map;

@WebServlet("/business/check")
public class BunsCheckCarController extends HttpServlet {
    private BunsCheckCarService checkListService=new BunsCheckCarServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String method = req.getParameter("method");
        switch (method){
            case "checkRentIsExist":
                checkRentIsExist(req,resp);
                break;
            case "initCheckFormData":
                initCheckFormData(req,resp);
                break;
            case "updateCheck":
                updateCheck(req,resp);
                break;
            case "loadAllCheck":
                loadAllCheck(req,resp);
                break;
        }
    }



    protected void checkRentIsExist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rentid = req.getParameter("rentid");
        BunsBusRent busRent = checkListService.queryRentByRentId(rentid);
        DataGridView dataGridView =new DataGridView(busRent);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));
        writer.close();
    }
    protected void initCheckFormData(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rentid = req.getParameter("rentid");
        Map<String, Object> map = checkListService.initCheckFormData(rentid);

        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.close();
    }
    protected void updateCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String checkid = req.getParameter("checkid");
        String problem = req.getParameter("problem");
        String checkdesc = req.getParameter("checkdesc");
        String paymoney = req.getParameter("paymoney");
        SysUser user =(SysUser) req.getSession().getAttribute("user");
        String realname = user.getRealname();
        Date date = new Date();
        BunsBusChecks bnsBusChecks = new BunsBusChecks();
        bnsBusChecks.setCheckid(checkid);
        bnsBusChecks.setProblem(problem);
        bnsBusChecks.setCheckdesc(checkdesc);
        bnsBusChecks.setPaymoney(Integer.parseInt(paymoney));
        bnsBusChecks.setOpername(realname);
        bnsBusChecks.setCheckdate(date);
        Integer integer = checkListService.updateCheck(bnsBusChecks);
        HashMap<String, Object> map = new HashMap<>();
        if (integer==1) {
            map.put("msg", SysTips.UPDATE_SUCCESS);
        }else {
            map.put("msg",SysTips.UPDATE_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.close();
    }
    private void loadAllCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String page = req.getParameter("page");
        String limit = req.getParameter("limit");
        String checkid = req.getParameter("checkid");
        String rentid = req.getParameter("rentid");
        String opername = req.getParameter("opername");
        String startTimeStr = req.getParameter("startTime");
        String endTimeStr = req.getParameter("endTime");
        String checkdesc = req.getParameter("checkdesc");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime=null;
        Date endTime=null;
        try {
            if(startTimeStr!=null&&!"".equals(startTimeStr)){
                startTime=sdf.parse(startTimeStr.trim());
            }
            if(endTimeStr!=null&&!"".equals(endTimeStr)){
                endTime=sdf.parse(endTimeStr.trim());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BunsBusChecksVo bnsBusChecksvo = new BunsBusChecksVo();
        bnsBusChecksvo.setLimit(Integer.parseInt(page));
        bnsBusChecksvo.setLimit(Integer.parseInt(limit));
        bnsBusChecksvo.setCheckid(checkid);
        bnsBusChecksvo.setRentid(rentid);
        bnsBusChecksvo.setOpername(opername);
        bnsBusChecksvo.setStartTime(startTime);
        bnsBusChecksvo.setEndTime(endTime);
        bnsBusChecksvo.setCheckdesc(checkdesc);
        List<BunsBusChecks> busChecks = checkListService.loadAll(bnsBusChecksvo);
        Long total = checkListService.getTotal();
        DataGridView dataGridView = new DataGridView(total, busChecks);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));
        writer.close();
    }
}
