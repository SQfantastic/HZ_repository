package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import org.lhz.entity.BunsBusCar;
import org.lhz.service.BunsCarMessageService;
import org.lhz.service.impl.BunsCarMessageServiceImpl;
import org.lhz.vo.BunsBusCarVo;
import utils.DataGridView;
import utils.SysTips;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/business/car")
public class BunsCarMessageController extends HttpServlet {
    private BunsCarMessageService storageService=new BunsCarMessageServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        switch (method){
            case "loadAll":
                loadAll(req,resp);
                break;
            case "addCar":
                addCar(req,resp);
                break;
            case "updateCar":
                updateCar(req,resp);
                break;
            case "deleteCar":
                deleteCar(req,resp);
                break;
            case "deleteBatchCar":
                deleteBatchCar(req,resp);
                break;
        }
    }

    private void deleteBatchCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String[] ids = req.getParameterValues("ids");
        Integer integer = storageService.deleteBatchCar(ids);
        HashMap<String, Object> map = new HashMap<>();
        if (integer== ids.length) {
            map.put("msg",SysTips.DELETE_SUCCESS);
        }else {
            map.put("msg",SysTips.DELETE_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.close();
    }

    protected void loadAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        String limit = req.getParameter("limit");
        String carnumber = req.getParameter("carnumber");
        String cartype = req.getParameter("cartype");
        String color = req.getParameter("color");
        String description = req.getParameter("description");
        String isrentings = req.getParameter("isrenting");
        System.out.println(isrentings);
        Integer isrenting = null;
        if (isrentings!= null) {
            isrenting = Integer.valueOf(isrentings);
        }
        BunsBusCarVo busCarVo = new BunsBusCarVo();
        busCarVo.setPage(Integer.parseInt(page));
        busCarVo.setLimit(Integer.parseInt(limit));
        busCarVo.setCarnumber(carnumber);
        busCarVo.setCartype(cartype);
        busCarVo.setColor(color);
        busCarVo.setDescription(description);
        busCarVo.setIsrenting(isrenting);
        List<BunsBusCar> bnsBusCars = storageService.loadAll(busCarVo);
        Long total = storageService.getTotal();
        DataGridView dataGridView = new DataGridView(total, bnsBusCars);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));
        writer.close();

    }
    protected void addCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carnumber = req.getParameter("carnumber");
        String cartype = req.getParameter("cartype");
        String color = req.getParameter("color");
        String price = req.getParameter("price");
        String rentprice = req.getParameter("rentprice");
        String deposit = req.getParameter("deposit");
        String isrenting = req.getParameter("isrenting");
        String description = req.getParameter("description");
        String carimg = req.getParameter("carimg");
        Date day = new Date();
        BunsBusCar build =new BunsBusCar();
        build.setCarnumber(carnumber);
        build.setCartype(cartype);
        build.setColor(color);
        build.setPrice(Integer.parseInt(price));
        build.setRentprice(Integer.parseInt(rentprice));
        build.setDeposit(Integer.parseInt(deposit));
        build.setIsrenting(Integer.parseInt(isrenting));
        build.setDescription(description);
        build.setCarimg(carimg);
        build.setCreatetime(day);
        Integer integer = storageService.addCar(build);
        HashMap<String, Object> map = new HashMap<>();
        if (integer==1) {
            map.put("msg",SysTips.ADD_SUCCESS);
        }else {
            map.put("msg",SysTips.ADD_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.close();

    }
    protected void updateCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carnumber = req.getParameter("carnumber");
        String cartype = req.getParameter("cartype");
        String color = req.getParameter("color");
        String price = req.getParameter("price");
        String rentprice = req.getParameter("rentprice");
        String deposit = req.getParameter("deposit");
        String isrenting = req.getParameter("isrenting");
        String description = req.getParameter("description");
        String carimg = req.getParameter("carimg");
        Date day=new Date();
        BunsBusCar build =new BunsBusCar();
        build.setCarnumber(carnumber);
        build.setCartype(cartype);
        build.setColor(color);
        build.setPrice(Integer.parseInt(price));
        build.setRentprice(Integer.parseInt(rentprice));
        build.setDeposit(Integer.parseInt(deposit));
        build.setIsrenting(Integer.parseInt(isrenting));
        build.setDescription(description);
        build.setCarimg(carimg);
        build.setCreatetime(day);
        Integer integer = storageService.updateCar(build);
        HashMap<String, Object> map = new HashMap<>();
        if (integer==1) {
            map.put("msg",SysTips.UPDATE_SUCCESS);
        }else {
            map.put("msg",SysTips.UPDATE_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.close();
    }
    protected void deleteCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carnumber = req.getParameter("carnumber");
        Integer integer = storageService.deleteCar(carnumber);
        HashMap<String, Object> map = new HashMap<>();
        if (integer==1) {
            map.put("msg",SysTips.DELETE_SUCCESS);
        }else {
            map.put("msg",SysTips.DELETE_ERROR);
        }
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.close();
    }
}
