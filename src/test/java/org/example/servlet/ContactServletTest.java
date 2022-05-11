package org.example.servlet;

import org.example.dao.ContactDao;
import org.example.model.Contact;
import org.example.model.Gender;
import org.example.model.MaritalStatus;
import org.example.model.SocialNetwork;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ContactServletTest {

    private final static String IMAGE = "data:image/jpeg;base64,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";

    private ContactServlet servlet;
    private ContactDao dao;

    @Before
    public void init(){
        servlet = new ContactServlet();
        servlet.init();

        dao = Mockito.mock(ContactDao.class);

        //mockito when dao.blablabla then dosmth

        Whitebox.setInternalState(servlet, "dao", dao);
    }

    @Test
    public void testDoPostOnCreate() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(request.getParameter("_method")).thenReturn("create");

        InputStream is = new ByteArrayInputStream(IMAGE.getBytes(StandardCharsets.UTF_8));

        Part part = Mockito.mock(Part.class);
        Mockito.when(part.getInputStream()).thenReturn(is);

        Mockito.when(request.getPart("avatar")).thenReturn(part);
        Mockito.when(request.getParameter("firstname")).thenReturn("Dmytro");
        Mockito.when(request.getParameter("lastname")).thenReturn("Karkhut");
        Mockito.when(request.getParameter("phone")).thenReturn("0673093558");
        Mockito.when(request.getParameter("email")).thenReturn("qqqq@gmail.com");
        Mockito.when(request.getParameter("gender")).thenReturn("MALE");
        Mockito.when(request.getParameterValues("socialNetworks")).thenReturn(new String[]{"FACEBOOK"});
        Mockito.when(request.getParameter("maritalStatus")).thenReturn("MARRIED");

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        servlet.doPost(request, response);

        List<SocialNetwork> socialNetworks = new ArrayList<>();
        socialNetworks.add(SocialNetwork.FACEBOOK);

        Contact contact = new Contact(
                null,
                IMAGE.getBytes(StandardCharsets.UTF_8),
                "Dmytro",
                "Karkhut",
                "0673093558",
                "qqqq@gmail.com",
                Gender.MALE,
                socialNetworks,
                MaritalStatus.MARRIED
        );

        Mockito.verify(dao).createContact(contact);

    }

    @Test
    public void testDoPostOnEdit() throws ServletException, IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        Mockito.when(request.getParameter("_method")).thenReturn("update");

        InputStream is = new ByteArrayInputStream(IMAGE.getBytes(StandardCharsets.UTF_8));
        Part part = Mockito.mock(Part.class);
        Mockito.when(part.getInputStream()).thenReturn(is);

        Mockito.when(request.getPart("avatar")).thenReturn(part);
        Mockito.when(request.getParameter("firstName")).thenReturn("Ivan");
        Mockito.when(request.getParameter("lastName")).thenReturn("Khmilevskiy");
        Mockito.when(request.getParameter("phone")).thenReturn("0678889990");
        Mockito.when(request.getParameter("email")).thenReturn("ivan@gmail.com");
        Mockito.when(request.getParameter("gender")).thenReturn("MALE");
        Mockito.when(request.getParameterValues("socialNetworks")).thenReturn(new String[]{"TELEGRAM"});
        Mockito.when(request.getParameter("maritalStatus")).thenReturn("SINGLE");

        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        servlet.doPost(request, response);

        List<SocialNetwork> socialNetworks = new ArrayList<>();
        socialNetworks.add(SocialNetwork.TELEGRAM);

        Contact contact = new Contact(
                null,
                IMAGE.getBytes(StandardCharsets.UTF_8),
                "Dmytro",
                "Karkhut",
                "0673093558",
                "qqqq@gmail.com",
                Gender.MALE,
                socialNetworks,
                MaritalStatus.MARRIED
        );
        Mockito.verify(dao).updateContact(contact);
    }

    @Test
    public void testDoDelete() throws ServletException, IOException {
        servlet.doDelete(null, null);

    }
}
