package org.lhz.controller;

import com.alibaba.fastjson.JSON;
import org.lhz.entity.BunsBusCustomer;
import org.lhz.service.CustomerService;
import org.lhz.service.impl.CustomerServiceImpl;
import org.lhz.vo.BunsBusCustomerVo;
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

@WebServlet("/customer")
public class CustomerController extends HttpServlet {
    //注入service对象
    private CustomerService customerService = new CustomerServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        String method = req.getParameter("method");
        //根据请求参数分别处理请求
        switch (method) {
            case "loadAllCustomer":
                loadAllCustomer(req, resp);
                break;
            case "deleteCustomer":
                deleteCustomer(req, resp);
                break;
            case "addCustomer":
                addCustomer(req, resp);
                break;
            case "updateCustomer":
                updateCustomer(req, resp);
                break;
            case "deleteBatchCustomer":
                deleteBatchCustomer(req, resp);
                break;
        }
    }
    /**
     * Infor:导入所有的客户信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/3 11:37
     */
    private void loadAllCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取请求参数
        Integer page = Integer.valueOf(req.getParameter("page"));
        Integer limit = Integer.valueOf(req.getParameter("limit"));
        String custname = req.getParameter("custname");
        String identity = req.getParameter("identity");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String career = req.getParameter("career");
        String sexStr = req.getParameter("sex");
        Integer sex = null;
        if (sexStr!= null) {
            sex = Integer.valueOf(sexStr);
        }
        BunsBusCustomerVo customerVo = new BunsBusCustomerVo();
        customerVo.setPage(page);
        customerVo.setAddress(address);
        customerVo.setLimit(limit);
        customerVo.setIdentity(identity);
        customerVo.setPhone(phone);
        customerVo.setSex(sex);
        customerVo.setCareer(career);
        customerVo.setCustname(custname);
        //调用service层方法
        List<BunsBusCustomer> customerList= customerService.findAllCustomerList(customerVo);
        //设置分页参数
        Long total = customerService.getTotal();
        DataGridView dataGridView = new DataGridView(total, customerList);
        //写出数据
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write(JSON.toJSONString(dataGridView));
    }

    /**
     * Infor:删除客户信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/3 11:37
     */
    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取参数
        Integer identity = Integer.valueOf(req.getParameter("identity"));
        HashMap<String, Object> map = new HashMap<>();
        if (customerService.deleteCustomerById(identity)>0) {
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
     * Infor:添加客户信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/3 11:37
     */
    private void addCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取请求参数
        String custname = req.getParameter("custname");
        String identity = req.getParameter("identity");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String career = req.getParameter("career");
        String sexStr = req.getParameter("sex");
        Integer sex = null;
        if (sexStr!= null) {
            sex = Integer.valueOf(sexStr);
        }
        BunsBusCustomer customer = new BunsBusCustomer();
        customer.setIdentity(identity);
        customer.setPhone(phone);
        customer.setSex(sex);
        customer.setCareer(career);
        customer.setCustname(custname);
        customer.setAddress(address);
        customer.setCreatetime(new Date());
        HashMap<String, Object> map = new HashMap<>();
        if (customerService.addCustomer(customer)>0) {
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
     * Infor:更新客户信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/3 11:37
     */
    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //获取请求参数
        String custname = req.getParameter("custname");
        String identity = req.getParameter("identity");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String career = req.getParameter("career");
        String sexStr = req.getParameter("sex");
        Integer sex = null;
        if (sexStr!= null) {
            sex = Integer.valueOf(sexStr);
        }
        BunsBusCustomer customer = new BunsBusCustomer();
        customer.setIdentity(identity);
        customer.setPhone(phone);
        customer.setSex(sex);
        customer.setCareer(career);
        customer.setCustname(custname);
        customer.setAddress(address);
        HashMap<String, Object> map = new HashMap<>();
        if (customerService.updateCustomer(customer)>0) {
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
     * Infor:批量删除客户信息
     * @param req
     * @param resp
     * @return : void
     * @author : LHZ
     * @date : 2021/11/3 11:37
     */
    private void deleteBatchCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String ids[] = req.getParameterValues("ids");
    }
}