package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="createContact", urlPatterns = "/contact/create")
public class CreateContactServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("method", "create");
        req.setAttribute("context", req.getContextPath());
        req.getRequestDispatcher("/createOrUpdate.jsp").forward(req, resp);
    }
}
