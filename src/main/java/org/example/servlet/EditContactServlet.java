package org.example.servlet;

import org.example.dao.ContactDao;
import org.example.model.Contact;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name="editContact", urlPatterns = "/contact/edit")
public class EditContactServlet extends HttpServlet {

    private ContactDao dao;

    @Override
    public void init() throws ServletException {
        try {
            dao = new ContactDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UUID uuid = UUID.fromString(id);
        Contact contact = dao.getContact(uuid);
        req.setAttribute("contact", contact);

        req.setAttribute("method", "update");
        req.setAttribute("context", req.getContextPath());
        resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        req.getRequestDispatcher("/createOrUpdate.jsp").forward(req, resp);
    }
}
