package org.example.dao;

import org.example.model.Contact;
import org.example.model.Gender;
import org.example.model.MaritalStatus;
import org.example.model.SocialNetwork;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ContactDaoTest {

    private static String image = "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\n";

    private ContactDao dao;

    @Before
    public void init() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        dao = new ContactDao();
    }

    @Test
    public void testGetContact(){
        UUID uuid = UUID.randomUUID();

        Contact contact = new Contact(uuid, image.getBytes(), "testName", "testName", "111", "qqq@www.eee", Gender.MALE, List.of(SocialNetwork.FACEBOOK), MaritalStatus.MARRIED);

        Contact createdContact = dao.createContact(contact);

        Contact retrievedContact = dao.getContact(createdContact.getId());

        Assert.assertEquals(createdContact.getFirstName(), retrievedContact.getFirstName());
        Assert.assertEquals(createdContact.getLastName(), retrievedContact.getLastName());
        Assert.assertEquals(createdContact.getPhone(), retrievedContact.getPhone());
        Assert.assertEquals(createdContact.getEmail(), retrievedContact.getEmail());
        Assert.assertEquals(createdContact.getGender(), retrievedContact.getGender());
        Assert.assertEquals(createdContact.getSocialNetworks(), retrievedContact.getSocialNetworks());
        Assert.assertEquals(createdContact.getMaritalStatus(), retrievedContact.getMaritalStatus());
    }

    @Test
    public void testCreateContact(){
        UUID uuid = UUID.randomUUID();
        Contact contact = new Contact(uuid, image.getBytes(), "testName", "testName", "111", "qqq@www.eee", Gender.MALE, List.of(SocialNetwork.FACEBOOK), MaritalStatus.MARRIED);

        Contact createdContact = dao.createContact(contact);

        Assert.assertEquals(createdContact.getFirstName(), contact.getFirstName());
        Assert.assertEquals(createdContact.getLastName(), contact.getLastName());
        Assert.assertEquals(createdContact.getPhone(), contact.getPhone());
        Assert.assertEquals(createdContact.getEmail(), contact.getEmail());
        Assert.assertEquals(createdContact.getGender(), contact.getGender());
        Assert.assertEquals(createdContact.getSocialNetworks(), contact.getSocialNetworks());
        Assert.assertEquals(createdContact.getMaritalStatus(), contact.getMaritalStatus());
    }
    @Test
    public void testUpdateContact(){
        UUID uuid = UUID.randomUUID();

        Contact contact = new Contact(uuid, image.getBytes(), "testName", "testDmytro", "666666", "qqq@www.eee", Gender.MALE, List.of(SocialNetwork.FACEBOOK,SocialNetwork.TWITTER), MaritalStatus.MARRIED);

        Contact createdContact = dao.createContact(contact);

        createdContact.setPhone("222222");
        createdContact.setFirstName("tested1");

        dao.updateContact(createdContact);

        Contact updatedContact = dao.getContact(createdContact.getId());

        Assert.assertEquals(updatedContact.getFirstName(), createdContact.getFirstName());
        Assert.assertEquals(updatedContact.getLastName(), createdContact.getLastName());
        Assert.assertEquals(updatedContact.getPhone(), createdContact.getPhone());
        Assert.assertEquals(updatedContact.getEmail(), createdContact.getEmail());
        Assert.assertEquals(updatedContact.getGender(), createdContact.getGender());
        Assert.assertEquals(updatedContact.getSocialNetworks(), createdContact.getSocialNetworks());
        Assert.assertEquals(updatedContact.getMaritalStatus(), createdContact.getMaritalStatus());
    }

    @Test
    public void testDeleteContact(){
        UUID uuid = UUID.randomUUID();

        Contact contact = new Contact(uuid, image.getBytes(), "testName", "testName", "111", "qqq@www.eee", Gender.MALE, List.of(SocialNetwork.FACEBOOK), MaritalStatus.MARRIED);

        Contact createdContact = dao.createContact(contact);

        Assert.assertNotNull(createdContact);

        dao.deleteContact(createdContact.getId());

        Contact deletedContact = dao.getContact(createdContact.getId());

        Assert.assertNull(deletedContact);
    }

}
