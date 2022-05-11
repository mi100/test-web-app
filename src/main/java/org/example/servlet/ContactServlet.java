package org.example.servlet;

import org.example.dao.ContactDao;
import org.example.model.Contact;
import org.example.model.Gender;
import org.example.model.MaritalStatus;
import org.example.model.SocialNetwork;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name="contact", urlPatterns = "/contact")
@MultipartConfig
public class ContactServlet extends HttpServlet {

    private ContactDao dao;

    @Override
    public void init() {
        try {
            dao = new ContactDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("_method");

        UUID uuid = null;
        if (method.equals("update")) {
            String id = req.getParameter("id");
            uuid = UUID.fromString(id);
        }

        Part avatarPart = req.getPart("avatar");
        InputStream inputStream = avatarPart.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        byte[] avatar = buffer.toByteArray();

        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        Gender gender = Gender.valueOf(req.getParameter("gender"));

        String[] socialNetworkValues = req.getParameterValues("socialNetworks");
        List<SocialNetwork> socialNetworks = new ArrayList<>();
        for(String socialNetworkValue : socialNetworkValues){
            SocialNetwork socialNetwork = SocialNetwork.valueOf(socialNetworkValue);
            socialNetworks.add(socialNetwork);
        }

        MaritalStatus maritalStatus = MaritalStatus.valueOf(req.getParameter("maritalStatus"));

        Contact contact = new Contact(uuid, avatar, firstName, lastName, phone, email, gender, socialNetworks, maritalStatus);

        if (method.equals("create")){
            dao.createContact(contact);
        }else if (method.equals("update")){
            dao.updateContact(contact);
        }

        resp.sendRedirect(req.getContextPath() + "/contacts");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        UUID uuid = UUID. fromString(id);
        dao.deleteContact(uuid);
    }
}
