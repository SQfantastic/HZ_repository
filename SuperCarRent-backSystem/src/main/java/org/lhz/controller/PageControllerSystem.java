package org.lhz.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/system")
public class PageControllerSystem extends HttpServlet {


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch(method){
            case"toDeskManager":
                toDeskManager(req,resp);
                break;
            case"toMenuManagerPage":
                toMenuManagerPage(req,resp);
                break;
            case"toRoleManagerPage":
                toRoleManagerPage(req,resp);
                break;
            case"toUserManagerPage":
                toUserManagerPage(req,resp);
                break;
            case"toLogInfoPage":
                toLogInfoPage(req,resp);
                break;
            case"toNewsManagerPage":
                toNewsManagerPage(req,resp);
                break;
            case"toMenuLeft":
                toMenuLeft(req,resp);
                break;
            case"toMenuRight":
                toMenuRight(req,resp);
                break;
        }
    }

    private void toMenuRight(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/view/system/menu/menuRight.jsp").forward(req,resp);
    }

    private void toMenuLeft(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("WEB-INF/view/system/menu/menuLeft.jsp").forward(req,resp);

    }

    private void toNewsManagerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException ,IOException {
        req.getRequestDispatcher("WEB-INF/view/system/news/newsManager.jsp").forward(req,resp);
    }


    private void toDeskManager(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        req.getRequestDispatcher("WEB-INF/view/system/main/deskManager.jsp").forward(req,resp);

    }

    private void toLogInfoPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        req.getRequestDispatcher("WEB-INF/view/system/logInfo/logInfoManager.jsp").forward(req,resp);
    }

    private void toUserManagerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        req.getRequestDispatcher("WEB-INF/view/system/user/userManager.jsp").forward(req,resp);
    }

    private void toRoleManagerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        req.getRequestDispatcher("WEB-INF/view/system/role/roleManager.jsp").forward(req,resp);
    }

    private void toMenuManagerPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException{
        req.getRequestDispatcher("WEB-INF/view/system/menu/menuManager.jsp").forward(req,resp);
    }

}
