package org.lhz.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/business")
public class PageControllerBusiness  extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch(method){
            case"toCustomerManagerPage":
                toCustomerManagerPage(req,resp);
                break;
            case"toCarManagerPage":
                toCarManagerPage(req,resp);
                break;
            case"toRentCarManagerPage":
                toRentCarManagerPage(req,resp);
                break;
            case"toRentManagerPage":
                toRentManagerPage(req,resp);
                break;
            case"toCheckCarManagerPage":
                toCheckCarManagerPage(req,resp);
                break;
            case"toCheckManagerPage":
                toCheckManagerPage(req,resp);
                break;
        }
    }

    private void toCheckManagerPage(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException  {
        req.getRequestDispatcher("WEB-INF/view/business/check/checkManager.jsp").forward(req,resp);
    }

    private void toCheckCarManagerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/business/check/checkCarManager.jsp").forward(req,resp);
    }

    private void toRentManagerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/business/rent/rentManager.jsp").forward(req,resp);

    }

    private void toRentCarManagerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/business/rent/rentCarManager.jsp").forward(req,resp);
    }

    private void toCarManagerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/business/car/carManager.jsp").forward(req,resp);
    }

    private void toCustomerManagerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/business/customer/customerManager.jsp").forward(req,resp);
    }
}
