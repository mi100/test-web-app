package org.example.servlet;

import org.example.dao.ContactDao;
import org.example.model.Contact;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="contacts", urlPatterns = "/contacts")
public class ContactsServlet extends HttpServlet {

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
        List<Contact> contacts = dao.getContacts();

        req.setAttribute("contacts", contacts);
        resp.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        req.getRequestDispatcher("contacts.jsp").forward(req, resp);
    }
}
